<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Log in</title>
    <link rel="stylesheet" href="login.css">
</head>
<body>
<% if (request.getAttribute("error") == null) {%>

<% } else {%>
<h1>Error Messages:</h1>
    <%= (String) request.getAttribute("error") %>
<% } %>

<div class="container">
    <div class="login-form">
        <form method="post">
            <label for="email">Email:</label>
            <input type="text" id="email" name="email" required><br><br>

            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required><br><br>

            <input type="submit" value="Log in">
        </form>

    </div>
</div>

</body>

</html>
