/*
 * File: MessageParser.java
 * Goal: 메일 메시지의 정보 추출(To, Cc, From, Subject, Body, Attached File)
 */
package cse.maven_webmail.model;

import java.io.File;
import java.io.FileOutputStream;
import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.MimeUtility;

/**
 *
 * @author jongmin
 */
public class MessageParser {

    private Message message;
    private String toAddress;
    private String fromAddress;
    private String ccAddress;
    private String sentDate;
    private String subject;
    private String body;
    private String fileName;
    private final String downloadTempDir = "C:/temp/download/";
    private String userid;

    public MessageParser(Message message, String userid) {
        this.message = message;
        this.userid = userid;
    }

    public boolean parse(boolean parseBody) {
        boolean status = false;

        try {
            getEnvelope(message);
            if (parseBody) {
                getPart(message);
            }
            printMessage(parseBody);
            //  예외가 발생하지 않았으므로 정상적으로 동작하였음.
            status = true;
        } catch (Exception ex) {
            System.out.println("MessageParser.parse() - Exception : " + ex);
            status = false;
        } finally {
            return status;
        }
    }

    private void getEnvelope(Message m) throws Exception {
        fromAddress = message.getFrom()[0].toString();  // 101122 LJM : replaces getMyFrom2()
        toAddress = getAddresses(message.getRecipients(Message.RecipientType.TO));
        Address[] addr = message.getRecipients(Message.RecipientType.CC);
        if (addr != null) {
            ccAddress = getAddresses(addr);
        } else {
            ccAddress = "";
        }
        subject = message.getSubject();
        sentDate = message.getSentDate().toString();
        sentDate = sentDate.substring(0, sentDate.length() - 8);  // 8 for "KST 20XX"
    }

    // ref: http://www.oracle.com/technetwork/java/faq-135477.html#readattach
    private void getPart(Part p) throws Exception {
        String disp = p.getDisposition();

        if (disp != null && (disp.equalsIgnoreCase(Part.ATTACHMENT)
                || disp.equalsIgnoreCase(Part.INLINE))) {  // 첨부 파일
//            fileName = p.getFileName();
            fileName = MimeUtility.decodeText(p.getFileName());
//            fileName = fileName.replaceAll(" ", "%20");
            if (fileName != null) {
                System.out.println("MessageParser.getPart() : file = " + fileName);
                // 첨부 파일을 서버의 내려받기 임시 저장소에 저장
                String tempUserDir = this.downloadTempDir + this.userid;
                File dir = new File(tempUserDir);
                if (!dir.exists()) {  // tempUserDir 생성
                    dir.mkdir();
                }

                String filename = MimeUtility.decodeText(p.getFileName());
                // 파일명에 " "가 있을 경우 서블릿에 파라미터로 전달시 문제 발생함.
                // " "를 모두 "_"로 대체함.
//                filename = filename.replaceAll("%20", " ");
                DataHandler dh = p.getDataHandler();
                FileOutputStream fos = new FileOutputStream(tempUserDir + "/" + filename);
                dh.writeTo(fos);
                fos.flush();
                fos.close();
            }
        } else {  // 메일 본문
            if (p.isMimeType("text/*")) {
                body = (String) p.getContent();
                if (p.isMimeType("text/plain")) {
                    body = body.replaceAll("\r\n", " <br>");
                }
            } else if (p.isMimeType("multipart/alternative")) {
                // html text보다  plain text 선호
                Multipart mp = (Multipart) p.getContent();
                for (int i = 0; i < mp.getCount(); i++) {
                    Part bp = mp.getBodyPart(i);
                    if (bp.isMimeType("text/plain")) {  // "text/html"도 있을 것임.
                        getPart(bp);
                    }
                }
            } else if (p.isMimeType("multipart/*")) {
                Multipart mp = (Multipart) p.getContent();
                for (int i = 0; i < mp.getCount(); i++) {
                    getPart(mp.getBodyPart(i));
                }
            }
        }
    }

    private void printMessage(boolean printBody) {
        System.out.println("From: " + fromAddress);
        System.out.println("To: " + toAddress);
        System.out.println("CC: " + ccAddress);
        System.out.println("Date: " + sentDate);
        System.out.println("Subject: " + subject);

        if (printBody) {
            System.out.println("본 문");
            System.out.println("---------------------------------");
            System.out.println(body);
            System.out.println("---------------------------------");
            System.out.println("첨부파일: " + fileName);
        }
    }

    private String getAddresses(Address[] addresses) {
        StringBuilder buffer = new StringBuilder();

        for (Address address : addresses) {
            buffer.append(address.toString());
            buffer.append(", ");
        } // 마지막에 있는 ", " 삭제
        int start = buffer.length() - 2;
        int end = buffer.length() - 1;
        buffer.delete(start, end);
        return buffer.toString();
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getCcAddress() {
        return ccAddress;
    }

    public void setCcAddress(String ccAddress) {
        this.ccAddress = ccAddress;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getSentDate() {
        return sentDate;
    }

    public void setSentDate(String sentDate) {
        this.sentDate = sentDate;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }
}
