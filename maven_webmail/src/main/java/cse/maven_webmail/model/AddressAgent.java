/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cse.maven_webmail.model;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author inan
 */
@WebServlet(name = "AddressAgent", urlPatterns = {"/model/AddressAgent.do"})
public class AddressAgent extends HttpServlet {
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
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            final String JdbcDriver = "com.mysql.cj.jdbc.Driver";
            final String JdbcUrl = "jdbc:mysql://localhost:3306/webmail?serverTimezone=Asia/Seoul";
            final String User = "webmail";
            final String Password = "2007";

            try {
                // 1. JDBC 드라이버 적재
                Class.forName(JdbcDriver);

                // 2. DB 연결
                Connection conn = DriverManager.getConnection(JdbcUrl, User, Password);

                // 3. PreparedStatement 생성
                String sql = "INSERT INTO db VALUES(?,?,?,?)";
                PreparedStatement pstmt = conn.prepareStatement(sql);

                // 4.SQL 문 완성
                request.setCharacterEncoding("UTF-8"); // 한글 인식
                String userid = request.getParameter("email"); // 주키
                
                if(!(userid == null) && !userid.equals(""));{
                    String email = request.getParameter("email");
                    String name = request.getParameter("name");
                    String phone = request.getParameter("phone");
                    
                    pstmt.setString(1, userid);
                    pstmt.setString(2, email);
                    pstmt.setString(3, name);
                    pstmt.setString(4, phone);
                    
                    // 5. 실행 : PreparedStatement.executeUpdate()는
                    //         INSERT, UPDATE, DELETE시 사용 가능
                    pstmt.executeUpdate();
                }
                // 6. 자원 해제
                pstmt.close();
                conn.close();
                response.sendRedirect("address_book.jsp");
            } catch (Exception ex) {
                out.println("오류가 발생했습니다. (발생오류: " + ex.getMessage() + ")");
                out.println("<br/> <a href=\"index.jsp\">초기 화면으로 가기</a>");
            }
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
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
