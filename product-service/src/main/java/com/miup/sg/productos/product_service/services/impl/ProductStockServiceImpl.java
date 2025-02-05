package com.miup.sg.productos.product_service.services.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.miup.sg.productos.product_service.exception.ProductNotFoundException;
import com.miup.sg.productos.product_service.models.productos.entity.ProductEntity;
import com.miup.sg.productos.product_service.models.productos.entity.StockEntity;
import com.miup.sg.productos.product_service.repositories.ProductRepository;
import com.miup.sg.productos.product_service.repositories.StockRepository;
import com.miup.sg.productos.product_service.services.ProductMovimientoService;
import com.miup.sg.productos.product_service.services.ProductStockService;

@Service
public class ProductStockServiceImpl implements ProductStockService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private ProductMovimientoService productMovService;

    @Override
    @Transactional(readOnly = false)
    public StockEntity RegisterStock(String productoId, Double cantidad, Double precioIngreso) {
        final ProductEntity productEntityFromDB = productRepository
                .findById(productoId)
                .orElseThrow(() -> new ProductNotFoundException("With given productID = " + productoId));

    StockEntity stockExistente = stockRepository.findByProductoId(productoId);

    // Si ya existe stock, sumar la cantidad
    if (stockExistente != null) {
        stockExistente.setCantidad(stockExistente.getCantidad() + cantidad); // Sumar la cantidad
        stockExistente.setPrecioIngreso(stockExistente.getPrecioIngreso()); // Actualizar el precio de compra
        stockExistente.setFechaIngreso(LocalDateTime.now()); // Actualizar la fecha de ingreso
        
        productMovService.registrarMovimientoStock(stockExistente, cantidad, precioIngreso, "INGRESO");

        return stockRepository.save(stockExistente);
    
    } else {  
        StockEntity stock = new StockEntity();
        stock.setProducto(productEntityFromDB);
        stock.setCantidad(cantidad);
        stock.setPrecioIngreso(precioIngreso);
        stock.setFechaIngreso(LocalDateTime.now());
        
        productMovService.registrarMovimientoStock(stock, cantidad, precioIngreso, "INGRESO");
        
        return stockRepository.save(stock);

    }

    }

    @Override
    @Transactional(readOnly = true)
    public StockEntity StockByProduct(String productoId) {
        
        return stockRepository.findByProductoId(productoId);
    }
}
