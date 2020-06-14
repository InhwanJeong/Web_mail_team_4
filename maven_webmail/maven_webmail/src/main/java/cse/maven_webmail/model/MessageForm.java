package cse.maven_webmail.model;

import java.util.List;

public class MessageForm {
    private String fromAddress;
    private String sentDate;
    private String subject;
    private String body;
    private String fileName;

    public MessageForm(List<String> message){
        this.fromAddress = message.get(0);
        this.subject = message.get(1);
        this.sentDate = message.get(2);
        this.body = message.get(3);
        this.fileName = message.get(4);
    }

    public String getFromAddress() {
        return fromAddress;
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
