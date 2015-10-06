<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%session.invalidate(); %>
<p>Session Expired!</p>
<table>
<tr>
<td><font face="courier" size="2" color="gray"><a href="welcome.jsp"><input type="button" value="HOME" /></a></font></td>
</tr>
</table>
</body>
</html>
