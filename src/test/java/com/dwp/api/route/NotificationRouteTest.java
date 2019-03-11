package com.dwp.api.route;

import com.dwp.api.DwpPublicApplication;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.test.spring.CamelSpringDelegatingTestContextLoader;
import org.apache.camel.test.spring.CamelSpringJUnit4ClassRunner;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;


@RunWith(CamelSpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DwpPublicApplication.class}, loader = CamelSpringDelegatingTestContextLoader.class)
@TestPropertySource(locations = "classpath:unit-test-notify.properties")
@Slf4j
@ActiveProfiles("dev")
public class NotificationRouteTest extends AbstractJUnit4SpringContextTests {
}
