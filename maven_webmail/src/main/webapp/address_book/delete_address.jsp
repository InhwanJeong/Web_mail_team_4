<%-- 
    Document   : delete_address
    Created on : 2020. 5. 28., 오전 11:40:26
    Author     : inan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="cse.maven_webmail.control.CommandType" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>새로운 주소 제거화면</title>
        <link type="text/css" rel="stylesheet" href="../css/main_style.css" />
    </head>
     <jsp:include page="../header.jsp" />

        <div id="sidebar">
            <jsp:include page="../sidebar_address_previous_menu.jsp" />
        </div>

        <div id="main">
            삭제할 주소의 사용자 이름을 입력해 주시기 바랍니다. <br> <br>

            <form name="AddAddress" action="../Address.do?userid=<%= session.getAttribute("userid") %>&menu=<%= CommandType.DELETE_ADDRESS_COMMAND%>"
                  method="POST">
                <table border="0" align="left">
                    <tr>
                        <td>이름</td>
                        <td> <input type="text" name="name" value="" size="20" required/>  </td>
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
</html>
