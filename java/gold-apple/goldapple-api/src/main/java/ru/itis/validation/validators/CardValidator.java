package ru.itis.validation.validators;

import ru.itis.validation.annotations.CardFormat;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CardValidator implements ConstraintValidator<CardFormat, Object> {

    private final String cvv_regex = "^[0-9]{3}$";
    private final String expiration_regex = "^((0[1-9])|(1[0-2]))\\/((2[2-9])|(3[0-2]))$";
    private final String username_regex = "^[A-Z]\\s[A-Z]$";

    private String cvv;
    private String expiration;
    private String username;

    @Override
    public void initialize(CardFormat constraintAnnotation) {
        this.cvv = constraintAnnotation.cvv();
        this.expiration = constraintAnnotation.expiration();
        this.username = constraintAnnotation.username();

    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        Pattern cvv_pattern = Pattern.compile(cvv_regex);
        Matcher cvv_matcher = cvv_pattern.matcher(cvv);

        Pattern expiration_pattern = Pattern.compile(expiration_regex);
        Matcher expiration_matcher = expiration_pattern.matcher(expiration);

        Pattern username_pattern = Pattern.compile(username_regex);
        Matcher username_matcher = username_pattern.matcher(username);

        return cvv_matcher.matches() &&
                expiration_matcher.matches() &&
                username_matcher.matches();
    }
}
