package ru.itis.validation.annotations;

import ru.itis.validation.validators.CardValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CardValidator.class)
public @interface CardFormat {

    String message() default "Cvv код на обороте карты";

    String cvv() default "";

    String expiration() default "";

    String username() default "";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
