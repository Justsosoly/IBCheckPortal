package com.ib.ibcheckweb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ib.ibcheckweb.mapper")
public class IbcheckportalApplication {

	public static void main(String[] args) {
		SpringApplication.run(IbcheckportalApplication.class, args);
	}

}
