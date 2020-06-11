<%-- 
    Document   : address_book
    Created on : 2020. 6. 3, 오후 5:05:15
    Author     : inan
--%>

<%@tag description="put the tag description here" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="user" required="true"%>
<%@attribute name="password" required="true"%>
<%@attribute name="schema" required="true"%>
<%@attribute name="table" required="true"%>

<%-- any content can be specified here e.g.: --%>
<sql:setDataSource var="dataSrc"
                   url="jdbc:mysql://localhost:3306/${schema}?serverTimezone=Asia/Seoul"
                   driver="com.mysql.jdbc.Driver"
                   user="${user}" password="${password}"/>

<sql:query var="rs" dataSource="${dataSrc}">
    SELECT email, name, phone FROM ${table} where user_id ='<%= session.getAttribute("userid") %>'
</sql:query>
    
    <table border="1">
        <thead>
            <tr>
                <th>이름</th>
                <th>이메일</th>
                <th>전화번호</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="row" items="${rs.rows}">
                <tr>
                    <td>${row.name}</td>
                    <td>${row.email}</td>
                    <td>${row.phone}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>