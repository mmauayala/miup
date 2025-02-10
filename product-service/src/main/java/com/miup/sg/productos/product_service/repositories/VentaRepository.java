package com.miup.sg.productos.product_service.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.miup.sg.productos.product_service.models.productos.entity.VentaEntity;

public interface VentaRepository extends CrudRepository<VentaEntity, Long> {

    List<VentaEntity> findByTransaccionId(String transaccionId);

}