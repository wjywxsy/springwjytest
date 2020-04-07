package com.wjyxsy.Annotation;

import java.lang.annotation.*;

@Target(value = ElementType.METHOD)
@Documented
@Retention(value = RetentionPolicy.RUNTIME)
public @interface MyTranscation {
}
