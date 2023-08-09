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
    </style>
</head>
<body>
<script>
    let ws;
    let receiverId;

    function connect(receiverId) {
        this.receiverId = receiverId

        const host = document.location.host;
        const pathname = document.location.pathname;
        const log = document.getElementById("log");
        log.innerHTML = ""

        console.log("ws://" + host  + pathname + "/" + <%= user.getProfileId()%> + "/" + receiverId)
        ws = new WebSocket("ws://" + host  + pathname + "/" + <%= user.getProfileId()%> + "/" + receiverId);

        ws.onmessage = function(event) {
            const log = document.getElementById("log");
            const message = JSON.parse(event.data);
            console.log(message)

            log.innerHTML += message.fromId + "(" + message.sendTime + ")" + ": " + message.content + "\n";
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
    <table>
        <tr>
            <td colspan="2">
                <input type="number" id="receiverId" placeholder="receiverId"/>
                <button type="button" onclick="connect(document.getElementById('receiverId').value);" >Connect</button>
            </td>
        </tr>
        <tr>
            <td>
                <textarea readonly="true" rows="10" cols="80" id="log"></textarea>
            </td>
        </tr>
        <tr>
            <td>
                <input type="text" size="51" id="msg" placeholder="Message"/>
                <button type="button" onclick="send();" id="sendButton" >Send</button>
            </td>
        </tr>
    </table>
</div>
</body>
</html>