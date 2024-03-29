package com.yil.contact;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@EnableCaching
@ComponentScan(basePackages = {"com.yil"})
@OpenAPIDefinition(info = @Info(title = "Contact Api", version = "1.0", description = "Yıldırım Information"))
@SpringBootApplication
public class ContactApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ContactApplication.class, args);
        context.start();
    }
}
