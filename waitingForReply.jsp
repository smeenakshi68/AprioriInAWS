<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="classes.*" %>

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<jsp:useBean id="ids" class="classes.RunMain" scope="session"></jsp:useBean>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Generating associations rules...</title>
</head>
<body>
<%

String support = (String)request.getAttribute("support");
String confidence= (String)request.getAttribute("confidence");
String uuid=(String)request.getAttribute("uuid")+".txt";
session.setAttribute("support", support);
session.setAttribute("confidence", confidence);
session.setAttribute("uuid",uuid);
request.getRequestDispatcher("/return.jsp").forward(request,response);
 %>
</body>
</html>
