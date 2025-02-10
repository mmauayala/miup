package com.miup.sg.productos.product_service.services;


import java.util.List;

import com.miup.sg.productos.product_service.models.productos.entity.StockEntity;

public interface ProductStockService {

    StockEntity RegisterStock(String productoId, Double cantidad, Double precioIngreso, Double precioVenta);

    StockEntity StockByProduct(String productoId);
    
    List<StockEntity> obtenerStock();

}
