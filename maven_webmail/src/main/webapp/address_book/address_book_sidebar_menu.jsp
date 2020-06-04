<%-- 
    Document   : address_book_sidebar_menu
    Created on : 2020. 5. 28., 오전 11:37:49
    Author     : inan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>주소록 메인메뉴</title>
    </head>
    <body>
        <span style="color: indigo"> <strong>사용자: <%= session.getAttribute("userid") %> </strong> </span> <br>
                
        <p> <a href="add_address.jsp"> 주소 추가 </a> </p>
        <p> <a href="delete_address.jsp"> 주소 제거 </a> </p>
        <p> <a href="../main_menu.jsp"> 메인 메뉴 </a> </p>
    </body>
</html>
