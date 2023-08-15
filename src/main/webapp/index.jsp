<%@ page import="marketplace.objects.FeedPost" %>
<%@ page import="marketplace.dao.PostDAO" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">

<%
    PostDAO postDAO = ((PostDAO)application.getAttribute("postDAO"));

    Integer page_int = null;
    String page_str = request.getParameter("page");
    if(page_str != null) page_int = Integer.parseInt(page_str);
    ArrayList<FeedPost> feedPost = postDAO.getAllFeedPosts(0);
    if(page_int != null)
        feedPost = postDAO.getAllFeedPosts(page_int.intValue());

%>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Marketplace</title>
    <link rel="stylesheet" href="index.css">
</head>
<body>
<div class="container">
    <header>
        <div class="container-header">
            <div class="header-left">
                <h1>Marketplace</h1>
            </div>
            <div class="header-middle">
                <div class="search-bar">
                    <input type="text" placeholder="Search...">
                    <button type="submit">Search</button>
                </div>
            </div>
            <div class="header-right">
                <!--<button class="favorites-button">Favorites</button>-->
                <div class="user-options">
                    <a href="login/login.jsp">Log in</a>
                    <span class="divider">|</span>
                    <a href="register/register.jsp">Sign up</a>
                </div>
            </div>
        </div>
    </header>

    <nav class="navbar">
        <ul class="nav-menu">
            <li><a href="#">Home</a></li>
            <li><a href="#">Products</a></li>
            <li><a href="#">Services</a></li>
            <li><a href="#">About</a></li>
            <li><a href="#">Contact</a></li>
        </ul>
    </nav>
</div>

<div class="all-photos">
    <% for(FeedPost post : feedPost){ %>
    <form action="/postpage" method="get">
    <input value="<%=post.getPost_id()%>" name="id" style="display: none">
    <div class="photo-container" onclick="this.closest('form').submit();">
        <div class="image">
            <img src= <%=post.getPhoto_address()%> >
        </div>
        <div class="text">
            <div class="description">
                <h4><%=post.getTitle()%></h4>
            </div>
            <div class="price">
                <h5><%=post.getPrice()%></h5>
            </div>
        </div>
    </div>
    </form>
    <% } %>
</div>

</body>
</html>