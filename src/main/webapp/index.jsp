<%@ page import="marketplace.objects.FeedPost" %>
<%@ page import="marketplace.dao.PostDAO" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Map" %>
<%@ page import="marketplace.dao.FilterDAO" %>
<%@ page import="marketplace.objects.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Marketplace</title>
    <link rel="stylesheet" href="index.css">
</head>

<body>

</body>

<script>
    window.addEventListener("load", (event) => {
        window.location.pathname = "/feedposts";
    });
</script>
</html>