package com.miup.sg.productos.product_service.services;

import com.miup.sg.productos.product_service.models.productos.entity.VentaEntity;

public interface ProductVentaService {

    VentaEntity registrarVenta(String productoId, Double cantidadVendida, Double precioVenta);

}
