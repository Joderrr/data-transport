package com.stewart.datatransport.annotation;

import java.io.Serializable;
import java.util.function.Function;

/**
 * serializable function
 *
 * @author stewart
 * @date 2023/1/21
 */
@FunctionalInterface
public interface SerializableFunction<P, R> extends Function<P, R>, Serializable {
}
