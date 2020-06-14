/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cse.maven_webmail.control;

import cse.maven_webmail.model.AddressAgent;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author inan
 */
public class AddressHandler extends HttpServlet {

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
            request.setCharacterEncoding("UTF-8");
            
            int select = Integer.parseInt((String) request.getParameter("menu"));

                switch (select) {
                    case CommandType.ADD_ADDRESS_COMMAND:
                        addAddress(request, response, out);
                        break;

                    case CommandType.DELETE_ADDRESS_COMMAND:
                        delAddress(request, response, out);
                        break;

                    default:
                        out.println("없는 메뉴를 선택하셨습니다. 어떻게 이 곳에 들어오셨나요?");
                        break;
                }            
        }
    }

    private void addAddress(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
        try {
            String userid = request.getParameter("userid");
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            AddressAgent agent = new AddressAgent(userid, name, email, phone);

            out.flush();

            // if (addUser successful)  사용자 등록 성공 팦업창
            // else 사용자 등록 실패 팝업창
            if (agent.addAddress()) {
                out.println(getAddressRegistrationSuccessPopUp());
            } else {
                out.println(getAddressRegistrationFailurePopUp());
            }

            out.flush();
        } catch (Exception ex) {
            out.println("시스템 접속에 실패했습니다.");
        }
    }

    private void delAddress(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
        try {
            String userid = request.getParameter("userid");
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            AddressAgent agent = new AddressAgent(userid, name, email, phone);

            out.flush();

            // if (addUser successful)  사용자 등록 성공 팦업창
            // else 사용자 등록 실패 팝업창
            if (agent.delAddress()) {
                out.println(getAddressDeletionSuccessPopUp());
            } else {
                out.println(getAddressDeletionFailurePopUp());
            }

            out.flush();
        } catch (Exception ex) {
            out.println("시스템 접속에 실패했습니다.");
        }
    }
    
    private String getAddressRegistrationSuccessPopUp() {
        String alertMessage = "주소록 등록을 성공했습니다.";
        StringBuilder successPopUp = new StringBuilder();
        successPopUp.append("<html>");
        successPopUp.append("<head>");

        successPopUp.append("<title>주소록 등록 결과</title>");
        successPopUp.append("<link type=\"text/css\" rel=\"stylesheet\" href=\"css/main_style.css\" />");
        successPopUp.append("</head>");
        successPopUp.append("<body onload=\"goMainMenu()\">");
        successPopUp.append("<script type=\"text/javascript\">");
        successPopUp.append("function goMainMenu() {");
        successPopUp.append("alert(\"");
        successPopUp.append(alertMessage);
        successPopUp.append("\"); ");
        successPopUp.append("window.location = \"address_book/address_book.jsp\"; ");
        successPopUp.append("}  </script>");
        successPopUp.append("</body></html>");
        return successPopUp.toString();
    }

    private String getAddressRegistrationFailurePopUp() {
        String alertMessage = "주소록 등록이 실패했습니다.";
        StringBuilder successPopUp = new StringBuilder();
        successPopUp.append("<html>");
        successPopUp.append("<head>");

        successPopUp.append("<title>주소록 등록 결과</title>");
        successPopUp.append("<link type=\"text/css\" rel=\"stylesheet\" href=\"css/main_style.css\" />");
        successPopUp.append("</head>");
        successPopUp.append("<body onload=\"goMainMenu()\">");
        successPopUp.append("<script type=\"text/javascript\">");
        successPopUp.append("function goMainMenu() {");
        successPopUp.append("alert(\"");
        successPopUp.append(alertMessage);
        successPopUp.append("\"); ");
        successPopUp.append("window.location = \"address_book/address_book.jsp\"; ");
        successPopUp.append("}  </script>");
        successPopUp.append("</body></html>");
        return successPopUp.toString();
    }

    private String getAddressDeletionSuccessPopUp() {
        String alertMessage = "주소록 삭제가 성공했습니다.";
        StringBuilder successPopUp = new StringBuilder();
        successPopUp.append("<html>");
        successPopUp.append("<head>");

        successPopUp.append("<title>주소록 등록 결과</title>");
        successPopUp.append("<link type=\"text/css\" rel=\"stylesheet\" href=\"css/main_style.css\" />");
        successPopUp.append("</head>");
        successPopUp.append("<body onload=\"goMainMenu()\">");
        successPopUp.append("<script type=\"text/javascript\">");
        successPopUp.append("function goMainMenu() {");
        successPopUp.append("alert(\"");
        successPopUp.append(alertMessage);
        successPopUp.append("\"); ");
        successPopUp.append("window.location = \"address_book/address_book.jsp\"; ");
        successPopUp.append("}  </script>");
        successPopUp.append("</body></html>");
        return successPopUp.toString();
    }

    private String getAddressDeletionFailurePopUp() {
        String alertMessage = "주소록 삭제가 실패했습니다.";
        StringBuilder successPopUp = new StringBuilder();
        successPopUp.append("<html>");
        successPopUp.append("<head>");

        successPopUp.append("<title>주소록 등록 결과</title>");
        successPopUp.append("<link type=\"text/css\" rel=\"stylesheet\" href=\"css/main_style.css\" />");
        successPopUp.append("</head>");
        successPopUp.append("<body onload=\"goMainMenu()\">");
        successPopUp.append("<script type=\"text/javascript\">");
        successPopUp.append("function goMainMenu() {");
        successPopUp.append("alert(\"");
        successPopUp.append(alertMessage);
        successPopUp.append("\"); ");
        successPopUp.append("window.location = \"address_book/address_book.jsp\"; ");
        successPopUp.append("}  </script>");
        successPopUp.append("</body></html>");
        return successPopUp.toString();
    }
    
//    private void deleteAddress(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
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
