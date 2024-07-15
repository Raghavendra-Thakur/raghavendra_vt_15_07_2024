package com.vt.project.utils;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Permission {
    PermissionsEnum[] permissions();
    LogicEnum type();
}