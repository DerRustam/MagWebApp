package com.rustam.magbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.rustam.magbackend.model")
@EnableJpaRepositories("com.rustam.magbackend.repository")
public class MagWebBackendApplication {//extends SpringBootServletInitializer {

    /*@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder){
        return builder.sources(MagWebBackendApplication.class);
    }*/

    public static void main(String[] args) {
        SpringApplication.run(MagWebBackendApplication.class, args);
    }

}
