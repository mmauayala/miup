package com.miup.sg.productos.product_service.models.productos.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.SuperBuilder;


@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "movimiento")
public class MovimientoStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "stock_id", nullable = false)
    private StockEntity stock;

    private LocalDateTime fecha; 
    private Double cantidad; 
    private Double precio; 
    private String tipo; 

}
