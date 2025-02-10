package com.miup.sg.productos.product_service.services.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miup.sg.productos.product_service.models.productos.entity.DesperdicioEntity;
import com.miup.sg.productos.product_service.models.productos.entity.ProductEntity;
import com.miup.sg.productos.product_service.models.productos.entity.StockEntity;
import com.miup.sg.productos.product_service.repositories.DesperdicioRepository;
import com.miup.sg.productos.product_service.repositories.ProductRepository;
import com.miup.sg.productos.product_service.repositories.StockRepository;
import com.miup.sg.productos.product_service.services.ProductDesperdicioService;
import com.miup.sg.productos.product_service.services.ProductMovimientoService;

@Service
public class ProductDesperdicioServiceImpl implements ProductDesperdicioService{

    @Autowired
    private DesperdicioRepository desperdicioRepository;

    @Autowired
    private StockRepository stockRepository;
    
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMovimientoService productMovService;

    @Override
    public DesperdicioEntity registrarDesperdicio(String productoId, Double cantidad) {
    ProductEntity producto = productRepository.findById(productoId)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

    StockEntity stock = stockRepository.findByProductoId(productoId);

    if (stock == null) {
        throw new RuntimeException("No se encontró stock para el producto");
    }

    if (stock.getCantidad() < cantidad) {
        throw new RuntimeException("Stock insuficiente para registrar el desperdicio");
    }

    stock.setCantidad(stock.getCantidad() - cantidad); // Restar la cantidad desperdiciada
    stock.setFechaIngreso(LocalDateTime.now());

    DesperdicioEntity desperdicio = new DesperdicioEntity();
    desperdicio.setProducto(producto);
    desperdicio.setCantidad(cantidad);
    desperdicio.setFechaDesperdicio(LocalDateTime.now());

    desperdicioRepository.save(desperdicio);
    stockRepository.save(stock);

    productMovService.registrarMovimientoStock(stock, cantidad, "DESPERDICIO");
    
    return desperdicio;
    }

    public List<DesperdicioEntity> obtenerHistorialDesperdicios(String productoId) {
        return desperdicioRepository.findByProductoId(productoId);
    }
}
