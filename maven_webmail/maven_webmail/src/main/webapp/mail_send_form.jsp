<%-- 
    Document   : mail_send_form.jsp
    Author     : jongmin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="cse.maven_webmail.control.CommandType" %>

<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>메일 쓰기 기본 폼</title>
    </head>
    <body>
        <form enctype="multipart/form-data" method="POST" 
              action="WriteMail.do?menu=<%= CommandType.SEND_MAIL_COMMAND %>" >
            <table>
                <tr>
                    <td> 수신 </td>
                    <td> <input type="text" name="to" size="80"
                                value=<%=request.getParameter("recv") == null ? "" : request.getParameter("recv")%>>  </td>
                </tr>
                <tr>
                    <td>참조</td>
                    <td> <input type="text" name="cc" size="80">  </td>
                </tr>
                <tr>
                    <td> 메일 제목 </td>
                    <td> <input type="text" name="subj" size="80"  >  </td>
                </tr>
                <tr>
                   <td colspan="2">본  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 문</td>
                </tr>
                <tr>  <%-- TextArea    --%>
                    <td colspan="2">  <textarea rows="15" name="body" cols="80"></textarea> </td>
                </tr>
                <tr>
                    <td>파일 첨부</td>
                    <td> <input type="file" name="file1"  size="80">  </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <input type="submit" value="메일 보내기">
                        <input type="reset" value="다시 입력">
                    </td>
                </tr>
            </table>
        </form>
    </body>
</html>
