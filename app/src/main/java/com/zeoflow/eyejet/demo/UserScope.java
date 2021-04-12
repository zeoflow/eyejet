package com.zeoflow.eyejet.demo;

import com.zeoflow.eyejet.annotation.EyejetScope;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@EyejetScope
@Retention(RUNTIME)
public @interface UserScope
{
}
