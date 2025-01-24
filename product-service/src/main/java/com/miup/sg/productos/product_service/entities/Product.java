package com.miup.sg.productos.product_service.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "producto")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="producto_id")
    private Long id;

    @NotEmpty(message = "{NotEmpty.producto.nombre}") //    NO PUEDE ESTAR SIN DATOS
    @Column(name="nombre")
    private String nombre;
    
    @Column(name="medida")
    private String medida;


}
