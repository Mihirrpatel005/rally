<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%@ page import="com.samples.web.Models.Category, com.samples.web.Models.Book" %>
    <%
    Object categoryObj = request.getAttribute("category");
    Category category = null;
    if(categoryObj instanceof Category) {
        category = (Category)categoryObj;
    }
    %>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Library: <%= category.getName() %></title>
</head>
<body>
    <h2>You are viewing <i><%= category.getName() %></i> category page</h2>
    <%
    if (category.getBooksDictionary().values().size() == 0) {
    %>
    <h3>Book list is empty</h3>
    <%
    } else {
    %>
    <h3>Books in this category</h3>
    <ul style="list-style-type:disc">
    <%
        for(Book book : category.getBooksDictionary().values()) {
    %>
        <li>
            <a href="<%=request.getContextPath()%>/home?bookid=<%= book.getISBN() %>">
                <u><%= book.getTitle() %></u></a> by <%= book.getStringWithAuthors() %>
        </li>
    <%
        }
    }
    %>
    </ul> 
</body>
</html>