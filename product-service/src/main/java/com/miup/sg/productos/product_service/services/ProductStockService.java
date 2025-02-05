package com.miup.sg.productos.product_service.services;

import com.miup.sg.productos.product_service.models.productos.entity.StockEntity;

public interface ProductStockService {

    StockEntity RegisterStock(String productoId, Double cantidad, Double precioIngreso);

    StockEntity StockByProduct(String productoId);

}
