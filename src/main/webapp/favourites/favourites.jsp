<%@ page import="marketplace.objects.User" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="marketplace.objects.FeedPost" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<%
    User profile_user = ((User)session.getAttribute("user"));
    boolean is_logged_in = profile_user == null ? false : true;

    int my_profile_id = 0;
    if(is_logged_in) my_profile_id = profile_user.getProfileId();

    ArrayList<FeedPost> feedPost = (ArrayList<FeedPost>) session.getAttribute("favouritePosts");
%>

<head>
    <title>Favourites</title>
    <link rel="stylesheet" href="/favourites/favourites.css">
</head>
<body>

<header>
    <div class="header">
        <div class="header-left">
            <a href="../index.jsp">Marketplace</a>
        </div>
        <div class="header-right">
            <div class="user-options">
                <%if (is_logged_in) {%>
                <a href="/profile?userId=<%=my_profile_id%>">My Account</a>
                <span class="divider">|</span>
                <a href="/chat">Chat</a>
                <span class="divider">|</span>
                <a href="/upload/upload.jsp">Upload Post</a>
                <span class="divider">|</span>
                <a href="/logout">Log out</a>
                <%}else{%>
                <a href="/login">Log in</a>
                <span class="divider">|</span>
                <a href="/register">Sign up</a>
                <%}%>
            </div>
        </div>
    </div>
</header>

<div class="user-fav-posts">
    <% for(FeedPost post : feedPost){ %>
    <form action="/postpage" method="get">
        <input value="<%=post.getPost_id()%>" name="id" style="display: none">
        <div class="photo-container" onclick="this.closest('form').submit();">
            <div class="image">
                <img src= "/<%=post.getPhoto_address()%>" >
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
