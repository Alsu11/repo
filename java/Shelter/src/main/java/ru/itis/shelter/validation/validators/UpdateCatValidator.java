package ru.itis.shelter.validation.validators;

import ru.itis.shelter.validation.annotations.UpdateCatFormat;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class UpdateCatValidator implements ConstraintValidator<UpdateCatFormat, Object> {

    private final List<String> HEALTH = Arrays.asList("ILL", "HEALTH", "UNCLEAR");

    private final List<String> STATE = Arrays.asList("ON_HANDS", "ON_SHELTER", "ON_HOSPITAL");

    private String state;
    private String health;

    @Override
    public void initialize(UpdateCatFormat constraintAnnotation) {
        this.state = constraintAnnotation.state();
        this.health = constraintAnnotation.health();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        return !(HEALTH.contains(health) && STATE.contains(state));
    }
}
