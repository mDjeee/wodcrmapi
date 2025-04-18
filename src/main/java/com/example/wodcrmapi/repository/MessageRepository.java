package com.example.wodcrmapi.repository;

import com.example.wodcrmapi.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, Long> {
    Optional<Message> findByKey(String key);
    Page<Message> findAll(Specification<Message> spec, Pageable pageable);
}
