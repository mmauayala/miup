package com.miup.sg.productos.product_service.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.miup.sg.productos.product_service.models.productos.entity.DesperdicioEntity;

public interface DesperdicioRepository extends CrudRepository <DesperdicioEntity, Long> {
    
    List<DesperdicioEntity> findByProductoId(String productoId); 
    
}
