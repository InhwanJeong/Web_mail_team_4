<%-- 
    Document   : changePW_user
    Created on : 2020. 6. 3., 오전 3:37:48
    Author     : Mijin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="cse.maven_webmail.control.CommandType" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>암호 변경 화면</title>
        <link type="text/css" rel="stylesheet" href="css/main_style.css" />
    </head>
    <body>
        <jsp:include page="header.jsp" />

        <div id="sidebar">
            <jsp:include page="sidebar_admin_previous_menu.jsp" /> <%--이전 메뉴로--%>
        </div>

        <div id="main">
            사용자 ID와 변경할 암호를 입력해 주시기 바랍니다. <br> <br>

            <form name="ChangePw" action="UserAdmin.do?menu=<%= CommandType.CHANGE_PW_COMMAND%>"
                  method="POST">
                <table border="0" align="left">
                    <tr>
                        <td>사용자 ID</td>
                        <td> <input type="text" name="id" value="" size="20" />  </td>
                    </tr>
                    <tr>
                        <td>변경할 암호 </td>
                        <td> <input type="password" name="changePw" value="" /> </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <input type="submit" value="변경" name="change" />
                            <input type="reset" value="초기화" name="reset" />
                        </td>
                    </tr>
                </table>

            </form>
        </div>

        <jsp:include page="footer.jsp" />
    </body>
</html>
