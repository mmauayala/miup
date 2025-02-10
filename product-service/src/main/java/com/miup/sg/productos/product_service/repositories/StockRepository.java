package com.miup.sg.productos.product_service.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.miup.sg.productos.product_service.models.productos.entity.StockEntity;

@Repository
public interface StockRepository extends CrudRepository<StockEntity, Long> {

    StockEntity findByProductoId(String productoId);

}
