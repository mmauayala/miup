package com.miup.sg.productos.product_service.services;

import java.util.List;
import java.util.Map;

import com.miup.sg.productos.product_service.models.productos.entity.VentaEntity;

public interface ProductVentaService {

    VentaEntity ventaUnitaria(String productoId, Double precioVenta, String transaccionId);

    List<VentaEntity> registrarVenta(Map<String, Double> productosYCantidades);

}
