package cse.maven_webmail.model;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.InputStream;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BookedMailExecutor implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String JdbcDriver = "com.mysql.cj.jdbc.Driver";
        String JdbcUrl = "jdbc:mysql://localhost:3306/webmail?serverTimezone=Asia/Seoul";
        String User = "root";
        String Password = "root";
        ArrayList<Integer> finish_list = new ArrayList<>();
        try {
            Class.forName(JdbcDriver);
            Connection connection = DriverManager.getConnection(JdbcUrl, User, Password);
            Statement stmt = connection.createStatement();
            String sql = "SELECT * From booked_mail";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()){
                SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                Date dateTime = form.parse(rs.getString("bookTime"));
                if(dateTime.compareTo(new Date()) > 0){
                    continue;
                }
                //SmtpAgent 객체에 메일 관련 정보 설정
                SmtpAgent agent = new SmtpAgent(rs.getString("host"), rs.getString("userid"));
                agent.setTo(rs.getString("toAddress"));
                agent.setCc(rs.getString("ccAddress"));
                agent.setSubj(rs.getString("subj"));
                agent.setBody(rs.getString("body"));
                String fileName = rs.getString("fileName");
                System.out.println("WriteMailHandler.sendMessage() : fileName = " + fileName);
                if (fileName != null) {
                    InputStream is = rs.getBinaryStream("file");
                    agent.setFile2(fileName);
                    agent.setFile(is);
                }

                //메일 전송 권한 위임
                if (agent.sendMessage()) {
                    finish_list.add(Integer.parseInt(rs.getString("mailnum")));
                }
            }

            //완료된 메일 DB에서 삭제
            for(int i:finish_list){
                String delsql = "delete from booked_mail where mailnum=?";
                PreparedStatement pstmt = connection.prepareStatement(delsql);
                pstmt.setString(1, Integer.toString(i));
                pstmt.executeUpdate();
            }
            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
