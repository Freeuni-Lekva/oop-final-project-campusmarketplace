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
    <style>
        :root {
            --primary-color: #0084ff;
            --secondary-color: #f1f0f0;
        }

        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            background-color: #f5f5f5;
        }

        #lastMessagedUsers {
            width: 25%;
            background-color: var(--secondary-color);
            padding: 20px;
            box-sizing: border-box;
            overflow-y: auto;
            height: 100vh;
            border-right: 1px solid #ccc;
        }

        #lastMessagedUsers .user {
            padding: 10px;
            border-bottom: 1px solid #ccc;
            cursor: pointer;
        }

        #lastMessagedUsers .user:hover {
            background-color: #ddd;
        }

        #chatTitle {
            margin: 0;
            padding-bottom: 10px;
            font-size: 24px;
            color: #333;
        }

        #chatContainer {
            width: 75%;
            padding: 20px;
            box-sizing: border-box;
            display: flex;
            flex-direction: column;
            background-color: #fff;
            height: 100vh;
            overflow-y: auto;
        }

        .message {
            width: auto;
            max-width: 70%;
            padding: 10px;
            margin-bottom: 2px;
            box-sizing: border-box;
            display: inline-block;
            overflow-wrap: anywhere;
        }

        .message span {
            font-size: 0.7em;
            color: #888;
            display: block;
            text-align: right;
        }

        .message.you {
            background-color: var(--primary-color);
            color: white;
            align-self: flex-end;
            border-radius: 15px 0 15px 15px;
            text-align: right;
        }

        .message.other {
            background-color: #f1f0f0;
            align-self: flex-start;
            border-radius: 0 15px 15px 15px;
        }

        #log {
            width: 100%;
            height: 500px;
            border: 1px solid #ccc;
            padding: 10px;
            box-sizing: border-box;
            overflow-y: auto;
            margin-bottom: 10px;
        }

        #msg {
            width: 100%;
            padding: 10px;
            margin-top: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }

        #sendButton {
            width: 70px;
            padding: 10px;
            margin-top: 10px;
            background-color: var(--primary-color);
            color: #fff;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

        #sendButton:hover {
            background-color: #0066cc;
        }

        .time {
            font-size: 0.7em;
            color: #888;
            display: block;
            text-align: right;
            margin-bottom: 10px;
        }

        .time.you {
            color: gray;
            align-self: flex-end;
        }

        .time.other {
            color: gray;
            align-self: flex-start;
        }

        .colorButton {
            padding: 5px 10px;
            background-color: #f1f0f0;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            margin-left: 5px;
        }

        .colorButton:hover {
            background-color: #ddd;
        }
    </style>
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
    <a href="/home" style="text-decoration: none; color: var(--primary-color);">
        <button style="padding: 10px; margin-top: 20px; background-color: var(--primary-color);
             color: #fff; border: none; border-radius: 4px; cursor: pointer; width: 100%">Home</button>
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