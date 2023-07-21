<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<% if (request.getAttribute("error") == null) {%>
<h1>Login</h1>
Please enter your information.

<% } else {%>
<h1>Error Messages:</h1>
    <%= (String) request.getAttribute("error") %>
<% } %>

<form method="post">
    <label for="email">Email:</label>
    <input type="text" id="email" name="email" required><br><br>

    <label for="password">Password:</label>
    <input type="password" id="password" name="password" required><br><br>

    <input type="submit" value="Register">
</form>

</body>
</html>
