package com.miup.sg.productos.product_service.services.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miup.sg.productos.product_service.models.productos.entity.StockEntity;
import com.miup.sg.productos.product_service.models.productos.entity.VentaEntity;
import com.miup.sg.productos.product_service.repositories.StockRepository;
import com.miup.sg.productos.product_service.repositories.VentaRepository;
import com.miup.sg.productos.product_service.services.ProductMovimientoService;
import com.miup.sg.productos.product_service.services.ProductVentaService;

@Service
public class ProductVentaServiceImpl implements ProductVentaService{

    @Autowired
    private StockRepository stockRepository;
    
    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private ProductMovimientoService productMovService;

    @Override
    public VentaEntity registrarVenta(String productoId, Double cantidadVendida, Double precioVenta){

        StockEntity stock = stockRepository.findByProductoId(productoId);

        if (stock.getCantidad() < cantidadVendida) {
            throw new RuntimeException("Stock insuficiente");
        }

        VentaEntity venta = new VentaEntity();

        venta.setStock(stock);
        venta.setCantidadVendida(cantidadVendida);
        venta.setPrecioVenta(precioVenta);
        venta.setFechaVenta(LocalDateTime.now());
    
        stock.setCantidad(stock.getCantidad() - cantidadVendida);
        stockRepository.save(stock);
    
        productMovService.registrarMovimientoStock(stock, cantidadVendida, precioVenta, "VENTA");

        return ventaRepository.save(venta);
            
    }
   
}
