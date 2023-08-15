<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Upload</title>
    <link rel="stylesheet" href="upload.css">
</head>
<body>

<div class="upload-container">
    <h1>Sell Your Item</h1>
    <form action="#" method="post" enctype="multipart/form-data">
        <label for="itemPhotos">Upload Photos:</label>
        <input type="file" id="itemPhotos" name="itemPhotos" accept="image/*" multiple required>

        <label for="itemDescription">Item Description:</label>
        <textarea id="itemDescription" name="itemDescription" rows="4" required></textarea>

        <label for="itemPrice">Price:</label>
        <input type="number" id="itemPrice" name="itemPrice" step="0.01" required>

        <button type="submit">Upload</button>
    </form>
</div>

</body>
</html>