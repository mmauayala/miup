package com.miup.sg.productos.product_service.models.common.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.miup.sg.productos.product_service.models.common.CustomPaging;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class CustomPagingRequest {

    @Autowired
    private CustomPaging pagination;

    public Pageable toPageable() {
        return PageRequest.of(
                Math.toIntExact(pagination.getPageNumber()),
                Math.toIntExact(pagination.getPageSize())
        );
    }

}