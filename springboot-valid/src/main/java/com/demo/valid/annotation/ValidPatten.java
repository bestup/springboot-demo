package com.demo.valid.annotation;

import com.demo.valid.enums.PatternType;
import com.demo.valid.util.ValidUtil;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;
import java.util.regex.Pattern;

@Documented
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidPatten.Validator.class)
public @interface ValidPatten {

    String message();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    //自定义检验属性
    PatternType patternType();

    class Validator implements ConstraintValidator<ValidPatten, Object> {

        private PatternType patternType;

        @Override
        public void initialize(ValidPatten constraintAnnotation) {
            this.patternType = constraintAnnotation.patternType();
        }

        @Override
        public boolean isValid(Object obj, ConstraintValidatorContext constraintValidatorContext) {
            if(obj.equals(patternType.TELEPHONE)) {
                return ValidUtil.checkPhone(obj.toString());
            }
            if(obj.equals(patternType.VEHICLENO)) {
                return ValidUtil.checkVehicleNo(obj.toString());
            }
            return false;
        }
    }


}
