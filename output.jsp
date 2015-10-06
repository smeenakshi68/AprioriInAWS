<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="java.io.*" %>
<%@ page import="classes.*" %>
<%@ page import="java.util.*" %>
<%@ page import="com.amazonaws.*" %>
<%@ page import="com.amazonaws.auth.*" %>
<%@ page import="com.amazonaws.services.ec2.*" %>
<%@ page import="com.amazonaws.services.ec2.model.*" %>
<%@ page import="com.amazonaws.services.s3.*" %>
<%@ page import="com.amazonaws.services.s3.model.*" %>
<%@ page import="com.amazonaws.services.dynamodbv2.*" %>
<%@ page import="com.amazonaws.services.dynamodbv2.model.*" %>
<%@ page import="java.io.*"%>
<%@ page import= "com.amazonaws.AmazonClientException"%>
<%@ page import ="com.amazonaws.AmazonClientException"%>
<%@ page import= "com.amazonaws.AmazonServiceException"%>
<%@ page import= "com.amazonaws.auth.profile.ProfileCredentialsProvider"%>
<%@ page import= "com.amazonaws.services.s3.*"%>



<% // Share the client objects across threads to
    // avoid creating new clients for each web request
   AmazonS3           s3 = null;
   String uuid = request.getParameter("uuid");
    /*AWS bean stalk app.....
     */
    if (request.getMethod().equals("HEAD")) return;
    if (s3 == null) {
        AWSCredentialsProvider credentialsProvider = new ClasspathPropertiesFileCredentialsProvider();
            s3     = new AmazonS3Client(credentialsProvider);
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8">
    <title>Hello AWS Web World!</title>
    <link rel="stylesheet" href="styles/styles.css" type="text/css" media="screen">
</head>
<body bgcolor="White">
<h2>Association Rules:</h2>
<hr>
<% 
  String bucketNameOut     = "newtext-apriori";
  String bucketNameIn     = "text-apriori";
	String keyNameOut        = "result-"+uuid;
	String keyNameIn        = uuid;
	S3Object object = s3.getObject(new GetObjectRequest(bucketNameOut, keyNameOut));
    System.out.println("Content-Type: "  + object.getObjectMetadata().getContentType());
        BufferedReader reader = new BufferedReader(new InputStreamReader(object.getObjectContent()));
        while (true) {
            String line = reader.readLine();
            if (line == null) break;

            out.println("    " + line);
            %>
            <br/>
            <%}
        out.println();
      session.invalidate();  %>
<table>
<tr>
<td><font face="courier" size="2" color="gray"><a href="welcome.jsp"><input type="button" value="HOME" /></a></font></td>
</tr>
</table>
</body>
</html>
