<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Log in</title>
    <link rel="stylesheet" href="/login/login.css">
</head>
<body>
<%
    String err = session.getAttribute("error") == null ? "" : "error";

%>

<div class="container">
    <div class="login-form">
        <form action="/login" method="post">
            <label for="email">Email:</label>
            <input type="text" id="email" name="email" class="<%=err%>" required><br><br>

            <label for="password">Password:</label>
            <input type="password" id="password" name="password" class="<%=err%>" required><br><br>

            <input type="submit" value="Log in">
        </form>
    </div>
</div>

</body>

</html>
