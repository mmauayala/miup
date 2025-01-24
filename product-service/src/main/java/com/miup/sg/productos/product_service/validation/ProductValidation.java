package com.miup.sg.productos.product_service.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.miup.sg.productos.product_service.entities.Product;

@SuppressWarnings("null")
@Component
public class ProductValidation implements Validator {

    
    @Override
    public boolean supports(Class<?> clazz) {
        return Product.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Product product = (Product) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nombre", null, "es requerido!");
        // ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "NotBlank.product.description");
        if (product.getMedida() == null || product.getMedida().isBlank()) {
            errors.rejectValue("medida", null, "es requerido, por favor");
        }

    }
    
}