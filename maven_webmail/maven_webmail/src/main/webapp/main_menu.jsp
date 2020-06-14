<%-- 
    Document   : main_menu.jsp
    Author     : jongmin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>

<jsp:useBean id="pop3" scope="page" class="cse.maven_webmail.model.Pop3Agent" />
<%
            //String pageno = (String) request.getParameter("pageno");
            //if (pageno != null) {
            //    session.setAttribute("pageno", pageno);
            //}
            pop3.setHost((String) session.getAttribute("host"));
            pop3.setUserid((String) session.getAttribute("userid"));
            pop3.setPassword((String) session.getAttribute("password"));
            //pop3.setPageno((int)Integer.parseInt((String)session.getAttribute("pageno")));
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>주메뉴 화면</title>
        <link type="text/css" rel="stylesheet" href="css/main_style.css" />
    </head>

    <body>
        <jsp:include page="header.jsp" />
        <script language="javascript" type="text/javascript">
            function getFilteredMessageList(){
                var e = document.getElementById("status");
                var selectedStatus = e.options[e.selectedIndex].value;
                var enterKeyword = document.getElementById("keyword").value;

                window.location.replace("/main_menu.jsp?status="
                    +selectedStatus+"&keyword"+enterKeyword);
            }
        </script>


        <div id="sidebar">
            <jsp:include page="sidebar_menu.jsp" />
        </div>

        <form name="filterForm" onsubmit="getFilteredMessageList()">
            <label>Filtering By </label>
            <select id="status" name="status">
                <option value="None" selected>필터링 X</option>
                <option value="fromAddress">발신인</option>
                <option value="toAddress">수신인</option>
                <option value="ccAddress">참조인</option>
                <option value="sentDate">특정 날짜</option>
                <option value="body" >내용</option>
                <option value="fileName">파일 이름</option>
            </select>

            <input type="text" id="keyword" value="" placeholder="Enter Keyword!!"  name="keyword"/>
            <input type="submit">
        </form>




        <div id="main">
            <%
                String status = request.getParameter("status");
                String keyword = request.getParameter("keyword");
                pop3.getMessageList(status, keyword);
            %>
        </div>

        <jsp:include page="footer.jsp" />

    </body>
</html>
