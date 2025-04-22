package com.example.wodcrmapi.mapper;

import com.example.wodcrmapi.dto.request.DealRequest;
import com.example.wodcrmapi.dto.response.DealResponse;
import com.example.wodcrmapi.entity.Deal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DealMapper {
    @Mapping(target = "company", ignore = true) // Will be set manually in service
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Deal toEntity(DealRequest dealRequest);

    @Mapping(source = "company.id", target = "companyId")
    DealResponse toResponse(Deal deal);

    @Mapping(target = "company", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    void updateEntity(DealRequest dealRequest, @MappingTarget Deal deal);
}
