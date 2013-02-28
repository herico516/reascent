<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  
  <body>
    This is my JSP page. <br>
    <form action="login.action" method="post">
		   UserName<input type="text" name="username"/><br/>
		   Password<input type="text" name="password"/><br/>
		   <input type="submit" value="Login"/> 
    </form>
    
    <s:text name="com.msg.test" ></s:text>
    
    <s:form action="login">
		   <s:textfield name="username" key="user" />
		   <s:textfield name="password" key="pass"  />
		   <s:submit key="login"/> 
    </s:form>
    
    
  </body>
</html>
