package com.example.wodcrmapi.service;

import com.example.wodcrmapi.dto.request.CreateClientRequest;
import com.example.wodcrmapi.dto.response.CompanyResponse;
import com.example.wodcrmapi.entity.Client;
import com.example.wodcrmapi.entity.Company;
import com.example.wodcrmapi.entity.User;
import com.example.wodcrmapi.repository.ClientRepository;
import com.example.wodcrmapi.security.SecurityUtils;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository repo;
    private final CompanyService companyService;
    private SecurityUtils securityUtils;

    public ClientService(
            ClientRepository repo,
            CompanyService companyService,
            SecurityUtils securityUtils
    ) {
        this.repo = repo;
        this.companyService = companyService;
        this.securityUtils = securityUtils;
    }

    public List<Client> getAllClients() {
        return repo.findAll();
    }

    public Optional<Client> getClientById(Long id) {
        return repo.findById(id);
    }

    public Client createClient(CreateClientRequest request) throws BadRequestException {
        Boolean clientExists = repo.existsClientByPhone(request.getPhone());

        if(clientExists) {
            throw new BadRequestException(
                    String.format("Клиент с номером %s уже существует", request.getPhone())
            );
        }

        Client client = new Client();

        client.setPhone(request.getPhone());
        client.setEmail(request.getEmail());
        client.setFirstName(request.getFirstName());
        client.setLastName(request.getLastName());

        CompanyResponse company = companyService.getCompanyById(request.getCompanyId());

        if(company == null) {
            throw new BadRequestException(
                    String.format("Компании с ID %s не существует", request.getCompanyId())
            );
        } else {
            client.setCompanyId(company.getId());
        }

        User currentUser = securityUtils.getCurrentUser();
        client.setCreatedBy(currentUser);

        return repo.save(client);
    }

    public Client updateClient(Long id, Client newClient) {
        return repo.findById(id)
                .map(Client -> {
                    Client.setFirstName(newClient.getFirstName());
                    Client.setLastName(newClient.getLastName());
                    Client.setPhone(newClient.getPhone());
                    return repo.save(Client);
                })
                .orElseThrow(() -> new RuntimeException("Client not found"));
    }

    public void deleteClient(Long id) {
        repo.deleteById(id);
    }
}
