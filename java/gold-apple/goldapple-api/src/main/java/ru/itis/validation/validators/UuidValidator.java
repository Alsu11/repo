package ru.itis.validation.validators;

import ru.itis.validation.annotations.UuidFormat;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UuidValidator implements ConstraintValidator<UuidFormat, Object> {

    private String uuid;

    private final String uuid_regex = "^[0-9a-f]{8}-[0-9a-f]{4}-[0-5][0-9a-f]{3}-[089ab][0-9a-f]{3}-[0-9a-f]{12}$";

    @Override
    public void initialize(UuidFormat constraintAnnotation) {
        this.uuid = constraintAnnotation.uuid();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        this.uuid = (String) value;
        Pattern pattern = Pattern.compile(uuid_regex);
        Matcher matcher = pattern.matcher(uuid);

        return matcher.matches();
    }
}
