<%@ page import="marketplace.dao.PostDAO" %>
<%@ page import="marketplace.objects.FeedPost" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="marketplace.dao.FilterDAO" %>
<%@ page import="java.util.Map" %>
<%@ page import="marketplace.dao.UserDAO" %>
<%@ page import="marketplace.objects.User" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<%
    User profile_user = ((User)session.getAttribute("user_profile"));
    User my_profile = ((User)session.getAttribute("user"));

    int profile_id = profile_user.getProfileId();
    int my_profile_id = my_profile.getProfileId();
    boolean is_logged_in = my_profile == null ? false : true;
    boolean is_my = false;
    if(is_logged_in){
        is_my = my_profile_id == profile_id;
    }

    List<FeedPost> feedPost = (ArrayList<FeedPost>) session.getAttribute("userFeedPosts");
%>

<head>
    <title>Profile</title>
    <link rel="stylesheet" href="/profile/profile.css">
</head>
<body>
<div class="container">
    <header>
        <div class="container-header">
            <div class="header-left">
                <a href="/feedposts">Marketplace</a>
            </div>
            <div class="header-right">
                <div class="user-options">
                    <a href="/favourites">Favorites</a>
                    <%if (is_logged_in) {%>
                    <%if (!is_my) {%>
                    <span class="divider">|</span>
                    <a href="/profile?userId=<%=my_profile_id%>">My Account</a>
                    <% } %>
                    <span class="divider">|</span>
                    <a href="/newpost">Upload Post</a>
                    <span class="divider">|</span>
                    <a href="/logout">Log out</a>
                    <% } %>
                </div>
            </div>
        </div>
    </header>

    <div class="vertical-info">
        <div class="info-item">
            <%=profile_user.getFirstName()%>
        </div>

        <div class="info-item">
            <%=profile_user.getSurname()%>
        </div>

        <div class="info-item">
            <%=profile_user.getPhoneNumber()%>
        </div>

        <div class="info-item">
            <%=profile_user.getEmail()%>
        </div>

        <div class="info-item">
            <%=profile_user.getBirthDate()%>
        </div>

        <%if (is_logged_in && !is_my) {%>
        <div class="info-item">
            <form action="/chat" method="post">
                <input type="hidden" name="otherUserId" value="<%=profile_id%>">
                <button class="chat-button" type="submit">Start Chat</button>
            </form>
        </div>
        <% } %>
    </div>
</div>

<div class="user-posts">
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