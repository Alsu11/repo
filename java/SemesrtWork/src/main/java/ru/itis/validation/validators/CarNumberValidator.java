package ru.itis.validation.validators;

import ru.itis.validation.annotations.CorrectCarNumber;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CarNumberValidator implements ConstraintValidator<CorrectCarNumber, Object> {

    private String carNumber;
    @Override
    public void initialize(CorrectCarNumber constraintAnnotation) {
        this.carNumber = constraintAnnotation.carNumber();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {
        this.carNumber = (String) object;
        String regex = "[a-z]\\d{3}[a-z]{2}\\d{2,3}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(carNumber);
        return matcher.matches();
    }
}
