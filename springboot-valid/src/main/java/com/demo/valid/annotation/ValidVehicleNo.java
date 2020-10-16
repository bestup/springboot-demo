package com.demo.valid.annotation;

import com.demo.valid.util.ValidUtil;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidVehicleNo.Validator.class)
public @interface ValidVehicleNo {

    String message() default "车牌号有误";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class Validator implements ConstraintValidator<ValidVehicleNo, Object> {

        @Override
        public boolean isValid(Object obj, ConstraintValidatorContext constraintValidatorContext) {
            String vehicleNo = obj.toString();
            return ValidUtil.checkVehicleNo(vehicleNo);
        }
    }

}
