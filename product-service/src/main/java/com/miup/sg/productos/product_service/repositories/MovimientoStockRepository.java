package com.miup.sg.productos.product_service.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.miup.sg.productos.product_service.models.productos.entity.MovimientoStock;

import java.util.List;

@Repository
public interface MovimientoStockRepository extends CrudRepository<MovimientoStock, Long> {

    List<MovimientoStock> findByStockId(Long stockId); 

}