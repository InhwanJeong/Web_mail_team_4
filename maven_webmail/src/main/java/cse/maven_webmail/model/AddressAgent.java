/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cse.maven_webmail.model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;

/**
 *
 * @author inan
 */
public class AddressAgent {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private final String JdbcDriver = "com.mysql.cj.jdbc.Driver";
    private final String JdbcUrl = "jdbc:mysql://localhost:3306/webmail?serverTimezone=Asia/Seoul";
    private final String User = "webmail";
    private final String Password = "2007";
    private final String userid;
    private final String name;
    private final String email;
    private final String phone;
    private boolean status = false;
    
    public AddressAgent(String userid, String name, String email, String phone){
        this.userid = userid;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }
    
    public boolean addAddress(){        
        try{
        // 1. JDBC 드라이버 적재
        Class.forName(JdbcDriver);

        // 2. DB 연결
        Connection conn = DriverManager.getConnection(JdbcUrl, User, Password);

        // 3. PreparedStatement 생성
        String sql = "INSERT INTO db(user_id, name, email, phone) VALUES(?,?,?,?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);

        // 4.SQL 문 완성        
        if (!(userid == null) && !userid.equals("")){            
            pstmt.setString(1, userid);
            pstmt.setString(2, name);
            pstmt.setString(3, email);
            pstmt.setString(4, phone);

            // 5. 실행 : PreparedStatement.executeUpdate()는
            //         INSERT, UPDATE, DELETE시 사용 가능
            pstmt.executeUpdate();
            status = true;
        }
        // 6. 자원 해제
        pstmt.close();
        conn.close();
        }catch(Exception ex){
            System.out.println(ex);
        }        
        return status;
    }
    
    public boolean delAddress(){        
        try{
        // 1. JDBC 드라이버 적재
        Class.forName(JdbcDriver);

        // 2. DB 연결
        Connection conn = DriverManager.getConnection(JdbcUrl, User, Password);

        // 3. PreparedStatement 생성
        String sql = "DELETE FROM db WHERE user_id=(?) and name=(?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);

        // 4.SQL 문 완성        
        if (!(userid == null) && !userid.equals("")){            
            pstmt.setString(1, userid);
            pstmt.setString(2, name);


            // 5. 실행 : PreparedStatement.executeUpdate()는
            //         INSERT, UPDATE, DELETE시 사용 가능
            pstmt.executeUpdate();
            status = true;
        }
        // 6. 자원 해제
        pstmt.close();
        conn.close();
        }catch(Exception ex){
            System.out.println(ex);
        }        
        return status;
    }
}
