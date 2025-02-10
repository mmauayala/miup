package com.miup.sg.productos.product_service.models.productos;

import java.time.LocalDate;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Promocion {

    private String nombre; 
    private String tipo;  
    private Double valor;  
    private Integer cantidadRequerida; 
    private Integer cantidadBonificada; 
    private LocalDate fechaFin;    
    private String producto; 

}
