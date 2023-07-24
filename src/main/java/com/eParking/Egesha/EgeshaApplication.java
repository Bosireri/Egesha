package com.eParking.Egesha;

import com.eParking.Egesha.model.LocalUser;
import com.eParking.Egesha.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
//		(exclude = {
//		org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
//		org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration.class}
//)
public class EgeshaApplication {

	public static void main(String[] args) {
		SpringApplication.run(EgeshaApplication.class, args);
	}
}
