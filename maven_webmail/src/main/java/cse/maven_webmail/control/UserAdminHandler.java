/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cse.maven_webmail.control;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import cse.maven_webmail.model.UserAdminAgent;

/**
 *
 * @author jongmin
 */
public class UserAdminHandler extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
//        RequestDispatcher view = request.getRequestDispatcher("main_menu.jsp");
            // Validate if userid == "admin"
            HttpSession session = request.getSession();
            String userid = (String) session.getAttribute("userid");
            if (userid == null || !userid.equals("admin")) {
                out.println("현재 사용자(" + userid + ")의 권한으로 수행 불가합니다.");
                out.println("<a href=/WebMailSystem/> 초기 화면으로 이동 </a>");
                return;
            } else {

                request.setCharacterEncoding("UTF-8");
                int select = Integer.parseInt((String) request.getParameter("menu"));

                switch (select) {
                    case CommandType.ADD_USER_COMMAND:
                        addUser(request, response, out);
                        break;

                    case CommandType.DELETE_USER_COMMAND:
                        deleteUser(request, response, out);
                        break;
                        
                    case CommandType.CHANGE_PW_COMMAND:
                        checkUser(request, response, out);
                        break;

                    default:
                        out.println("없는 메뉴를 선택하셨습니다. 어떻게 이 곳에 들어오셨나요?");
                        break;
                }
            }
        } catch (Exception ex) {
            System.err.println(ex.toString());
        }
    }

    private void addUser(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
        String server = "127.0.0.1";
        int port = 4555;
        try {
            UserAdminAgent agent = new UserAdminAgent(server, port);
            String userid = request.getParameter("id");  // for test
            String password = request.getParameter("password");// for test
            out.println("userid = " + userid + "<br>");
            out.println("password = " + password + "<br>");
            out.flush();
            // if (addUser successful)  사용자 등록 성공 팦업창
            // else 사용자 등록 실패 팝업창
            if (agent.addUser(userid, password)) {
                out.println(getUserRegistrationSuccessPopUp());
            } else {
                out.println(getUserRegistrationFailurePopUp());
            }
            out.flush();
        } catch (Exception ex) {
            out.println("시스템 접속에 실패했습니다.");
        }
    }

    private String getUserRegistrationSuccessPopUp() {
        String alertMessage = "사용자 등록이 성공했습니다.";
        String titleMessage = "사용자 등록 결과";
        String path = "admin_menu.jsp";
        String successPopup = setPopup(titleMessage, alertMessage, path);
        
        return successPopup;
    }

    private String getUserRegistrationFailurePopUp() {
        String alertMessage = "사용자 등록이 실패했습니다.";
        String titleMessage = "사용자 등록 결과";
        String path = "admin_menu.jsp";
        String failPopup = setPopup(titleMessage, alertMessage, path);
        
        return failPopup;
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
        String server = "127.0.0.1";
        int port = 4555;
        try {
            UserAdminAgent agent = new UserAdminAgent(server, port);
            String[] deleteUserList = request.getParameterValues("selectedUsers");
            agent.deleteUsers(deleteUserList);
            response.sendRedirect("admin_menu.jsp");
        } catch (Exception ex) {
            System.out.println(" UserAdminHandler.deleteUser : exception = " + ex);
        }
    }
    
    private void checkUser (HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
        String server = "127.0.0.1";
        int port = 4555;
        try {
            UserAdminAgent agent = new UserAdminAgent(server, port);
            String checkId = request.getParameter("id"); 
            String changePw = request.getParameter("changePw");
            boolean check = false;

            for (String userId : agent.getUserList()) {
                if (userId.equals(checkId)) {
                    check = true;
                    System.out.println("check ok");
                    break;
                }
            }
            if(check) {
                if(changePw(checkId, changePw)){ //암호 변경 성공
                    out.println(getChangePasswordSuccessPopUp());
                } else { //암호 변경 실패
                    out.println(getChangePasswordFailPopUp());
                }
            } else { //유효하지 않은 사용자
                out.println(getNoUserPopUp());
            }
        } catch (Exception ex) {
            out.println("시스템 접속에 실패했습니다.");
        }
    }
    
    private boolean changePw (String id, String password) {
        String server = "127.0.0.1";
        int port = 4555;
        boolean result = false;
        
        try {
            UserAdminAgent agent = new UserAdminAgent(server, port);
            String checkId = id;
            String changePw = password;
            
            result = agent.changePassword(checkId, changePw); //암호 변경
            
        } catch (Exception ex) {
            System.out.println(" UserAdminHandler.changePw : exception = " + ex);
        }
        return result;
    }
    
    private String getChangePasswordSuccessPopUp() {
        String alertMessage = "사용자 암호 변경을 성공했습니다.";
        String titleMessage = "암호 변경 결과";
        String path = "admin_menu.jsp";
        String successPopup = setPopup(titleMessage, alertMessage, path);
        
        return successPopup;
    }
    
    private String getChangePasswordFailPopUp() {
        String alertMessage = "사용자 암호 변경에 실패했습니다.";
        String titleMessage = "암호 변경 결과";
        String path = "admin_menu.jsp";
        String failPopup = setPopup(titleMessage, alertMessage, path);
        
        return failPopup;
    }
    
    private String getNoUserPopUp() {
        String alertMessage = "입력한 사용자 ID가 존재하지 않습니다.";
        String titleMessage = "사용자 ID 확인 결과";
        String path = "changePW_user.jsp";
        String noUserPopup = setPopup(titleMessage, alertMessage, path);

        return noUserPopup;
    }
    
    private String setPopup (String title, String alertMessage, String path) {
        StringBuilder PopUp = new StringBuilder();
        
        PopUp.append("<html>");
        PopUp.append("<head>");
        PopUp.append("<title>").append(title).append("</title>");
        PopUp.append("<link type=\"text/css\" rel=\"stylesheet\" href=\"css/main_style.css\" />");
        PopUp.append("</head>");
        PopUp.append("<body onload=\"goMainMenu()\">");
        PopUp.append("<script type=\"text/javascript\">");
        PopUp.append("function goMainMenu() {");
        PopUp.append("alert(\"");
        PopUp.append(alertMessage);
        PopUp.append("\"); ");
        PopUp.append("window.location = \"").append(path).append("\"; ");
        PopUp.append("}  </script>");
        PopUp.append("</body></html>");
        
        return PopUp.toString();
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
