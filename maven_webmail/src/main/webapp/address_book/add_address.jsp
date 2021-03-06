<%-- 
    Document   : add_address
    Created on : 2020. 5. 28., 오전 11:40:15
    Author     : inan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="cse.maven_webmail.control.CommandType" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>새로운 주소 추가화면</title>
        <link type="text/css" rel="stylesheet" href="../css/main_style.css" />
    </head>
    <body>
        <jsp:include page="../header.jsp" />

        <div id="sidebar">
            <jsp:include page="../sidebar_address_previous_menu.jsp" />
        </div>

        <div id="main">
            추가로 등록할 주소의 사용자 이름과 이메일 주소, 전화번호를 입력해 주시기 바랍니다. <br> <br>

            <form name="AddAddress" action="../Address.do?userid=<%= session.getAttribute("userid") %>&menu=<%= CommandType.ADD_ADDRESS_COMMAND%>"
                  method="POST">
                <table border="0" align="left">
                    <tr>
                        <td>이름</td>
                        <td> <input type="text" name="name" value="" size="20" required/>  </td>
                    </tr>
                    <tr>
                        <td>이메일주소 </td>
                        <td> <input type="text" name="email" value="" required/> </td>
                    </tr>
                    <tr>
                        <td>전화번호 </td>
                        <td> <input type="text" name="phone" value="" required/> </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <input type="submit" value="등록" name="register" />
                            <input type="reset" value="초기화" name="reset" />
                        </td>
                    </tr>
                </table>

            </form>
        </div>
        
        <jsp:include page="../footer.jsp" />
    </body>
</html>
