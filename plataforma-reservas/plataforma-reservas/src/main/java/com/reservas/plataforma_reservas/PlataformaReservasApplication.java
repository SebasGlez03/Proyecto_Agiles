package com.reservas.plataforma_reservas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages = {"reservas.controller"})
public class PlataformaReservasApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlataformaReservasApplication.class, args);
                
	}

}
