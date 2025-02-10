package com.miup.sg.productos.product_service.models.productos.entity;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "promocion")
public class PromocionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre; 
    private String tipo;   // "DESCUENTO_PORCENTUAL", "DESCUENTO_FIJO", "CANTIDAD"
    private Double valor;  // Valor de la promoción (ejemplo: 20 para 20% de descuento)
    private Integer cantidadRequerida; // Cantidad requerida para aplicar la promoción (ejemplo: 2 para 2x1)
    private Integer cantidadBonificada; // Cantidad bonificada (ejemplo: 1 para 2x1)
    private LocalDate fechaInicio; 
    private LocalDate fechaFin;    
    private Boolean activa; 

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private ProductEntity producto; 

}
