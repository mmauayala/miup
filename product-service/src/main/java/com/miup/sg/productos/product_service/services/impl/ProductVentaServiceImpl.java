package com.miup.sg.productos.product_service.services.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miup.sg.productos.product_service.models.productos.entity.MovimientoStock;
import com.miup.sg.productos.product_service.models.productos.entity.StockEntity;
import com.miup.sg.productos.product_service.models.productos.entity.VentaEntity;
import com.miup.sg.productos.product_service.repositories.StockRepository;
import com.miup.sg.productos.product_service.repositories.VentaRepository;
import com.miup.sg.productos.product_service.services.ProductMovimientoService;
import com.miup.sg.productos.product_service.services.ProductPromocionService;
import com.miup.sg.productos.product_service.services.ProductVentaService;

@Service
public class ProductVentaServiceImpl implements ProductVentaService{

    @Autowired
    private StockRepository stockRepository;
    
    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private ProductMovimientoService productMovService;
    
    @Autowired
    private ProductPromocionService promocionService;

    @Override
    public VentaEntity ventaUnitaria(String productoId, Double cantidadVendida, String transaccionId){

        StockEntity stock = stockRepository.findByProductoId(productoId);

        if (stock == null) {
            throw new RuntimeException("No se encontró stock para el producto con ID " + productoId);
        }

        if (stock.getCantidad() < cantidadVendida) {
            throw new RuntimeException("Stock insuficiente");
        }

        Double precioUnitario = stock.getPrecioVenta();
        
        Double precioFinal = promocionService.aplicarPromociones(productoId, cantidadVendida, precioUnitario);

        VentaEntity venta = new VentaEntity();

        venta.setStock(stock);
        venta.setCantidadVendida(cantidadVendida);
        venta.setPrecioVenta(precioFinal);
        venta.setFechaVenta(LocalDateTime.now());
        venta.setTransaccionId(transaccionId);
    
        stock.setCantidad(stock.getCantidad() - cantidadVendida);
        stockRepository.save(stock);
    
        MovimientoStock movimiento = productMovService.registrarMovimientoStock(stock, cantidadVendida, "VENTA");

        venta.setMovimiento(movimiento);

        return ventaRepository.save(venta);

        
    }

    public List<VentaEntity> registrarVenta(Map<String, Double> productosYCantidades){
        String transaccionId = UUID.randomUUID().toString(); // Generar un ID único para la transacción
        List<VentaEntity> ventas = new ArrayList<>();

        for (Map.Entry<String, Double> entry : productosYCantidades.entrySet()) {
            String productoId = entry.getKey();
            Double cantidadVendida = entry.getValue();

            // Registrar la venta individual
            VentaEntity venta = ventaUnitaria(productoId, cantidadVendida, transaccionId);
            System.out.println("Guardando venta con transaccionId: " + venta.getTransaccionId());
            ventas.add(venta);
        }

        return ventas;
    }
   
}
