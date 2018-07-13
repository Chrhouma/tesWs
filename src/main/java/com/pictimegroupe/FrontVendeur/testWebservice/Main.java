package com.pictimegroupe.FrontVendeur.testWebservice;

import com.pictimegroupe.FrontVendeur.testWebservice.services.UserServices;
import com.pictimegroupe.FrontVendeur.testWebservice.services.UserServicesImpl;
import com.pictimegroupe.FrontVendeur.testWebservice.services.WebServicesServiceImpl;
import com.pictimegroupe.FrontVendeur.testWebservice.services.WebServicesServices;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@SpringBootApplication
public class Main {
	public static UserServices userServices= new UserServicesImpl();
	public static WebServicesServices webServicesServices= new WebServicesServiceImpl();


	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}
}

