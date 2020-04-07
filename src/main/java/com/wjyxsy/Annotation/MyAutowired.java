package com.wjyxsy.Annotation;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Documented
@Retention(value = RetentionPolicy.RUNTIME)
public @interface MyAutowired {
    String value() default "";
}
