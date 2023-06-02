package com.example.eksamentemplate.exception;

import java.util.Date;

public class ErrorMessage {
    //laver vores egen fejlmeddelelse, returneres til front end

    //ting til fejlkoden
    private int statusCode; //http statuskode
    private Date timeStamp; //hvornår gik det galt
    private String message; //selve beskeden

    public ErrorMessage(int statusCode, Date timeStamp, String message) {
        this.statusCode = statusCode;
        this.timeStamp = timeStamp;
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
