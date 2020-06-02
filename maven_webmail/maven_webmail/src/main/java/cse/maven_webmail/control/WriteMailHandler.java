/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cse.maven_webmail.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.jdbc.Driver;
import cse.maven_webmail.model.FormParser;
import cse.maven_webmail.model.SmtpAgent;

/**
 *
 * @author jongmin
 */
public class WriteMailHandler extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
//        PrintWriter out = response.getWriter();
        PrintWriter out = null;

        try {
            request.setCharacterEncoding("UTF-8");
            int select = Integer.parseInt((String) request.getParameter("menu"));

            switch (select) {
//                case CommandType.WRITE_MENU:  // 메일 쓰기 화면
//                    out = response.getWriter();
//                    response.sendRedirect(homeDirectory + "write_mail.jsp");
//                    break;


                case CommandType.SEND_MAIL_COMMAND: // 실제 메일 전송하기
                    out = response.getWriter();
                    boolean status = sendMessage(request);
                    out.print(getMailTransportPopUp(status));
//                    out.flush();
                    break;

                case CommandType.BOOK_MAIL_COMMAND:
                    out = response.getWriter();
                    boolean bookStatus = bookMessage(request);
                    out.print(getMailTransportPopUp(bookStatus));
                    break;

                default:
                    out = response.getWriter();
                    out.println("없는 메뉴를 선택하셨습니다. 어떻게 이 곳에 들어오셨나요?");
                    break;
            }
        } catch (Exception ex) {
            out.println(ex.toString());
        } finally {
            out.close();
        }
    }

    private boolean bookMessage(HttpServletRequest request){
        boolean status = false;

        String JdbcDriver = "com.mysql.cj.jdbc.Driver";
        String JdbcUrl = "jdbc:mysql://localhost:3306/webmail?serverTimezone=Asia/Seoul";
        String User = "jdbctester";
        String Password = "1234";

        try {
            Class.forName(JdbcDriver);
            Connection connection = DriverManager.getConnection(JdbcUrl, User, Password);
            Statement stmt = connection.createStatement();

            // 1. toAddress, ccAddress, subject, body, file1 정보를 파싱하여 추출
            FormParser parser = new FormParser(request);
            parser.parse();

            // 2.  request 객체에서 HttpSession 객체 얻기
            HttpSession session = (HttpSession) request.getSession();

            // 3. HttpSession 객체에서 메일 서버, 메일 사용자 ID 정보 얻기
            String host = (String) session.getAttribute("host");
            String userid = (String) session.getAttribute("userid");

            // 4. 메일 관련 정보 가져오기
            String to = parser.getToAddress();
            String cc = parser.getCcAddress();
            String subj = parser.getSubject();
            String body = parser.getBody();
            String fileName = parser.getFileName();

            String sql = "select * from bookmail";
            ResultSet rs = stmt.executeQuery(sql);
            // 5. 메일 전송 권한 위임
            if (stmt.executeQuery(sql) != null) {
                status = true;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return status;
    }

    private boolean sendMessage(HttpServletRequest request) {
        boolean status = false;

        // 1. toAddress, ccAddress, subject, body, file1 정보를 파싱하여 추출
        FormParser parser = new FormParser(request);
        parser.parse();

        // 2.  request 객체에서 HttpSession 객체 얻기
        HttpSession session = (HttpSession) request.getSession();

        // 3. HttpSession 객체에서 메일 서버, 메일 사용자 ID 정보 얻기
        String host = (String) session.getAttribute("host");
        String userid = (String) session.getAttribute("userid");

        // 4. SmtpAgent 객체에 메일 관련 정보 설정
        SmtpAgent agent = new SmtpAgent(host, userid);
        agent.setTo(parser.getToAddress());
        agent.setCc(parser.getCcAddress());
        agent.setSubj(parser.getSubject());
        agent.setBody(parser.getBody());
        String fileName = parser.getFileName();
        System.out.println("WriteMailHandler.sendMessage() : fileName = " + fileName);
        if (fileName != null) {
            agent.setFile1(fileName);
        }

        // 5. 메일 전송 권한 위임
        if (agent.sendMessage()) {
            status = true;
        }
        return status;
    }  // sendMessage()

    private String getMailTransportPopUp(boolean success) {
        String alertMessage = null;
        if (success) {
            alertMessage = "메일 전송이 성공했습니다.";
        } else {
            alertMessage = "메일 전송이 실패했습니다.";
        }

        StringBuilder successPopUp = new StringBuilder();
        successPopUp.append("<html>");
        successPopUp.append("<head>");

        successPopUp.append("<title>메일 전송 결과</title>");
        successPopUp.append("<link type=\"text/css\" rel=\"stylesheet\" href=\"css/main_style.css\" />");
        successPopUp.append("</head>");
        successPopUp.append("<body onload=\"goMainMenu()\">");
        successPopUp.append("<script type=\"text/javascript\">");
        successPopUp.append("function goMainMenu() {");
        successPopUp.append("alert(\"");
        successPopUp.append(alertMessage);
        successPopUp.append("\"); ");
        successPopUp.append("window.location = \"main_menu.jsp\"; ");
        successPopUp.append("}  </script>");
        successPopUp.append("</body></html>");
        return successPopUp.toString();
    }

// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);


    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);


    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";

    }// </editor-fold>
}
