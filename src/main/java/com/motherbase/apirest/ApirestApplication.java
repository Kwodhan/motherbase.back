package com.motherbase.apirest;

import com.motherbase.apirest.config.WebSecurity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@ComponentScan(basePackages = {"com.motherbase.apirest.controller", "com.motherbase.apirest.service"})
@Import({WebSecurity.class})
@EntityScan("com.motherbase.apirest.model")
@EnableJpaRepositories("com.motherbase.apirest.repository")
public class ApirestApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApirestApplication.class, args);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

