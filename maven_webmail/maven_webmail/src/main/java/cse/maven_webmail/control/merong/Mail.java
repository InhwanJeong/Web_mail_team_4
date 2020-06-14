package merong;

public class Mail {
    private String name;
    private String subject;
    private String body;

    public Mail(String name, String subject, String body){
        this.name = name;
        this.subject = subject;
        this.body = body;

    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
