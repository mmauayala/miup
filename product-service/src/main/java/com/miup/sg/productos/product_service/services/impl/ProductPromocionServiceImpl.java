package com.miup.sg.productos.product_service.services.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.miup.sg.productos.product_service.exception.ProductNotFoundException;
import com.miup.sg.productos.product_service.models.productos.entity.ProductEntity;
import com.miup.sg.productos.product_service.models.productos.entity.PromocionEntity;
import com.miup.sg.productos.product_service.repositories.ProductRepository;
import com.miup.sg.productos.product_service.repositories.PromocionRepository;
import com.miup.sg.productos.product_service.services.ProductPromocionService;

@Service
public class ProductPromocionServiceImpl implements ProductPromocionService {

    @Autowired
    private PromocionRepository promocionRepository;

    @Autowired
    private ProductRepository productRepository;

    // Aplicar promociones a una venta
    public Double aplicarPromociones(String productoId, Double cantidad, Double precioUnitario) {
        List<PromocionEntity> promociones = promocionRepository.findByProductoIdAndFechaInicioBeforeAndFechaFinAfterAndActivaTrue(
            productoId, LocalDate.now(), LocalDate.now());

        Double precioFinal = cantidad * precioUnitario;

        for (PromocionEntity promocion : promociones) {
            switch (promocion.getTipo()) {
                case "DESCUENTO_PORCENTUAL":
                    precioFinal -= precioFinal * (promocion.getValor() / 100);
                    break;
                case "DESCUENTO_FIJO":
                    precioFinal -= promocion.getValor();
                    break;
                case "CANTIDAD":
                    if (cantidad >= promocion.getCantidadRequerida()) {
                        int vecesAplicable = (int) (cantidad / promocion.getCantidadRequerida());
                        precioFinal -= vecesAplicable * promocion.getCantidadBonificada() * precioUnitario;
                    }
                    break;
            }
        }

        return precioFinal;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PromocionEntity> obtenerTodasLasPromociones() {
        return (List<PromocionEntity>) promocionRepository.findAll();
    }

    // Crear una nueva promoción
    public PromocionEntity crearPromocion(String nombre, String tipo, Double valor, Integer cantidadBonificada,
    Integer cantidadRequerida, LocalDate fechaFin, String productoId) {
        
        final ProductEntity productEntityFromDB = productRepository
                .findById(productoId)
                .orElseThrow(() -> new ProductNotFoundException("With given productID = " + productoId));

        PromocionEntity promocion = new PromocionEntity();

        promocion.setNombre(nombre);
        promocion.setTipo(tipo);
        promocion.setValor(valor);
        promocion.setCantidadBonificada(cantidadBonificada);
        promocion.setCantidadRequerida(cantidadRequerida);
        promocion.setFechaFin(fechaFin);
        promocion.setProducto(productEntityFromDB);
        promocion.setFechaInicio(LocalDate.now());
        promocion.setActiva(true);
        return promocionRepository.save(promocion);
    }


    // Cambiar el estado de una promoción (activa/inactiva)
    public PromocionEntity cambiarEstadoPromocion(Long id, Boolean activa) {
        PromocionEntity promocion = promocionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Promoción no encontrada"));
        promocion.setActiva(activa);
        return promocionRepository.save(promocion);
    }



}
