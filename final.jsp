<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
   <%@ page import="classes.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Request continued..</title>
</head>
<body>
<p>
Processing......
</p>
<% 
String support = request.getParameter("support");
String confidence  = request.getParameter("confidence");
String uuid = request.getParameter("uuid");%>
<jsp:useBean id="finalSet" class="classes.RunMain" scope="session">
<% 
String[] args = {support,confidence,uuid};
finalSet.run(args); %>
</jsp:useBean>
<p>
Your request has been processed click below to see result...
</p>
<a href="output.jsp?uuid=<%=uuid%>">Show Association Rules</a>
</body>
</html>
