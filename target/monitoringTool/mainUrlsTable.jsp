<%--
  Created by IntelliJ IDEA.
  User: polozhevets
  Date: 29.08.20
  Time: 16:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="Model.Url" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="UTF-8" %>
<html>
<head>
    <title>Initial Data</title>
</head>
<body>
<%
    List<Url> monitoredURLS = (List<Url>) request.getAttribute("monitoredURLS");
%>
<h2>Set your monitoringDataStorage</h2>
<table border="1">
    <thead>
    <tr>
        <th>URL</th>
        <th>Min response time</th>
        <th>Max response time</th>
        <th>Monitoring time seconds</th>
        <th>Response code</th>
        <th>Min size</th>
        <th>Max size</th>
    </tr>
    </thead>
    <tbody>
    <%
        for (Url url : monitoredURLS){
    %>
    <tr>
        <td><%=url.getUrl()%></td>
        <td><%=url.getMinResponseTime()%></td>
        <td><%=url.getMaxResponseTime()%></td>
        <td><%=url.getMonitoringTimeSeconds()%></td>
        <td><%=url.getResponseCode()%></td>
        <td><%=url.getMinSize()%></td>
        <td><%=url.getMaxSize()%></td>
        <td>
            <form action="editorPage" method="post">
                <input type="hidden" name="url" value="<%=url.getUrl()%>">
                <input type="submit" value="Edit">
            </form>
        </td>
        <td>
            <form action="remover" method="post">
                <input type="hidden" name="url" value="<%=url.getUrl()%>">
                <input type="submit" value="Remove">
            </form>
        </td>
    </tr>
    <%
        }
    %>
    </tbody>
</table>
<Br>
<form action="addNewLink.jsp" method="post">
    <input type="submit" value="Add Link">
</form>
<Br>
<a href="monitoringTable">Start monitoring</a>
</body>
</html>