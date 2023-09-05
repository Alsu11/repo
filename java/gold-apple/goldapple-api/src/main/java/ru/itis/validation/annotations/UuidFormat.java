package ru.itis.validation.annotations;

import ru.itis.validation.validators.CardValidator;
import ru.itis.validation.validators.UuidValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UuidValidator.class)
public @interface UuidFormat {
    String message() default "UUID";

    String uuid() default "";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
