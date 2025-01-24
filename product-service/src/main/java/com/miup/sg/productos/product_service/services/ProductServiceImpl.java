package com.miup.sg.productos.product_service.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.miup.sg.productos.product_service.entities.Product;
import com.miup.sg.productos.product_service.repositories.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository repository; 

    @Transactional(readOnly = true)
    @Override
    public List<Product> findAll() {

        return (List<Product>) repository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Product> findById(Long id) {
        return repository.findById(id);

    }

    @Transactional
    @Override
    public Product save(Product product) {
        return repository.save(product);

    }

    @Transactional
    @Override
    public Optional<Product> update(Long id, Product product) {
        
        Optional<Product> productOptional = repository.findById(id);
        
        if(productOptional.isPresent()) {
            Product productDb = productOptional.orElseThrow(); 
            productDb.setNombre(product.getNombre());
            productDb.setMedida(product.getMedida());
            
            return Optional.of(repository.save(productDb));
        };

        return productOptional;
    }

    @Transactional
    @Override
    public Optional<Product> delete(Long id) {
        
        Optional<Product> productOptional = repository.findById(id);
        
        productOptional.ifPresent(productDb -> {
            repository.delete(productDb);
        });

        return productOptional;
    }
}
