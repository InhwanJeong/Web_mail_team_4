<%-- 
    Document   : login_fail
    Author     : jongmin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>

<script type="text/javascript">
    <!--
    function gohome(){
        window.location = "/maven_webmail/"
    }
    -->
</script>


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>로그인 실패</title>
        <link type="text/css" rel="stylesheet" href="css/main_style.css" />
    </head>
    <body onload="setTimeout('gohome()', 5000)">

        <jsp:include page="header.jsp" />

        <p id="login_fail">
            <%= request.getParameter("userid")%>님, 로그인이 실패하였습니다.

            올바른 사용자 ID와 암호를 사용하여 로그인하시기 바랍니다.

            5초 뒤 자동으로 초기 화면으로 돌아갑니다.

            자동으로 화면 전환이 일어나지 않을 경우
            <!-- <a href="/WebMailSystem/" title="초기 화면">초기 화면</a>을 선택해 주세요.-->
            <a href="<%= getServletContext().getInitParameter("HomeDirectory") %>" title="초기 화면">초기 화면</a>을 선택해 주세요.
        </p>

        <jsp:include page="footer.jsp" />

    </body>
</html>
