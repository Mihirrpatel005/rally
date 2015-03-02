<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Library: Home</title>
</head>
<body>
   <%@ page import="java.util.ArrayList, com.samples.web.Models.Category" %>
   <%
   Object categoryArrayList = request.getAttribute("categoryArrayList");
   if(categoryArrayList instanceof ArrayList) {
	   ArrayList<Category> categories = (ArrayList<Category>) categoryArrayList;
    %>
   <h1>List of categories</h1>
   <%
       for(Category category : categories) {
    	   String name = category.getName();
    %>
   <a href="<%=request.getContextPath()%>/home?category=<%= name %>"><h3><%= name %> (<%= category.getBookCount() %>)</h3></a>
   <%
       }
   }
   %>
</body>
</html>
