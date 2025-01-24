package com.miup.sg.productos.product_service.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.miup.sg.productos.product_service.entities.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {


}
