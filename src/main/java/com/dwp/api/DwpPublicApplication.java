package com.dwp.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.*;


@Import({PersistenceContext.class, CamelConfig.class})
@Configuration
@EnableAutoConfiguration(exclude={HibernateJpaAutoConfiguration.class})
@ComponentScan(basePackages = {
        "com.dwp.api.processsor",
        "com.dwp.api.service"})
@PropertySource(value = "classpath:notify.properties", ignoreResourceNotFound = true)
public class DwpPublicApplication {
    public static void main(String[] args) {
        SpringApplication.run(DwpPublicApplication.class, args);
    }

}
