<%@ page import="marketplace.objects.User" %>
<%@ page import="marketplace.dao.UserDAO" %>
<%@ page import="javax.swing.*" %><%--
  Created by IntelliJ IDEA.
  User: effis
  Date: 7/24/2023
  Time: 7:50 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<% User user = (User) session.getAttribute("user"); %>

<head>
    <title>Chat</title>
</head>
<body>
<script>
    let ws;
    let receiverId;

    function connect() {
        receiverId = document.getElementById("receiverId").value;

        const host = document.location.host;
        const pathname = document.location.pathname;
        console.log("ws://" + host  + pathname + "/" + <%= user.getProfileId()%> + "/" + receiverId)
        ws = new WebSocket("ws://" + host  + pathname + "/" + <%= user.getProfileId()%> + "/" + receiverId);

        ws.onmessage = function(event) {
            var log = document.getElementById("log");

            var message = JSON.parse(event.data);
            console.log(message)

            log.innerHTML += message.fromId + "(" + message.sendTime + ")" + " : " + message.content + "\n";
        };
    }

    function send() {
        if (receiverId == null)
            return

        const content = document.getElementById("msg").value;

        const options = { year: 'numeric', month: 'short', day: '2-digit', hour: 'numeric', minute: '2-digit', second: '2-digit', hour12: true };
        const formattedDate = new Date().toLocaleString('en-US', options);

        const json = JSON.stringify({
            "messageId": 5,
            "fromId" : <%= user.getProfileId()%>,
            "toId": receiverId,
            "content": content,
            "sendTime": formattedDate
        });

        console.log(json)
        try {
            ws.send(json);
        } catch (error) {
            console.error(error);
        }
    }
</script>
<table>
    <tr>
        <td colspan="2">
            <input type="text" id="receiverId" placeholder="receiverId"/>
            <button type="button" onclick="connect();" >Connect</button>
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
            <button type="button" onclick="send();" >Send</button>
        </td>
    </tr>
</table>
</body>
</html>