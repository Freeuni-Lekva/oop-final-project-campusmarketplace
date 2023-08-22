<%@ page import="marketplace.objects.FeedPost" %>
<%@ page import="marketplace.dao.PostDAO" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Map" %>
<%@ page import="marketplace.dao.FilterDAO" %>
<%@ page import="marketplace.objects.User" %>
<%@ page import="java.util.List" %>
<%@ page import="marketplace.constants.FilterConstants" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">

<%
  List<FeedPost> feedPost = (List<FeedPost>) session.getAttribute("feedPosts");
  boolean logged_in = session.getAttribute("user") == null ? false : true;
%>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Marketplace</title>
  <link rel="stylesheet" href="/homepage/homepage.css">
</head>

<body>
<div class="container">
  <header>
    <div class="container-header">
      <div class="header-left">
        <a href="/feedposts">Marketplace</a>
      </div>
      <div class="header-middle">
        <div class="search-bar">
          <form action="/search" method="post" accept-charset="utf-8">
            <input type="text" placeholder="Search..." id="search_in" name="query">
            <input type="hidden" name="filters" id="filters">
            <button type="submit" id="search">Search</button>
          </form>
        </div>
      </div>
      <div class="header-right">
        <div class="user-options">
          <a href="/getfavourites">Favorites</a>
          <span class="divider">|</span>
          <%if(logged_in){%>
          <a href="/profile?userId=<%=((User)session.getAttribute("user")).getProfileId()%>">My Account</a>
          <span class="divider">|</span>
          <a href="/chat">Chat</a>
          <span class="divider">|</span>
          <a href="/newpost">Upload Post</a>
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

  <nav class="vertical-navbar">
    <div class="category-container">
      <% for (String filter : FilterConstants.filters) { %>
      <div class="nav-item">
        <input type="checkbox" id="<%=FilterConstants.REV_FILTERS_MAP.get(filter)%>" name="filter">
        <label for="<%=FilterConstants.REV_FILTERS_MAP.get(filter)%>"><%=filter%></label>
      </div>
      <% } %>
    </div>
  </nav>
</div>

<div class="all-photos">
  <% for(FeedPost post : feedPost){ %>
  <form action="/postpage" method="get">
    <input value="<%=post.getPost_id()%>" name="post_id" style="display: none">
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

<script>

  filters = new Set();

  function click_handler(ev){
    let text = ev.target.id
    if(text==="") text=ev.target.for
    if(filters.has(text)){
      filters.delete(text);
    }else{
      filters.add(text);
    }

    let filts = "";

    filters.forEach((e) => {
      filts += e.toString()+" ";
    });
    filts = filts.trim();

    document.getElementById("filters").value = filts;
    console.log(filts);
  }

  document.getElementsByName("filter").forEach((i)=>{
    i.addEventListener("click", click_handler);
  });

</script>
</html>