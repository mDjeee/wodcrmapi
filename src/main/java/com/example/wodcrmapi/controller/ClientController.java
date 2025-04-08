package com.example.wodcrmapi.controller;

import com.example.wodcrmapi.entity.Client;
import com.example.wodcrmapi.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@CrossOrigin(origins = "*") // Allow frontend requests (for testing)
@Tag(name = "Client Management", description = "Endpoints for managing clients")
public class ClientController {

    private final ClientService service;

    public ClientController(ClientService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Get all clients", description = "Returns a list of all clients")
    public List<Client> getAll() {
        return service.getAllClients();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get client by id", description = "Returns client")
    public ResponseEntity<Client> getById(@PathVariable Long id) {
        return service.getClientById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a new client", description = "Adds a new client")
    public Client create(@RequestBody Client Client) {
        return service.createClient(Client);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update client by id", description = "Updates and return client")
    public Client update(@PathVariable Long id, @RequestBody Client Client) {
        return service.updateClient(id, Client);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete client by id", description = "Deletes client")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteClient(id);
        return ResponseEntity.noContent().build();
    }
}
