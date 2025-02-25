package com.triana.salesianos.dam.lagrada.validation;
import com.triana.salesianos.dam.lagrada.model.Equipo;
import com.triana.salesianos.dam.lagrada.repo.EquipoRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

public class UniqueNombreValidator implements ConstraintValidator<UniqueNombre, String> {

    @Autowired
    private EquipoRepository repo;

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return StringUtils.hasText(s) && !repo.existsByNombre(s);
    }
}