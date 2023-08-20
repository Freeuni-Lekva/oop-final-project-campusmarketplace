<%@ page import="marketplace.objects.User" %>
<%@ page import="marketplace.dao.UserDAO" %>
<%@ page import="javax.swing.*" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: effis
  Date: 7/24/2023
  Time: 7:50 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<% User user = (User) request.getAttribute("user"); %>
<% User other = (User) request.getAttribute("other"); %>
<% List<User> lastMessagedUsers = (List<User>) request.getAttribute("lastMessagedUsers"); %>

<head>
    <title>Chat</title>
    <link rel="stylesheet" href="/chat/chat.css">
</head>
<body>
<script>
    let ws;
    let receiverId;


    function connect(receiverId, name) {
        this.receiverId = receiverId;
        const host = document.location.host;
        const pathname = document.location.pathname;
        const log = document.getElementById("log");
        const chatTitle = document.getElementById("chatTitle");
        chatTitle.innerText = "Chat with " + name
        log.innerHTML = "";

        ws = new WebSocket("ws://" + host  + pathname + "/" + <%= user.getProfileId()%> + "/" + receiverId);

        ws.onmessage = function(event) {
            const log = document.getElementById("log");
            const message = JSON.parse(event.data);
            console.log(message);

            const messageElement = document.createElement("div");
            messageElement.classList.add("message");
            messageElement.classList.add(message.fromId === <%= user.getProfileId()%> ? "you" : "other");
            messageElement.innerHTML = message.content;

            const messageTimeElement = document.createElement("div");
            messageTimeElement.classList.add("time");
            messageTimeElement.classList.add(message.fromId === <%= user.getProfileId()%> ? "you" : "other");
            messageTimeElement.innerHTML = message.sendTime;

            log.append(messageElement);
            log.append(messageTimeElement)
        };
    }

    function send() {
        if (this.receiverId == null)
            return

        const content = document.getElementById("msg").value;

        const options = { year: 'numeric', month: 'short', day: '2-digit', hour: 'numeric', minute: '2-digit', second: '2-digit', hour12: true };
        const formattedDate = new Date().toLocaleString('en-US', options);

        const json = JSON.stringify({
            "messageId": 5,
            "fromId" : <%= user.getProfileId()%>,
            "toId": this.receiverId,
            "content": content,
            "sendTime": formattedDate
        });

        console.log(json)
        document.getElementById("msg").value = ""
        try {
            ws.send(json);
        } catch (error) {
            console.error(error);
        }
    }

    <% if (other != null) {%>

    window.onload = function() {
        connect(<%= other.getProfileId() %>, '<%= other.getFirstName() + " " + other.getSurname() %>');
    };

    <% } %>

    function changePrimaryColor(color) {
        document.documentElement.style.setProperty('--primary-color', color);
    }
</script>


<div id="lastMessagedUsers">
    <h3>Last Messaged Users</h3>
    <% for (User messagedUser : lastMessagedUsers) { %>
    <div class="user" onclick="connect(<%= messagedUser.getProfileId() %>,
                                       '<%= messagedUser.getFirstName() + " " + messagedUser.getSurname()%>');">
        <%= messagedUser.getFirstName() %> <%= messagedUser.getSurname() %>
    </div>
    <% } %>
    <a href="/feedposts" style="text-decoration: none; color: var(--primary-color);">
        <button style="padding: 10px; margin-top: 20px; background-color: var(--primary-color);
             color: #fff; border: none; border-radius: 4px; cursor: pointer; width: 100%">Marketplace</button>
    </a>
</div>
<div id="chatContainer">
    <h2 id="chatTitle"></h2>

    <div id="log" style="display: flex; flex-direction: column;"></div>
    <input type="text" id="msg" placeholder="Message"/>
    <div style="display: flex; justify-content: space-between; align-items: center;">
        <div>
            <button onclick="send();" id="sendButton">Send</button>
        </div>
        <div>
            <button class="colorButton" onclick="changePrimaryColor('#0084ff')">Blue</button>
            <button class="colorButton" onclick="changePrimaryColor('#ff0000')">Red</button>
            <button class="colorButton" onclick="changePrimaryColor('#00ff00')">Green</button>
        </div>
    </div>
</div>
</body>
</html>