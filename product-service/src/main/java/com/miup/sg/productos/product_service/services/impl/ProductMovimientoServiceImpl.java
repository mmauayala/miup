package com.miup.sg.productos.product_service.services.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miup.sg.productos.product_service.models.productos.entity.MovimientoStock;
import com.miup.sg.productos.product_service.models.productos.entity.StockEntity;
import com.miup.sg.productos.product_service.repositories.MovimientoStockRepository;
import com.miup.sg.productos.product_service.repositories.StockRepository;
import com.miup.sg.productos.product_service.services.ProductMovimientoService;

@Service
public class ProductMovimientoServiceImpl implements ProductMovimientoService {

    @Autowired
    private MovimientoStockRepository movimientoStockRepository;

    @Autowired
    private StockRepository stockRepository;
    
    public MovimientoStock registrarMovimientoStock(StockEntity stock, Double cantidad, String tipo) {

    MovimientoStock movimiento = new MovimientoStock();
    movimiento.setStock(stock); // Vincular el movimiento con el stock
    movimiento.setCantidad(cantidad);
    movimiento.setTipo(tipo);
    movimiento.setFecha(LocalDateTime.now());

    return movimientoStockRepository.save(movimiento);
    
    }

    public List<MovimientoStock> obtenerHistorialMovimientos(String productoId) {
        
        StockEntity stock = stockRepository.findByProductoId(productoId);

        return movimientoStockRepository.findByStockId(stock.getId());
    }

}

    
