package com.stewart.datatransport;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author stewart
 */
@ComponentScan(basePackages = "com.stewart.datatransport.*")
@MapperScan(basePackages = "com.stewart.datatransport.mapper")
@SpringBootApplication
public class DataTransportApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataTransportApplication.class, args);
	}

}
