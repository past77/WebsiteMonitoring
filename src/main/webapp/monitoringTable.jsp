<%--
  Created by IntelliJ IDEA.
  User: polozhevets
  Date: 30.08.20
  Time: 20:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="Model.Results" %>
<%@ page import="java.util.List" %>
<%@ page import="Model.Results" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="UTF-8" %>
<html>
<head>
    <title>Monitoring Table</title>
    <script language="JavaScript" defer>
        var warningSound = new Audio("http://localhost:8080/monitoringTool_war/sounds/checkaWarn.mp3");
        var criticalSound = new Audio("http://localhost:8080/monitoringTool_war/sounds/petardaCrtcl.mp3");
        <%
            if ((boolean) request.getAttribute("isWarning")){
        %>
        warningSound.play();
        <%
            } if ((boolean) request.getAttribute("isCritical")) {
        %>
        criticalSound.play();
        <%
            }
        %>
    </script>
</head>
<body>
<%
    response.setIntHeader("Refresh", 5);
%>
<table border="1">
    <thead>
    <tr>
        <th>URL</th>
        <th>Page Size</th>
        <th>Response Code</th>
        <th>Response Time</th>
        <th>Monitoring Time Left</th>
        <th>Status</th>
    </tr>
    </thead>
    <tbody>
    <%
        List<Results> monitoringResult = (List<Results>) request.getAttribute("gatheredData");
    %>
    <%
        for (Results data : monitoringResult){
    %>
    <tr>
        <td><%=data.getUrl()%></td>
        <td><%=data.getPageSize()%></td>
        <td><%=data.getResponseCode()%></td>
        <td><%=data.getResponseTime()%></td>
        <td><%=data.getMonitoringTimeLeft()%></td>
        <td><%=data.getStatus()%></td>

        <td>
            <form action="monitoringTable" method="post">
                <input type="hidden" name="buttonStop" value="<%=data.getUrl()%>">
                <input type="submit" value="Stop">
            </form>
        </td>
        <td rowspan="1">
            <form action="monitoringTable" method="post">
                <input type="hidden" name="buttonRun" value="<%=data.getUrl()%>">
                <input type="submit" value="Run">
            </form>

        <td>

    </tr>

    <%
        }
    %>

    </tbody>
</table>
<a href="mainUrlsTable"> Back to Main Table</a>
</body>
</html>