package ru.itis.usersservice.validation.annotations;

import ru.itis.usersservice.validation.validators.CustomPasswordValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Documented
@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CustomPasswordValidator.class)
public @interface CustomPassword {
    String message() default "Wrong Password";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
