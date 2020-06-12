package cse.maven_webmail.model;

import java.util.ArrayList;

public class MessageForm {
    private String toAddress;
    private String fromAddress;
    private String ccAddress;
    private String sentDate;
    private String subject;
    private String body;

    public MessageForm(ArrayList<String> message){
        this.toAddress = message.get(0);
        this.fromAddress = message.get(1);
        this.ccAddress = message.get(2);
        this.sentDate = message.get(3);
        this.subject = message.get(4);
        this.body = message.get(5);
    }

    public String getToAddress() {
        return toAddress;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public String getCcAddress() {
        return ccAddress;
    }

    public String getSentDate() {
        return sentDate;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }
}
