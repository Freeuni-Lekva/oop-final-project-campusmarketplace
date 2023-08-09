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
<% User user = (User) session.getAttribute("user"); %>
<% List<User> lastMessagedUsers = (List<User>) request.getAttribute("lastMessagedUsers"); %>

<head>
    <title>Chat</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            display: flex;
        }

        #lastMessagedUsers {
            width: 20%;
            background-color: #f5f5f5;
            padding: 20px;
            box-sizing: border-box;
            overflow-y: auto;
        }

        #lastMessagedUsers .user {
            padding: 10px;
            border-bottom: 1px solid #ccc;
            cursor: pointer;
        }

        #lastMessagedUsers .user:hover {
            background-color: #ddd;
        }

        #chatContainer {
            width: 80%;
            padding: 20px;
            box-sizing: border-box;
        }

        .message {
            width: 70%;
            min-width: 70%;
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
            background-color: #0084ff;
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
            width: calc(100% - 75px);
        }

        #sendButton {
            width: 70px;
        }

        .time {
            font-size: 0.7em;
            color: #888;
            display: block;
            text-align: right;
            margin-bottom: 10px; /* Adjust margin as desired */
        }

        .time.you {
            /* Styles for time element when the message is from the user */
            color: gray; /* Change color as desired */
            align-self: flex-end;
        }

        .time.other {
            /* Styles for time element when the message is from the other user */
            color: gray; /* Change color as desired */
            align-self: flex-start;
        }
    </style>
</head>
<body>
<script>
    let ws;
    let receiverId;


    function connect(receiverId) {
        this.receiverId = receiverId;
        const host = document.location.host;
        const pathname = document.location.pathname;
        const log = document.getElementById("log");
        const chatTitle = document.getElementById("chatTitle");
        chatTitle.innerText = "Chat with "
        log.innerHTML = "";

        console.log("ws://" + host  + pathname + "/" + <%= user.getProfileId()%> + "/" + receiverId);
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
</script>
<div id="lastMessagedUsers">
    <h3>Last Messaged Users</h3>
    <% for (User messagedUser : lastMessagedUsers) { %>
    <div class="user" onclick="connect(<%= messagedUser.getProfileId() %>);">
        <%= messagedUser.getFirstName() %> <%= messagedUser.getSurname() %>
    </div>
    <% } %>
</div>
<div id="chatContainer">
    <h2 id="chatTitle"></h2>
    <div id="log" style="display: flex; flex-direction: column;"></div>
    <input type="text" id="msg" placeholder="Message"/>
    <button onclick="send();" id="sendButton" >Send</button>
</div>
</body>
</html>