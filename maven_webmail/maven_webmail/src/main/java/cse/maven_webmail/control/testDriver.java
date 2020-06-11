import java.util.ArrayList;
import java.util.List;
import merong.Mail;
import java.util.stream.Collectors;


import merong.mailFilter;


public class testDriver {
    public static mailFilter mf = new mailFilter(2);
    public static List<Mail> list = new ArrayList<Mail>();
    public static ArrayList<Mail> arrayList = new ArrayList<>();


    public static void main(String[] args){

        for(int i = 0; i < 10; i++){
            arrayList.add(new Mail("name" + i, "to" + i, "subject" + i));
        }

        List<Mail> filteredList = mf.filterMail(arrayList, "0");
        for (Mail mail : filteredList){
            System.out.println(mail.getName());
        }

//        filteredList.clear();
//        mf.setFILTERED_STATUS(1);
//        filteredList = mf.filterMail(list, "1");
//        for (Mail mail : filteredList){
//            System.out.println(mail.getName());
//        }
//
//        filteredList.clear();
//        mf.setFILTERED_STATUS(2);
//        filteredList = mf.filterMail(list, "2");
//        for (Mail mail : filteredList){
//            System.out.println(mail.getName());
//        }

    }
}
