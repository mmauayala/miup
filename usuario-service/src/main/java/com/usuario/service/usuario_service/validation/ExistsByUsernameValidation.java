package com.usuario.service.usuario_service.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


@Component
public class ExistsByUsernameValidation implements ConstraintValidator<ExistsByUsername, String> {

    @Autowired
    private com.usuario.service.usuario_service.services.UserService service;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
    
        if (service == null) {
            return true;
        }

        return !service.existsByUsername(username);
    }


}