/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cse.maven_webmail.model;

import cse.maven_webmail.control.CommandType;

import javax.mail.Message;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author jongmin
 */

/**
 * @author taeyong
 * getMessageTable 함수 63Line 메시지 필터링 실행. main_menu.jsp 페이지에서 dropdown 메뉴, 키워드를 읽어와 변수로 전달함.
 * */
public class MessageFormatter {

    private String userid;  // 파일 임시 저장 디렉토리 생성에 필요

    public MessageFormatter(String userid) {
        this.userid = userid;
    }

    public String getMessageTable(Message[] messages, String status, String keyword) {
        StringBuilder buffer = new StringBuilder();
        List<String> messageList = new ArrayList<>();
        List<MessageForm> messageForms = new ArrayList<>();


        // 메시지 제목 보여주기
        buffer.append("<table>");  // table start
        buffer.append("<tr> "
                + " <th> No. </td> "
                + " <th> 보낸 사람 </td>"
                + " <th> 제목 </td>     "
                + " <th> 보낸 날짜 </td>   "
                + " <th> 삭제 </td>   "
                + " </tr>");

        for (int i = messages.length - 1; i >= 0; i--) { // stream 정렬을 위해 List에 메시지 요소 삽입
            MessageParser parser = new MessageParser(messages[i], userid);
            parser.parse(false);  // envelope 정보만 필요

            messageList.add(parser.getFromAddress());
            messageList.add(parser.getToAddress());
            messageList.add(parser.getCcAddress());
            messageList.add(parser.getSentDate());
            messageList.add(parser.getSubject());
            messageList.add(parser.getBody());
            messageList.add(parser.getFileName());

            messageForms.add(new MessageForm(messageList));
        }


        List<MessageForm> filteredMessage = filteringMessage(messageForms, status, keyword);

        int index = 0;
        for (MessageForm message : filteredMessage) {
            buffer.append("<tr> "
                    + " <td id=no>" + (index + 1) + " </td> "
                    + " <td id=sender>" + message.getFromAddress() + "</td>"
                    + " <td id=subject> "
                    + " <a href=show_message.jsp?msgid=" + (index + 1) + " title=\"메일 보기\"> "
                    + message.getSubject() + "</a> </td>"
                    + " <td id=date>" + message.getSentDate() + "</td>"
                    + " <td id=delete>"
                    + "<a href=ReadMail.do?menu="
                    + CommandType.DELETE_MAIL_COMMAND
                    + "&msgid=" + (index++ + 1) + "> 삭제 </a>" + "</td>"
                    + " </tr>");

        }

        buffer.append("</table>");

        return buffer.toString();
//        return "MessageFormatter 테이블 결과";
    }

    public String getMessage(Message message) {
        StringBuilder buffer = new StringBuilder();

        MessageParser parser = new MessageParser(message, userid);
        parser.parse(true);

        buffer.append("보낸 사람: " + parser.getFromAddress() + " <br>");
        buffer.append("받은 사람: " + parser.getToAddress() + " <br>");
        buffer.append("Cc &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; : " + parser.getCcAddress() + " <br>");
        buffer.append("보낸 날짜: " + parser.getSentDate() + " <br>");
        buffer.append("제 &nbsp;&nbsp;&nbsp;  목: " + parser.getSubject() + " <br> <hr>");

        buffer.append(parser.getBody());

        String attachedFile = parser.getFileName();
        if (attachedFile != null) {
            buffer.append("<br> <hr> 첨부파일: <a href=ReadMail.do?menu="
                    + CommandType.DOWNLOAD_COMMAND
                    + "&userid=" + this.userid
                    + "&filename=" + attachedFile.replaceAll(" ", "%20")
                    + " target=_top> " + attachedFile + "</a> <br>");
        }

        return buffer.toString();
    }

    public List<MessageForm> filteringMessage(List<MessageForm> messages, String status, String keyword){
        switch (status){
            case "FromAddress":
                // filteredMessage.stream().sorted(filteredMessage.forEach(messageForm -> messageForm.getSentDate());)
                return messages.stream().filter(messageForm -> messageForm.getFromAddress().contains(keyword)).sorted().collect(Collectors.toList());
            case "toAddress":
                return messages.stream().filter(messageForm -> messageForm.getToAddress().contains(keyword)).collect(Collectors.toList());
            case "ccAddress":
                return messages.stream().filter(messageForm -> messageForm.getCcAddress().contains(keyword)).collect(Collectors.toList());
            case "sentDate":
                return messages.stream().filter(messageForm -> messageForm.getSentDate().contains(keyword)).collect(Collectors.toList());
            case "subject":
                return messages.stream().filter(messageForm -> messageForm.getSubject().contains(keyword)).collect(Collectors.toList());
            case "body":
                return messages.stream().filter(messageForm -> messageForm.getBody().contains(keyword)).collect(Collectors.toList());
            case "fileName":
                return messages.stream().filter(messageForm -> messageForm.getFileName().contains(keyword)).collect(Collectors.toList());
            default:
                return messages;
        }
    }
}
