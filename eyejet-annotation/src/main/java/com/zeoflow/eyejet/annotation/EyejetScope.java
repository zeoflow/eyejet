package com.zeoflow.eyejet.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * EyejetScope is an annotation for building a lifecycle aware data holder
 * based on custom scopes.
 * <p>
 * EyejetScope It should be annotated a class (activity, fragment, repository or any classes)
 * that has EyejetField fields.
 */
@Target(TYPE)
@Retention(RUNTIME)
public @interface EyejetScope
{
}
