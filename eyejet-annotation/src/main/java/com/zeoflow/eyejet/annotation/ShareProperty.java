package com.zeoflow.eyejet.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * ShareProperty annotation used to distinguish explicitly a
 * [EyejetField] property on the [Eyejet]
 * using key name: [value].
 */
@Target(FIELD)
@Retention(RUNTIME)
public @interface ShareProperty
{
    String value();

    ShareType[] shareType() default {ShareType.KEEP_ACTIVE};
}
