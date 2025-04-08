package com.example.wodcrmapi.service;

import com.example.wodcrmapi.entity.Client;
import com.example.wodcrmapi.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository repo;

    public ClientService(ClientRepository repo) {
        this.repo = repo;
    }

    public List<Client> getAllClients() {
        return repo.findAll();
    }

    public Optional<Client> getClientById(Long id) {
        return repo.findById(id);
    }

    public Client createClient(Client Client) {
        return repo.save(Client);
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
