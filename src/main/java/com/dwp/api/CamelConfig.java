package com.dwp.api;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.jpa.JpaComponent;
import org.apache.camel.spring.javaconfig.CamelConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import java.util.Properties;

@Slf4j
@Configuration
@EnableAutoConfiguration(exclude=DataSourceAutoConfiguration.class)
@ComponentScan(basePackages = {
        "com.dwp.api.controller",
        "com.dwp.api.dao",
        "com.dwp.api.route"})
public class CamelConfig  extends CamelConfiguration{

    @Autowired
    @Qualifier("jpa")
    JpaComponent jpaComponent;

    @Override
    protected void setupCamelContext(CamelContext camelContext) throws Exception {
        camelContext.addComponent("jpa", jpaComponent);
    }
    @Bean
    public ProducerTemplate getProducerTemplate(CamelContext camelContext) throws Exception {
        return camelContext.createProducerTemplate();
    }

}