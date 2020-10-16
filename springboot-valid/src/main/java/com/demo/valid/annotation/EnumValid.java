package com.demo.valid.annotation;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

@Documented
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {EnumValid.Validator.class})
public @interface EnumValid {

    String message() default "{validation.EnumValid.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * 允许的枚举
     * @return
     */
    Class<? extends Enum<?>> enumClass();

    /**
     * 枚举校验的方法
     */
    String method() default "isValidEnum";

    /**
     * 是否允许为空
     * @return
     */
    boolean allowNull() default true;

    class Validator implements ConstraintValidator<EnumValid,Object> {

        private Class<? extends Enum<?>> enumClass;
        private String enumMethod;
        private boolean allowNull;

        @Override
        public void initialize(EnumValid enumValid) {
            enumMethod = enumValid.method();
            enumClass = enumValid.enumClass();
            allowNull = enumValid.allowNull();
        }

        @Override
        public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
            if(allowNull) {
                return true;
            }
            if (null == value) {
                return false;
            }
            if (null == enumClass || null == enumMethod) {
                return false;
            }
            Class<?> valueClass = value.getClass();
            try {
                Method method = enumClass.getMethod(enumMethod, valueClass);
                if (!Boolean.TYPE.equals(method.getReturnType()) && !Boolean.class.equals(method.getReturnType())) {
                    throw new RuntimeException(" method return is not boolean type in the " + enumClass + " class");
                }
                if(!Modifier.isStatic(method.getModifiers())) {
                    throw new RuntimeException(method + " method is not static method in the " + enumClass + " class");
                }
                Boolean result = (Boolean) method.invoke(null, value);
                return result == null ? false : result;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }
    }

}
