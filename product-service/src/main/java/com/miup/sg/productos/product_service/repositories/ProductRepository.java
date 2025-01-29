package com.miup.sg.productos.product_service.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.miup.sg.productos.product_service.models.productos.Product;
import com.miup.sg.productos.product_service.models.productos.entity.ProductEntity;

@Repository
public interface ProductRepository extends CrudRepository<ProductEntity, String> {

    boolean existsProductEntityByName(final String nombre);

    Page<Product> findAll(Pageable pageable);

}
