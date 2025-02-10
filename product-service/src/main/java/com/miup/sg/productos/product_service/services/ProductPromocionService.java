 package com.miup.sg.productos.product_service.services;

import java.time.LocalDate;
import java.util.List;

import com.miup.sg.productos.product_service.models.productos.entity.PromocionEntity;

public interface ProductPromocionService {

    List<PromocionEntity> obtenerTodasLasPromociones();

    Double aplicarPromociones(String productoId, Double cantidad, Double precioUnitario);

    PromocionEntity cambiarEstadoPromocion(Long id, Boolean activa);

    PromocionEntity crearPromocion(String nombre, String tipo, Double valor, Integer cantidadBonificada,
            Integer cantidadRequerida, LocalDate fechaFin, String producto);

}