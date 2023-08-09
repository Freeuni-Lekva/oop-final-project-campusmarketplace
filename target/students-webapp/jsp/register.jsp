<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<% if (request.getAttribute("errors") == null) {%>
<h1>Create New Account</h1>
Please enter your information.

<% } else if (((List<String>) request.getAttribute("errors")).isEmpty()) { %>

<h1>Registration Successful</h1>
<a href="/login">Please login</a>
<% } else {%>
<h1>Error Messages:</h1>
<ul>
    <% List<String> errors = (List<String>) request.getAttribute("errors");
        for (String error : errors) { %>
    <li><%= error %></li>
    <% } %>
</ul>
<% } %>


<form method="post">
    <label for="firstName">First Name:</label>
    <input type="text" id="firstName" name="firstName" required><br><br>

    <label for="surname">Surname:</label>
    <input type="text" id="surname" name="surname" required><br><br>

    <label for="password">Password:</label>
    <input type="password" id="password" name="password" required><br><br>

    <label for="email">Email:</label>
    <input type="text" id="email" name="email" required><br><br>

    <label for="phoneNumber">Phone Number:</label>
    <input type="text" id="phoneNumber" name="phoneNumber" required><br><br>

    <label for="birthDate">Birth Date:</label>
    <input type="date" id="birthDate" name="birthDate" required><br><br>

    <input type="submit" value="Register">
</form>

</body>
</html>
