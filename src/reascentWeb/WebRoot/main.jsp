<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>Reascent mian</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
        <frameset rows="50,*" frameborder="no" border="0" >
	           <frame src="reascent/html/benner.html" noresize="noresize" scrolling="no"/>
	           <frame src="reascent/html/topMenu.html" noresize="noresize" scrolling="no"/>
        </frameset>
        <noframes>
           <a>Your brower don't support the frames ,please check and update the IE version 3 or above.</a>
        </noframes>
</html>
