<%@ page import="marketplace.dao.PostDAO" %>
<%@ page import="marketplace.objects.Post" %>
<%@ page import="marketplace.objects.User" %>
<%@ page import="marketplace.objects.Photo" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    Post post = (Post) session.getAttribute("post");
    User my_profile = ((User)session.getAttribute("user"));
    boolean is_logged_in = my_profile == null ? false : true;
    int author_id = post.getProfile_id();
    User author = (User) session.getAttribute("post_author");
    ArrayList<Photo> photos = post.getPhotos();
    int index = 0;
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
        <img src="<%=photos.get(index).getPhoto_url()%>" len="<%=photos.size()%>" id="img" alt="Product Photo">
        <div class="photo-select">
            <button class="all-buttons" type="button" id="prev">Previous Preview</button>
            <button class="all-buttons" type="button" id="next">Next Preview</button>
        </div>
    </div>

    <div class="product-details">
        <p class="product-title"><%=post.getTitle()%></p>
        <p class="product-description"><%=post.getDescription()%></p>
        <p class="product-price">Price: <%=post.getPrice()%></p>

        <% if(is_logged_in) { %>
        <% if(author_id == my_profile.getProfileId()) { %>
        <div class="my-post-options">
            <a href="/deletepost?post_id=<%=post.getPost_id()%>">Delete Post</a>
            <a href="/editpost?post_id=<%=post.getPost_id()%>">Edit Post</a>
        </div>
        <% } else { %>
        <form action="/chat?otherUserId=<%=author_id%>" method="post">
            <button class="all-buttons" type="submit">Chat with <%=author.getFirstName()%></button>
        </form>
        <% } %>
        <% } %>
        <div class="manage-post-options">
        <% if (!post.isFavourite()) { %>
        <div class="favourites">
            <a href="/addfavourite?post_id=<%=post.getPost_id()%>">Add To Favourites</a>
        </div>
        <% } else { %>
        <div class="favourites">
            <a href="/removefavourite?post_id=<%=post.getPost_id()%>">Remove From Favourites</a>
        </div>
        <% } %>
        <div class="profile">
            <a href="/profile?userId=<%=author_id%>">Visit <%=author.getFirstName() + " " + author.getSurname()%></a>
        </div>
        <div class="marketplace">
            <a href="/feedposts">Back To Marketplace</a>
        </div>
    </div>
    </div>
</div>
<script>
    let photo_list = [];
    let index = 0;
    let img = document.getElementById("img");
    <% for(Photo photo : photos){ %>
    photo_list.push("<%=photo.getPhoto_url()%>");
    <% } %>
    document.getElementById("prev").addEventListener("click", (e)=>{

    });

    document.getElementById("next").addEventListener("click", (e)=>{
        index++;
        index %= photo_list.length;
        img.src = photo_list[index];
    });
    document.getElementById("prev").addEventListener("click", (e)=>{
        index--;
        if (index < 0) index = photo_list.length + index;
        img.src = photo_list[index];
    });
</script>
</body>
</html>