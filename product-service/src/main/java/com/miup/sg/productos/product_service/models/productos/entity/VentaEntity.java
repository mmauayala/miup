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
@Table(name = "venta")
public class VentaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "stock_id", nullable = false)
    private StockEntity stock; 

    @JoinColumn(name = "cantidad_vendida", nullable = false)
    private Double cantidadVendida; 

    @JoinColumn(name = "precio_venta", nullable = false)
    private Double precioVenta; 

    @JoinColumn(name = "fecha_venta", nullable = false)
    private LocalDateTime fechaVenta; 

    @OneToOne
    @JoinColumn(name = "movimiento_id", nullable = false)
    private MovimientoStock movimiento;

}
