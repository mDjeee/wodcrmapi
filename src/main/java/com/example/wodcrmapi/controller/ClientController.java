package com.example.wodcrmapi.controller;

import com.example.wodcrmapi.aop.CheckPermission;
import com.example.wodcrmapi.dto.request.CreateClientRequest;
import com.example.wodcrmapi.dto.request.PaginationRequest;
import com.example.wodcrmapi.dto.response.ClientResponse;
import com.example.wodcrmapi.dto.response.PaginatedResponse;
import com.example.wodcrmapi.entity.Client;
import com.example.wodcrmapi.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.coyote.BadRequestException;
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
    @CheckPermission(value = "CLIENT:READALL", description = "Gets all Clients", displayName = "Получение списка клиентов")
    @Operation(summary = "Get all clients", description = "Returns a list of all clients")
    public ResponseEntity<PaginatedResponse<ClientResponse>> getAll(
            @ModelAttribute PaginationRequest paginationRequest
    ) {
        return service.getAllClients(paginationRequest);
    }

    @GetMapping("/{id}")
    @CheckPermission(value = "CLIENT:READ", description = "Get client by id", displayName = "Получение клиента по ID")
    @Operation(summary = "Get client by id", description = "Returns client")
    public ResponseEntity<ClientResponse> getById(@PathVariable Long id) {
        return service.getClientById(id);
    }

    @PostMapping
    @CheckPermission(value = "CLIENT:CREATE", description = "Create client", displayName = "Добавление клиента")
    @Operation(summary = "Create a new client", description = "Adds a new client")
    public ClientResponse create(@RequestBody CreateClientRequest request) throws BadRequestException {
        return service.createClient(request);
    }

    @PutMapping("/{id}")
    @CheckPermission(value = "CLIENT:UPDATE", description = "Update client", displayName = "Обновление клиента")
    @Operation(summary = "Update client by id", description = "Updates and return client")
    public ClientResponse update(@PathVariable Long id, @RequestBody Client Client) {
        return service.updateClient(id, Client);
    }

    @DeleteMapping("/{id}")
    @CheckPermission(value = "CLIENT:DELETE", description = "Delete client", displayName = "Удалить клиента")
    @Operation(summary = "Delete client by id", description = "Deletes client")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteClient(id);
        return ResponseEntity.noContent().build();
    }
}
