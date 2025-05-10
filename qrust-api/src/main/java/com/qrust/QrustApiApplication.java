package com.qrust;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class QrustApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(QrustApiApplication.class, args);
    }

}
