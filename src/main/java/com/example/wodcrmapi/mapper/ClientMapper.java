package com.example.wodcrmapi.mapper;

import com.example.wodcrmapi.dto.response.ClientResponse;
import com.example.wodcrmapi.entity.Client;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    ClientResponse toResponse(Client client);
}
