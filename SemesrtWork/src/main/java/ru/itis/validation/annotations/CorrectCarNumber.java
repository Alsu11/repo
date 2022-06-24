package ru.itis.validation.annotations;

import ru.itis.validation.validators.CarNumberValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CarNumberValidator.class)
public @interface CorrectCarNumber {
    String message() default "car number";
    String carNumber() default "";
    Class<? extends Payload>[] payload() default {};
    Class<?>[] groups() default { };

}
