<%@ page import="marketplace.dao.PostDAO" %>
<%@ page import="marketplace.objects.Post" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    PostDAO postDAO = ((PostDAO)application.getAttribute("postDAO"));
    int id = Integer.parseInt(request.getParameter("id"));
    Post post = postDAO.getPostById(id);
%>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Product Page</title>
    <link rel="stylesheet" href="/post/post.css">
</head>
<body>

<div class="product-container">
    <div class="product-image">
        <img src="<%=post.getPhotos().get(0)%>" alt="Product Photo">
    </div>
    <div class="product-details">
        <p class="product-description">Product description goes here...</p>
        <p class="product-price">$99.99</p>
        <a class="chat-option">Chat with us</a>
        <div class="profile">
            <div class="profile-image">
                <img src="profile-picture.jpg" alt="Profile Picture">
            </div>
            <p class="profile-name">Seller Name</p>
        </div>
    </div>
</div>

</body>
</html>