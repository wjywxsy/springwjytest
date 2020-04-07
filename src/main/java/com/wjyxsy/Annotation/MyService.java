package com.wjyxsy.Annotation;

import java.lang.annotation.*;

@Target(value = ElementType.TYPE)
@Documented
@Retention(value = RetentionPolicy.RUNTIME)
public @interface MyService {
    String value() default "";
}
