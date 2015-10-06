<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ page import="classes.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:useBean id="item" class="classes.RunMain" scope="session"></jsp:useBean>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Data Uploaded in S3</title>
</head>
<body>
<p>
Your data is uploaded. Please click to process request....
</p>
<% String support =(String)session.getAttribute("support"); 
String confidence = (String)session.getAttribute("confidence");
String uuid =(String)session.getAttribute("uuid");
%>

<form name="frm" method="post" action="final.jsp">
Min Support : <input type="text" name="support" value="<%=support %>" readonly/><br></br>
Min Confidence: <input type="text" name="confidence" value="<%=confidence %>" readonly/><br></br>
Request Id :<input type="text" name="uuid" value="<%=uuid%>" readonly/><br></br>
<input type="submit" name="submit" value="click to process..."/>
</form>
</body>
</html>
