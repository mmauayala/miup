package com.miup.sg.productos.product_service.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.miup.sg.productos.product_service.models.productos.entity.PromocionEntity;

public interface PromocionRepository extends CrudRepository<PromocionEntity, Long> {

    List<PromocionEntity> findByProductoIdAndFechaInicioBeforeAndFechaFinAfterAndActivaTrue(
        String productoId, LocalDate fechaInicio, LocalDate fechaFin);

}