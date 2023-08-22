<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
    <link rel="stylesheet" href="/register/register.css">
</head>
<body>
<%
    String err = request.getSession().getAttribute("errors") == null ? "" : "error";
%>


<div class="container">
    <div class="registration-form">
        <form action="/register" method="post">

            <% if (request.getSession().getAttribute("errors") == null) {%>

            <% } else if (((List<String>) request.getSession().getAttribute("errors")).isEmpty()) { %>

            <h1>Registration Successful</h1>
            <a href="/login">Please login</a>
            <% } else {%>
            <h1>Error Messages:</h1>
            <ul>
                <% List<String> errors = (List<String>) request.getSession().getAttribute("errors");
                    for (String error : errors) { %>
                <li><%= error %></li>
                <% } %>
            </ul>
            <% } %>
            <label for="firstName">First Name:</label>
            <input type="text" id="firstName" name="firstName" required class="<%=err%>"><br><br>

            <label for="surname">Surname:</label>
            <input type="text" id="surname" name="surname" required class="<%=err%>"><br><br>

            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required class="<%=err%>"><br><br>

            <label for="email">Email:</label>
            <input type="text" id="email" name="email" required class="<%=err%>"><br><br>

            <label for="phoneNumber">Phone Number:</label>
            <input type="text" id="phoneNumber" name="phoneNumber" required class="<%=err%>"><br><br>

            <label for="birthDate">Birth Date:</label>
            <input type="date" id="birthDate" name="birthDate" required class="<%=err%>"><br><br>

            <input type="submit" value="Register">
        </form>
    </div>
</div>

</body>
</html>
