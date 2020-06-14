package cse.maven_webmail.control;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import cse.maven_webmail.model.UserAdminAgent;
import cse.maven_webmail.model.Utility;
import java.io.UnsupportedEncodingException;
import static java.lang.System.out;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author jongmin
 */
public class UserAddHandler extends HttpServlet {

    Connection conn = null;
    PreparedStatement pstmt = null;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
//        PrintWriter out = response.getWriter();
        try ( PrintWriter out = response.getWriter()) {
//        RequestDispatcher view = request.getRequestDispatcher("main_menu.jsp");
            // Validate if userid == "admin"
            HttpSession session = request.getSession();
            String userid = (String) session.getAttribute("userid");

            request.setCharacterEncoding("UTF-8");
            int select = Integer.parseInt((String) request.getParameter("menu"));

            if (select == CommandType.ADD_USER_COMMAND) {
                adduser(request, response, out);
            }
        } catch (Exception ex) {
            System.err.println(ex.toString());
        }
    }

    private void adduser(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
        String server = "127.0.0.1";
        int port = 4555;
        try {

            String className = "com.mysql.cj.jdbc.Driver";
            Class.forName(className);
            String url = "jdbc:mysql://localhost:3306/adduser?serverTimezone=Asia/Seoul";
            String User = "root";
            String Password = "root";
            String id = request.getParameter("username");
            String password = request.getParameter("password");
            String password2 = request.getParameter("password2");
            String name = request.getParameter("name");
            String gender = request.getParameter("gender");
            String address = request.getParameter("address");
            String birth = request.getParameter("birth");
            String email1 = request.getParameter("email1");
            String email2 = request.getParameter("email2");
            String cellphone1 = request.getParameter("cellphone1");
            String cellphone2 = request.getParameter("cellphone2");
            conn = DriverManager.getConnection(url, User, Password);
            String sql = "INSERT INTO users VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            boolean isMatched = password.equals(password2);
            String PWDrange = "^[a-zA-Z0-9]{5,30}$";
            boolean isMatchedPWDrange = password.matches(PWDrange);

            String pwdHash = Utility.digestString(password, "SHA");

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.setString(2, pwdHash);
            pstmt.setString(3, name);
            pstmt.setString(4, gender);
            pstmt.setString(5, address);
            pstmt.setString(6, birth);
            pstmt.setString(7, email1);
            pstmt.setString(8, email2);
            pstmt.setString(9, cellphone1);
            pstmt.setString(10, cellphone2);
            pstmt.setString(11, "SHA");
            pstmt.setInt(12, 0);
            pstmt.setNull(13, 13);
            pstmt.setInt(14, 0);
            pstmt.setNull(15, 15);

            int count = pstmt.executeUpdate();

            if (count >= 1) {
                StringBuilder Popup = new StringBuilder();
                Popup.append("<script>alert('회원가입 성공!'); location.href='index.jsp';</script>");
                out.println(Popup.toString());
            } else {
                StringBuilder Popup = new StringBuilder();
                Popup.append("<script>alert('회원가입 실패 - 서버 상태를 확인하세요.'); window.history.back();</script>");
                out.println(Popup.toString());
            }
            pstmt.close();
            conn.close();
            out.flush();
        } catch (Exception ex) {
            out.println("시스템 접속에 실패했습니다.");
            out.println(ex.getMessage());
            ex.printStackTrace();
        }

    }

        
// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
/**
 * Handles the HTTP <code>GET</code> method.
 *
 * @param request servlet request
 * @param response servlet response
 * @throws ServletException if a servlet-specific error occurs
 * @throws IOException if an I/O error occurs
 */
@Override
protected void doGet(HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
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
     *
     * @return a String containing servlet description
     */
    @Override
public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
