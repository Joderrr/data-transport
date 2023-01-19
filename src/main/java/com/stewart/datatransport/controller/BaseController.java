package com.stewart.datatransport.controller;

import com.stewart.datatransport.annotation.ParameterValid;
import com.stewart.datatransport.enums.ErrorCode;
import com.stewart.datatransport.enums.reflection.MethodType;
import com.stewart.datatransport.exception.ValidException;
import com.stewart.datatransport.pojo.vo.GeneralResponse;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Base class of controllers
 * include general success and fail methods
 *
 * @author stewart
 * @date 2023/1/17
 */
@Slf4j
public class BaseController {

    /**
     * controller business logic execute method
     *
     * @param resp     general response
     * @param consumer lambda method consumer
     * @return general response
     */
    protected GeneralResponse execute(GeneralResponse resp, Consumer<GeneralResponse> consumer) {
        consumer.accept(resp);
        return resp;
    }

    /**
     * controller business logic execute method
     *
     * @param supplier lambda method consumer
     * @return general response
     */
    protected GeneralResponse execute(Supplier<GeneralResponse> supplier) {
        return supplier.get();
    }

    /**
     * parameter validate by using java reflection and annotation ParameterValid.class
     *
     * @param t parameter
     * @param <T>   parameterType
     * @throws ValidException   validate fail will throws validException
     */
    protected <T> void parameterValidate(T t) throws ValidException {
        try {
            Method[] declaredMethods = t.getClass().getDeclaredMethods();
            Field[] declaredFields = t.getClass().getDeclaredFields();
            for (Field declaredField : declaredFields) {
                if (declaredField.isAnnotationPresent(ParameterValid.class)) {
                    ParameterValid annotation = declaredField.getAnnotation(ParameterValid.class);
                    Optional<Method> getterOptional = Arrays.stream(declaredMethods)
                            .filter(m -> m.getName().equals(methodNameAssemble(MethodType.GETTER, declaredField.getName())))
                            .findFirst();
                    if (getterOptional.isPresent()) {
                        Method getterMethod = getterOptional.get();
                        switch (annotation.validType()) {
                            case NOT_NULL:
                                if (getterMethod.invoke(t) == null) {
                                    throw new ValidException(ErrorCode.PARAMETER_VALID_FAILED, declaredField.getName() + " can not be null");
                                }
                                break;
                            case NUMBER_ONLY:
                                try {
                                    Long.parseLong(String.valueOf(getterMethod.invoke(t)));
                                } catch (NumberFormatException e) {
                                    throw new ValidException(ErrorCode.PARAMETER_VALID_FAILED, declaredField.getName() + " supports number value only");
                                }
                                break;
                            case NONE:
                            default:
                                break;
                        }
                    }
                }
            }
        } catch (InvocationTargetException | IllegalAccessException e) {
            log.error("Parameter valid error: {}", e.getMessage());
        }
    }

    /**
     * getter/setter method name assemble by using field name and method type
     *
     * @param methodType    getter/setter
     * @param fieldName     field name
     * @return  getter/setter method name
     */
    private String methodNameAssemble(MethodType methodType, String fieldName) {
        return methodType.getPrefix() + initialCapitalize(fieldName);
    }

    /**
     * initial capitalize
     *
     * @param fieldName field name
     * @return field name with initial capitalized
     */
    private String initialCapitalize(String fieldName) {
        if (fieldName.length() > 0) {
            char[] chars = fieldName.toCharArray();
            chars[0] = String.valueOf(chars[0]).toUpperCase().toCharArray()[0];
            return String.valueOf(chars);
        }
        return fieldName;
    }

}
