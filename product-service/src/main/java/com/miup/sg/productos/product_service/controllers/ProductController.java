package com.miup.sg.productos.product_service.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.miup.sg.productos.product_service.entities.Product;
import com.miup.sg.productos.product_service.services.ProductService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/v1/productos")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping("/lista")
    public List<Product> list() {
        return service.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@RequestParam Long id) {
        
        Optional<Product> productOptional = service.findById(id);
        if (productOptional.isPresent()) {
            return ResponseEntity.ok(productOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();

    }

    @PostMapping
    public ResponseEntity<?> create (@Valid @RequestBody Product product, BindingResult result){
        
        if (result.hasFieldErrors()) {
            return validation(result);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(product)); 
    
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> update ( @Valid @RequestBody Product product, BindingResult result, @PathVariable Long id) {
        
        if (result.hasFieldErrors()) {  
            return validation(result);
        }
        Optional<Product> productOptional = service.update(id, product);
        if (productOptional.isPresent()) {

            return ResponseEntity.status(HttpStatus.CREATED).body(productOptional.orElseThrow()); 
    
        }
        return ResponseEntity.notFound().build();
        
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete (@PathVariable Long id){

        Optional<Product> productOptional = service.delete(id);

        if (productOptional.isPresent()) {
            return ResponseEntity.ok(productOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();

    }

    private ResponseEntity<?> validation(BindingResult result) {

        Map<String, String> errors = new HashMap<>();

        result.getFieldErrors().forEach(error -> {
            errors.put(error.getField(), "El campo " + error.getField() + " " + error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
        

    }
}
