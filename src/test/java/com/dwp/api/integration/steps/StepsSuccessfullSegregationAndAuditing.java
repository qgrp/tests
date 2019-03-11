package com.dwp.api.integration.steps;

import com.dwp.api.integration.NotificationIntegrationTest;
import com.dwp.api.model.Notification;
import com.dwp.api.model.NotificationAuditLog;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.http.HttpResponse;
import org.joda.time.DateTime;

import java.util.List;

import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.util.Assert.notNull;


public class StepsSuccessfullSegregationAndAuditing extends NotificationIntegrationTest {


    private HttpResponse response = null;
    Notification notificationObject;
    NotificationAuditLog notificationAuditLog;
    @Given("^There exists valid documenttype, documentAuthority to notify \"([^\"]*)\"\"([^\"]*)\"\"([^\"]*)\"\"([^\"]*)\"$")
    public void there_exists_valid_documenttype_documentAuthority_to_notify(String id, String dtype, String duath, String dcont) throws Throwable {
        this.truncateAuditDB();
        notificationObject = new Notification();
        notificationObject.setDocumentAuthority(duath);
        notificationObject.setDocumentContent(dcont);
        notificationObject.setDocumentId(id);
        notificationObject.setDocumentType(dtype);
        String notifyLog = "{\n" +
                "  \"reference\" : \""+id+"\",\n" +
                "  \"type\" : \""+dtype+"\",\n" +
                "  \"authority\" : \""+duath+"\",\n" +
                "  \"content\" : \""+dcont+"\"\n" +
                "}";
        notificationAuditLog = new NotificationAuditLog();
        notificationAuditLog.setNotification(notifyLog);
    }

    @When("^We notify of the docunent$")
    public void we_notify_of_the_docunent() throws Throwable {
        response = this.notifyDocument(notificationObject);
    }

    @Then("^We get a 'OK' Http Response Code$")
    public void we_get_a_OK_Http_Response_Code() throws Throwable {
        assertEquals ("Ok with not content",204,response.getStatusLine().getStatusCode());
    }

    @Then("^We find a NOTIFICATION queue in the cbr Exchange$")
    public void we_find_a_NOTIFICATION_queue_in_the_cbr_Exchange() throws Throwable {
//  we need to call the rabbit mq Api to get these values .Skipping due to lack of time http://localhost:15672/api/index.html
    }

    @Then("^We find a NOTIFICATION\\.LA(\\d+)\\.UC(\\d+) queue in the cbr Exchange$")
    public void we_find_a_NOTIFICATION_LA_UC_queue_in_the_cbr_Exchange(int arg1, int arg2) throws Throwable {
//  we need to call the rabbit mq Api to get these values .Skipping due to lack of time http://localhost:15672/api/index.html
    }

    @Then("^We find the message has been logged in the audit database$")
    public void we_find_the_message_has_been_logged_in_the_audit_database() throws Throwable {
        Thread.sleep(10000);//This test executes before the actual functionality
        List<NotificationAuditLog> notificationAuditLogs = this.findAllFromAuditDB();
        notNull(notificationAuditLogs);
        assertEquals("should be of size 1",1,notificationAuditLogs.size());
        assertEquals("Audit String", notificationAuditLog.getNotification(),notificationAuditLogs.get(0).getNotification());

    }


}
