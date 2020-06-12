package cse.maven_webmail.model;

import javax.mail.Message;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

;

public class MessageFilter{
    public  MessageFilter(){

    }


    public List<ArrayList<String>> filteringMessage(List<ArrayList<String>> messages, String text){

        if (true){
            return messages.stream().filter(strings -> strings.contains(text)).collect(Collectors.toList());
        }


    }


}

