<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="marketplace.constants.FilterConstants" %>
<%@ page import="marketplace.objects.Post" %>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">

<html>
<head>
    <title>Upload</title>
    <link rel="stylesheet" href="/upload/upload.css">
</head>
<body>

<%
    String err = request.getSession().getAttribute("error") == null ? "" : "error";
    Post post = (Post) session.getAttribute("editPost");
    boolean is_edit = false;
    if(post != null) is_edit = true;
%>

<div class="container">
    <div class="upload-container">
        <h1>Sell Your Item</h1>
        <form action="/newpost" method="post" enctype="multipart/form-data" accept-charset="utf-8">

            <% if (request.getSession().getAttribute("error") == null) {%>
                <%System.out.println("Upload Successful");%>
            <% } else {%>
                <h1>Error Messages:</h1>
                <h3><%=(String)(request.getSession().getAttribute("error"))%></h3>
            <% } %>

            <label for="itemPhotos">Upload Photos:</label>
            <input type="file" id="itemPhotos" name="itemPhotos" accept="image/*" multiple class="<%=err%>">

            <label for="title">Item Tile:</label>
            <textarea id="title" name="title" rows="1" cols="50" required class="<%=err%>"></textarea>

            <label for="description">Item Description:</label>
            <textarea id="description" name="description" rows="4" cols="50" required class="<%=err%>"></textarea>

            <h4>Item Category:</h4>
            <h5>Please choose 3 or less</h5>
            <div class="category-container">
                <% for (String filter : FilterConstants.filters) { %>
                <div class="category-item">
                    <input type="checkbox" id="<%=filter%>" name="<%=filter%>" class="<%=err%>">
                    <label for="<%=filter%>"><%=filter%></label>
                </div>
                <% } %>
            </div>

            <label for="price">Price:</label>
            <input type="number" id="price" name="price" step="0.01" required class="<%=err%>">

            <button type="submit">Upload</button>
        </form>
    </div>
</div>

</body>
</html>