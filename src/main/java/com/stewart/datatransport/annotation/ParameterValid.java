package com.stewart.datatransport.annotation;

import com.stewart.datatransport.enums.ValidType;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * annotation of parameter's field validate
 *
 * @author stewart
 * @date 2023/1/19
 */
@Target({FIELD})
@Retention(RUNTIME)
public @interface ParameterValid {

    ValidType validType() default ValidType.NONE;

}
