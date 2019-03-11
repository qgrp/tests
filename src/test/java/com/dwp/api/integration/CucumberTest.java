package com.dwp.api.integration;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;


@RunWith(Cucumber.class)
@CucumberOptions( features = "src/test/resources",format = { "pretty","html: cucumber-html-reports",
        "json: cucumber-html-reports/cucumber.json" },
        glue = "com.dwp.api.integration.steps" )
public class CucumberTest {
}
