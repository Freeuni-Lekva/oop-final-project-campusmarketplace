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
    String title = "";
    String description = "";
    double price = 0;
    if(post != null) {
        is_edit = true;
        title = post.getTitle();
        description = post.getDescription();
        price = post.getPrice();
    }
    String form_action = is_edit == false ? "/newpost" : "/editpost?post_id=" + Integer.toString(post.getPost_id());
%>

<div class="container">
    <div class="upload-container">
        <form action="<%=form_action%>" method="post" enctype="multipart/form-data" accept-charset="utf-8">
            <% if (is_edit) { %>
                <input type="hidden" value="<%=post.getPost_id()%>" name="post_id">
            <% } %>

            <% if (request.getSession().getAttribute("error") == null) {%>
                <%System.out.println("Upload Successful");%>
            <% } else {%>
                <h1>Error Messages:</h1>
                <h3><%=(String)(request.getSession().getAttribute("error"))%></h3>
            <% } %>
            <div class="photo-container">
                <div class="upload-options">
                    <label for="itemPhotos">Upload Photos:</label>
                    <input type="file" id="itemPhotos" name="itemPhotos" accept="image/*" multiple class="<%=err%>">
                </div>
                <div id="latestImagePreview" class="latest-image-preview"></div>
                <div id="imagePreview" class="image-preview"></div>
                <div class="photo-options">
                    <div class="photo-select">
                        <button type="button" id="prev">Previous Preview</button>
                        <button type="button" id="next">Next Preview</button>
                    </div>
                    <button type="button" id="del">Delete Photo</button>
                </div>
            </div>

            <div class="info-container">
                <label for="title">Item Tile:</label>
                <textarea id="title" name="title" rows="1" cols="50" required class="<%=err%>"><%=title%></textarea>

                <label for="description">Item Description:</label>
                <textarea id="description" name="description" rows="4" cols="50" required class="<%=err%>"><%=description%></textarea>

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
                <input type="number" id="price" name="price" step="1" required class="<%=err%>" value="<%=price%>">

                <button type="submit">Upload</button>
            </div>
        </form>
    </div>
</div>

<script>
    document.addEventListener("DOMContentLoaded", function() {
        const itemPhotosInput = document.getElementById("itemPhotos");
        const latestImagePreview = document.querySelector(".latest-image-preview");
        let previewImages = [];
        let img_files = []
        let index=-1;
        itemPhotosInput.addEventListener("change", function() {
            const files = itemPhotosInput.files;

            latestImagePreview.innerHTML = "";

            for (const file of files) {
                const image = document.createElement("img");
                image.src = URL.createObjectURL(file);
                img_files.push(file);
                console.log(image);
                image.style.maxWidth = "100%";
                image.style.marginBottom = "10px";
                latestImagePreview.innerHTML = "";
                latestImagePreview.appendChild(image);
                previewImages.push(image);
                addFileFromFileList(file);
                index++;
            }
        });

        document.getElementById("prev").addEventListener("click", (e)=>{
            if (previewImages.length == 0) return;
            index--;
            index %= previewImages.length;
            if (index < 0) index = previewImages.length + index;

            latestImagePreview.innerHTML = "";
            latestImagePreview.appendChild(previewImages[index]);
        });

        document.getElementById("next").addEventListener("click", (e)=>{
            if (previewImages.length == 0) return;
            index++;
            index %= previewImages.length;

            latestImagePreview.innerHTML = "";
            latestImagePreview.appendChild(previewImages[index]);
        });
        function removeFileFromFileList(index) {
            const dt = new DataTransfer()
            const input = document.getElementById('itemPhotos')
            const { files } = input

            for (let i = 0; i < files.length; i++) {
                const file = files[i]
                if (index !== i)
                    dt.items.add(file) // here you exclude the file. thus removing it.
            }

            input.files = dt.files // Assign the updates list
        }

        function addFileFromFileList(filee) {
            const dt = new DataTransfer()
            const input = document.getElementById('itemPhotos')
            const { files } = input

            for (let i = 0; i < img_files.length; i++) {
                const file = img_files[i]
                dt.items.add(file) // here you exclude the file. thus removing it.
            }

            input.files = dt.files // Assign the updates list
        }
        document.getElementById("del").addEventListener("click", (e)=>{

            if (index > -1) {
                previewImages.splice(index, 1);
                img_files.splice(index, 1);
                removeFileFromFileList(index)
                latestImagePreview.innerHTML = "";
            }
            index--;
            if (previewImages.length != 0){
                index %= previewImages.length;
                if (index < 0) index = previewImages.length + index;
                latestImagePreview.appendChild(previewImages[index]);
            }
        });
    });


</script>

</body>
</html>