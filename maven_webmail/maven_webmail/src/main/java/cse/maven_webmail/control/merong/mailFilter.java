package merong;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;


public class mailFilter {
    public ArrayList<String> mailBook = new ArrayList<String>();

    public int FILTERER_BY_NONE = 0;
    public int FILTERER_BY_NAME = 1;
    public int FILTERER_BY_SUBJECT = 2;
    public int FILTERER_BY_BODY = 3;

    public int FILTERED_STATUS = FILTERER_BY_NONE;

    public mailFilter(int status){
        this.FILTERED_STATUS = status;
    }

    public void setFILTERED_STATUS(int FILTERED_STATUS) {
        this.FILTERED_STATUS = FILTERED_STATUS;
    }

    public List<Mail> filterMail(List<Mail> mailList, String str){
        Mail[] mails = mailList.toArray(new Mail[0]);

        if (FILTERED_STATUS == FILTERER_BY_NAME)
            return mailList.stream().filter(mail -> mail.getName().contains(str)).collect(Collectors.toList());
        else if (FILTERED_STATUS == FILTERER_BY_SUBJECT)
            return mailList.stream().filter(mail -> mail.getSubject().contains(str)).collect(Collectors.toList());
        else if (FILTERED_STATUS == FILTERER_BY_BODY)
            return mailList.stream().filter(mail -> mail.getBody().contains(str)).collect(Collectors.toList());
        else
            return mailList;

    }
}
