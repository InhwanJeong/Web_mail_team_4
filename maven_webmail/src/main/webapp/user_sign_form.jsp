<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="cse.maven_webmail.control.CommandType" %>

<!DOCTYPE html"> 

<html>
    <body>
    <head>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>회원가입</title>
        <link type="text/css" rel="stylesheet" href="css/main_style.css" />
        <script type="text/javascript">
            function isSame() {

                var pw = document.getElementById("pw");
                if (join.pw.value != join.checkpw.value) {
                    alert("비밀번호가 다릅니다. 다시 확인해 주세요.");
                    join.checkpw.value = "";
                    join.checkpw.focus();
                    return false;
                }

    }
        </script>

    </head>
    <jsp:include page="header.jsp" />
    <div id="sidebar">
        <jsp:include page="sidebar_admin_previous_menu.jsp" />
    </div>

    <div id="main">

        <form name="AddUser" action="UserAddhandler.do?menu=<%= CommandType.ADD_USER_COMMAND%>" method="POST">
            <table border="0" align="left">
                <tr>
                    <td>사용자 ID </td>
                    <td> <input type="text" name="id" value="" size="15" placeholder="ID입력" maxlength="15" required/></td>

                </tr>
                <tr>
                    <td>비밀번호 </td>
                    <td> <input type="password" name="password" value="" size="25" placeholder="비밀번호(8자이상)" maxlength="20" id="pw" onchange="isSame()" required/> </td>
                </tr>
                <tr>                 
                    <td>비밀번호 확인 </td>
                    <td> <input type="password" name="password1" id="checkpw" onchange="isSame()" value="" size="25" placeholder="비밀번호 확인" maxlength="15" required/> </td>
                    <br> <span id="isSame"></span> </td>
                </tr>

                    <td>이름 </td>
                    <td> <input type="text" name="name" value="" size="25" placeholder="이름" required/> </td>
                </tr>

                <tr>
                    <td>성별 </td>
                    <td> <input type='radio' name='gender' value='female' required />여성
                        <input type='radio' name='gender' value='male' required />남성 </td>
                </tr>
                <tr>
                    <td>주소 </td>
                    <td> <input type="text" name="address" value="" size="25" required />  </td>
                </tr>

                <tr>
                    <td>생년월일 </td>
                    <td> <input type="date" name="birth" value="" size="25" required/> </td>
                </tr>
                <tr>
                    <td>이메일 </td>
                    <td> <input type="text" size="10" name="email1" required>@<input type="text" size="10" name="email2" placeholder="직접입력" required>
                    </td>
                </tr>    

                <tr>
                    <td>휴대폰 번호 </td>
                    <td valgn="top">
                        <select name="cellphone1" size="">
                            <option value="010">010</option>
                        </select>
                        <input type="tel" name="cellphone2" value="" size="20" required/>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <input type="submit" value="가입신청"onClick ="return getConfirmResult()"/>
                        <input type="reset" value="초기화" name="reset" />
                    </td>

            </table>

        </form>
    </div>

    <jsp:include page="footer.jsp" />

</body>
</html>
