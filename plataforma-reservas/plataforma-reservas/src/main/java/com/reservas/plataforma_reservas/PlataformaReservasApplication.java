package com.reservas.plataforma_reservas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@ComponentScan(basePackages = {"reservas"})
@EnableJpaRepositories(basePackages = "reservas.repository")
@EntityScan(basePackages = "reservas.model")
public class PlataformaReservasApplication {

    public static void main(String[] args) {
            SpringApplication.run(PlataformaReservasApplication.class, args);

    }

}
