package com.dwp.api.processsor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.dwp.api.dto.ErrorResponse;
import com.dwp.api.dto.ErrorStatus;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.script.ScriptException;


@Component
@Slf4j
public class ErrorProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        log.info("processing the error response");
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorCode(ErrorStatus.UNKNOWN_ERROR.getErrorCode());
        errorResponse.setErrorMessage(ErrorStatus.UNKNOWN_ERROR.getErrorMessage());
        ObjectMapper mapper = new ObjectMapper();
        String errorResponseJson = mapper.writeValueAsString(errorResponse);
        exchange.getIn().setBody(errorResponseJson);
        exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, ErrorStatus.UNKNOWN_ERROR.getHttpStatusCode());
        exchange.setOut(exchange.getIn());
        log.info("processed the error response");
    }
}
