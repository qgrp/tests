package com.dwp.api.integration;

import com.dwp.api.DwpPublicApplication;
import com.dwp.api.dao.NotificationAuditLogDAO;
import com.dwp.api.model.Notification;
import com.dwp.api.model.NotificationAuditLog;
import com.dwp.api.repository.NotificationAuditLogRespository;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;



@ContextConfiguration(
        classes = DwpPublicApplication.class
)
@RunWith(SpringJUnit4ClassRunner.class)
@ComponentScan(basePackages = {
        "com.dwp.api.integration"})
@TestPropertySource(locations="classpath:integration-test-notify.properties")
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("dev")
public abstract class NotificationIntegrationTest {


    @LocalServerPort
    int port;
    @Value("${notify.path}")
    private String notifyPath;

    @Autowired
    NotificationAuditLogRespository notificationAuditLogRespository;

    private final HttpClient httpClient = HttpClientBuilder.create().build();

    public  String getNotifyURL() throws Throwable {
        String notifyURL = "http://localhost:"+port+notifyPath;
        return notifyURL;
    }

    public HttpResponse notifyDocument( Notification notificationObject) throws Throwable {
        HttpPost request = new HttpPost(getNotifyURL());
        String json = "{" +
                "    \"documentId\" : \""+notificationObject.getDocumentId()+"\"," +
                "    \"documentType\" : \""+notificationObject.getDocumentType()+"\"," +
                "    \"documentAuthority\" : \""+notificationObject.getDocumentAuthority()+"\"," +
                "    \"documentContent\" :\""+notificationObject.getDocumentContent()+"\"" +
                "}";
        StringEntity entity = new StringEntity(json);
        entity.setContentType("application/json");
        request.setEntity(entity);
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");        request.addHeader("content-type", APPLICATION_JSON.getType());
        HttpResponse response  = httpClient.execute(request);
        return  response;
    }

    public void truncateAuditDB() throws Throwable {

       notificationAuditLogRespository.deleteAll();
    }

    public List<NotificationAuditLog> findAllFromAuditDB() throws Throwable {
       List<NotificationAuditLog> notificationAuditLogs = new ArrayList<>();
       notificationAuditLogRespository.findAll().forEach(notificationAuditLogs::add);
       return notificationAuditLogs;
    }

}
