package com.stewart.datatransport.enums.object;

import lombok.Getter;

/**
 * data object's fields type
 *
 * @author stewart
 * @date 2023/1/19
 */
@Getter
public enum DataType {

    /**
     * String , Number(Integer, Long, Short etc.), Decimal(Float, Double)
     */
    String, Number, Decimal
}
