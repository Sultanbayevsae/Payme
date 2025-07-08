package org.example.payme.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidAboutHandler implements ConstraintValidator<ValidAbout, String> {

    private int min = 0;
    @Override
    public void initialize(ValidAbout constraintAnnotation) {
        min = constraintAnnotation.min();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (!s.isEmpty() && countProbel(s)) {
            return true;
        }

        String customMessage = "Error: invalid about value";
    constraintValidatorContext.disableDefaultConstraintViolation();
    constraintValidatorContext.buildConstraintViolationWithTemplate(customMessage).addConstraintViolation();
    return false;

    }
    private boolean countProbel(String str){
        String[] words = str.split(" ");
        return words.length==min;
    }

}
