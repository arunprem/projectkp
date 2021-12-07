package com.keralapolice.projectk.config.validation;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = CheckUniqnessValidator.class)
@Target({ FIELD })
@Retention(RUNTIME)
public @interface CheckUniqness {
    String message() default "{must.be.unique}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
