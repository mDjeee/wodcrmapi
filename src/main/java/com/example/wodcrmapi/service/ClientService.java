package com.example.wodcrmapi.service;

import com.example.wodcrmapi.dto.request.CreateClientRequest;
import com.example.wodcrmapi.dto.request.PaginationRequest;
import com.example.wodcrmapi.dto.response.ClientResponse;
import com.example.wodcrmapi.dto.response.CompanyResponse;
import com.example.wodcrmapi.dto.response.PaginatedResponse;
import com.example.wodcrmapi.entity.Client;
import com.example.wodcrmapi.entity.Company;
import com.example.wodcrmapi.entity.User;
import com.example.wodcrmapi.exception.NotFoundException;
import com.example.wodcrmapi.mapper.ClientMapper;
import com.example.wodcrmapi.repository.ClientRepository;
import com.example.wodcrmapi.security.SecurityUtils;
import com.example.wodcrmapi.specifications.ClientSpecifications;
import com.example.wodcrmapi.specifications.CompanySpecifications;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository repo;
    private final CompanyService companyService;
    private final SecurityUtils securityUtils;
    private final ClientMapper mapper;

    public ClientService(
            ClientRepository repo,
            CompanyService companyService,
            SecurityUtils securityUtils,
            ClientMapper mapper
    ) {
        this.repo = repo;
        this.companyService = companyService;
        this.securityUtils = securityUtils;
        this.mapper = mapper;
    }

    public ResponseEntity<PaginatedResponse<ClientResponse>> getAllClients(
            PaginationRequest paginationRequest
    ) {
        Specification<Client> spec = ClientSpecifications.withSearch(paginationRequest.getSearch());
        Page<Client> pageResult = repo.findAll(spec, paginationRequest.toPageable());
        Page<ClientResponse> responsePage = pageResult.map(mapper::toResponse);

        return ResponseEntity.ok(new PaginatedResponse<>(responsePage));
    }

    public ResponseEntity<ClientResponse> getClientById(Long id) {
        Client client = repo.findById(id).orElseThrow(() -> new NotFoundException("Client not found"));
        ClientResponse clientResponse = mapper.toResponse(client);

        return new ResponseEntity<>(clientResponse, HttpStatus.OK);
    }

    public ClientResponse createClient(CreateClientRequest request) throws BadRequestException {
        Boolean clientExists = repo.existsClientByPhone(request.getPhone());

        if(clientExists) {
            throw new BadRequestException(
                    String.format("Клиент с номером %s уже существует", request.getPhone())
            );
        }

        User currentUser = securityUtils.getCurrentUser();
        Client client = new Client();

        client.setPhone(request.getPhone());
        client.setEmail(request.getEmail());
        client.setFirstName(request.getFirstName());
        client.setLastName(request.getLastName());

        Long companyId = Optional.ofNullable(request.getCompanyId())
                .orElseGet(currentUser::getCompanyId);

        CompanyResponse company = companyService.getCompanyById(companyId);

        if(company == null) {
            throw new BadRequestException(
                    String.format("Компании с ID %s не существует", request.getCompanyId())
            );
        } else {
            client.setCompanyId(company.getId());
        }

        client.setCreatedBy(currentUser);

        Client savedClient = repo.saveAndFlush(client);

        return mapper.toResponse(savedClient);
    }

    public ClientResponse updateClient(Long id, Client newClient) {
        return repo.findById(id)
                .map(Client -> {
                    Client.setFirstName(newClient.getFirstName());
                    Client.setLastName(newClient.getLastName());
                    Client.setPhone(newClient.getPhone());

                    Client.setEmail(newClient.getEmail());

                    Client savedClient = repo.saveAndFlush(Client);

                    return mapper.toResponse(savedClient);
                })
                .orElseThrow(() -> new RuntimeException("Client not found"));
    }

    public void deleteClient(Long id) {
        repo.deleteById(id);
    }
}
