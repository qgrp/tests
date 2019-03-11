package com.dwp.api.dto;

import org.springframework.http.HttpStatus;


public enum ErrorStatus {

    UNKNOWN_ERROR("S00001","Unknown error occurred", HttpStatus.SERVICE_UNAVAILABLE.value());

    String errorCode ;
    String errorMessage;
    int httpStatusCode ;

    ErrorStatus(String errorCode, String errorMessage, int httpStatusCode) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.httpStatusCode = httpStatusCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(int httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public String getErrorMessage() {

        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
