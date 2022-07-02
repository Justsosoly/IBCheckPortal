package com.ib.ibcheckweb;

import org.jfree.chart.servlet.DisplayChart;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@MapperScan("com.ib.ibcheckweb.mapper")
public class IbcheckportalApplication {

	public static void main(String[] args) {
		SpringApplication.run(IbcheckportalApplication.class, args);
	}

    @Bean
    public ServletRegistrationBean Servlet() {
        return new ServletRegistrationBean<>(new DisplayChart(),"/chart");
    }
	
}
