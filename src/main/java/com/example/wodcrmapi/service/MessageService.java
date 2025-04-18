package com.example.wodcrmapi.service;

import com.example.wodcrmapi.dto.request.MessageRequest;
import com.example.wodcrmapi.dto.request.PaginationRequest;
import com.example.wodcrmapi.dto.response.PaginatedResponse;
import com.example.wodcrmapi.entity.Message;
import com.example.wodcrmapi.exception.NotFoundException;
import com.example.wodcrmapi.repository.MessageRepository;
import com.example.wodcrmapi.specifications.MessageSpecifications;
import jakarta.annotation.PostConstruct;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MessageService {

    MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @PostConstruct
    public void init() {
        System.out.println("MessageService initialized");
    }


    public ResponseEntity<PaginatedResponse<Message>> getAllMessages(PaginationRequest paginationRequest) {
        Specification<Message> spec = MessageSpecifications.withSearch(paginationRequest.getSearch());
        Page<Message> pageResult = messageRepository.findAll(spec, paginationRequest.toPageable());
        return ResponseEntity.ok(new PaginatedResponse<>(pageResult));
    }

    public Message addMessage(Message message) {
        return messageRepository.save(message);
    }

    public Message getMessageById(Long id) {
        return messageRepository.findById(id).orElse(null);
    }

    public Message updateMessage(Long id, MessageRequest request) {

        return messageRepository.findById(id)
                .map(existingMessage -> {
                    existingMessage.setKey(request.getKey());
                    existingMessage.setRu(request.getRu());
                    existingMessage.setUz(request.getUz());
                    existingMessage.setKaa(request.getKaa());

                    return messageRepository.save(existingMessage);
                })
                .orElseThrow(() -> new NotFoundException("Message not found"));
    }

    public void deleteMessage(Long id) {
        messageRepository.deleteById(id);
    }

    public Message getOrCreateMessageByKey(String key) {
        Optional<Message> optionalMessage = messageRepository.findByKey(key);
        Message message;
        message = optionalMessage.orElseGet(() -> addMessage(new Message(key)));
        return message;
    }
}
