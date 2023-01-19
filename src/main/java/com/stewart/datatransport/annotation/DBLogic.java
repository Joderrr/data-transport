package com.stewart.datatransport.annotation;

import com.stewart.datatransport.enums.database.DatabaseType;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * this annotation marks the database execute logic class of different database type like mysql, oracle etc.
 *
 * @author stewart
 * @date 2023/1/18
 */
@Target({TYPE})
@Retention(RUNTIME)
public @interface DBLogic {

    String name() default "";

    DatabaseType type();
}
