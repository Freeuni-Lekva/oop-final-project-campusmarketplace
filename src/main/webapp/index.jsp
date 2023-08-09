<%@ page import="marketplace.objects.FeedPost" %>
<%@ page import="marketplace.dao.PostDAO" %>
<%@ page import="java.util.ArrayList" %>
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
                <button class="favorites-button">Favorites</button>
                <div class="user-options">
                    <a href="#">Log in</a>
                    <span class="divider">|</span>
                    <a href="#">Sign up</a>
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
    <div class="photo-container">
        <div class="image">
            <img src= <%=post.getPhoto_address()%> >
        </div>
        <div class="text">
            <div class="price">
                <h4><%=post.getPrice()%></h4>
            </div>
            <div class="description">
                <h5><%=post.getTitle()%></h5>
            </div>
        </div>
    </div>
    <% } %>
</div>

</body>
</html>