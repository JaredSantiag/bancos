package com.jaredsantiag.springcloud.msvc.bancos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MsvcBancosApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcBancosApplication.class, args);
	}

}
