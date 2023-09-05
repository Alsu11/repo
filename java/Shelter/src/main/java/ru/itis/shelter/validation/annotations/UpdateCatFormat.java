package ru.itis.shelter.validation.annotations;

import ru.itis.shelter.validation.validators.UpdateCatValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UpdateCatValidator.class)
public @interface UpdateCatFormat {
    String message() default "Неккоректный ввод";

    String state() default "";

    String health() default "";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
