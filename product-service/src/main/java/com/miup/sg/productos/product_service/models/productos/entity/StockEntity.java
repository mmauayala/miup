package com.miup.sg.productos.product_service.models.productos.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;


@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "stock")
public class StockEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private ProductEntity producto;

    private Double cantidad;

    @JoinColumn(name = "fecha_ingreso", nullable = false)
    private LocalDateTime fechaIngreso;

    @JoinColumn(name = "precio_ingreso", nullable = false)
    private Double precioIngreso;

    @OneToMany(mappedBy = "stock", cascade = CascadeType.PERSIST)
    private List<MovimientoStock> movimientos;


}
