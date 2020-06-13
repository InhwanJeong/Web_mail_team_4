package cse.maven_webmail.model;

import java.util.List;

public class MessageForm {
    private String toAddress;
    private String fromAddress;
    private String ccAddress;
    private String sentDate;
    private String subject;
    private String body;
    private String fileName;

    public MessageForm(List<String> message){
        this.fromAddress = message.get(0);
        this.toAddress = message.get(1);
        this.ccAddress = message.get(2);
        this.sentDate = message.get(3);
        this.subject = message.get(4);
        this.body = message.get(5);
        this.fileName = message.get(6);
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

    public String getFileName() {
        return fileName;
    }
}
