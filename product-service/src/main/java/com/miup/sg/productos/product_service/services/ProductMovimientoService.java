package com.miup.sg.productos.product_service.services;

import java.util.List;

import com.miup.sg.productos.product_service.models.productos.entity.MovimientoStock;
import com.miup.sg.productos.product_service.models.productos.entity.StockEntity;

public interface ProductMovimientoService {

    MovimientoStock registrarMovimientoStock(StockEntity stock, Double cantidad, String tipo);

    List<MovimientoStock> obtenerHistorialMovimientos(String productoId);
}
