package org.fullstack;

import org.fullstack.annotation.EnableReference;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableReference(basePackages = "org.fullstack.service")
@SpringBootApplication
public class ReferenceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReferenceApplication.class, args);
    }
}
