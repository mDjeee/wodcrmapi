package com.example.wodcrmapi.controller;


import com.example.wodcrmapi.aop.CheckPermission;
import com.example.wodcrmapi.aop.PaginationParams;
import com.example.wodcrmapi.dto.request.MessageRequest;
import com.example.wodcrmapi.dto.request.PaginationRequest;
import com.example.wodcrmapi.dto.response.PaginatedResponse;
import com.example.wodcrmapi.entity.Message;
import com.example.wodcrmapi.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/messages")
@CrossOrigin(origins = "*")
@Tag(name = "Message Management", description = "Endpoints for managing messages")
public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping
    @CheckPermission(value = "MESSAGE:READALL", description = "Get all messages", displayName = "Получение списка сообщений")
    @Operation(summary = "Get all messages", description = "Returns a list of all messages")
    public ResponseEntity<PaginatedResponse<Message>> getAllMessages(
            @PaginationParams PaginationRequest paginationRequest
    ) {
        return messageService.getAllMessages(paginationRequest);
    }

    @GetMapping("/{id}")
    @CheckPermission(value = "MESSAGE:READ", description = "Get message", displayName = "Получение сообщений")
    @Operation(summary = "Get message by id", description = "Returns message by ID")
    public Message getMessageById(@PathVariable Long id) {
        return messageService.getMessageById(id);
    }

    @PutMapping("/{id}")
    @CheckPermission(value = "MESSAGE:UPDATE", description = "Update message", displayName = "Обновление сообщений")
    @Operation(summary = "Update message by id", description = "Updates and returns message")
    public Message updateMessage(@PathVariable Long id, @RequestBody MessageRequest message) {
        return messageService.updateMessage(id, message);
    }

    @DeleteMapping("/{id}")
    @CheckPermission(value = "MESSAGE:DELETE", description = "Delete message", displayName = "Удаление сообщений")
    @Operation(summary = "Delete message by id", description = "Deletes message by ID")
    public void deleteMessage(@PathVariable Long id) {
        messageService.deleteMessage(id);
    }
}
