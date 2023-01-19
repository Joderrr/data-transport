package com.stewart.datatransport.pojo.vo.database;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * databases address and port
 *
 * @author stewart
 * @date 2023/1/17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressAndPort {

    /**
     * ip address of database
     */
    String ip;

    /**
     * port of database
     */
    String port;
}
