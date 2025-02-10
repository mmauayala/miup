package com.miup.sg.productos.product_service.models.productos;

import java.time.LocalDateTime;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Desperdicio {

    private String id;
    private Double cantidad; 
    private LocalDateTime fechaDesperdicio;


}