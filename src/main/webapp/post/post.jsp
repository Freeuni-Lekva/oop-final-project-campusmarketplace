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
    System.out.println(post.getPhotos().size());
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
            <button type="button" id="prev">Previous Preview</button>
            <button type="button" id="next">Next Preview</button>
        </div>
    </div>

    <div class="product-details">
        <p class="product-description"><%=post.getDescription()%></p>
        <p class="product-price"><%=post.getPrice()%></p>
        <% if(is_logged_in) { %>
        <form action="/chat?otherUserId=<%=author_id%>" method="post">
            <button type="submit">Chat with <%=author.getFirstName()%></button>
        </form>
        <% if(author_id == my_profile.getProfileId()) { %>
            <a href="/deletepost?post_id=<%=post.getPost_id()%>">Delete Post</a>
        <% } %>
        <% } %>
        <div class="profile">
            <p class="profile-name"><%=author.getFirstName() + " " + author.getSurname()%></p>
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