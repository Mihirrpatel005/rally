<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%@ page import="com.samples.web.Models.Book" %>
    <%
    Object bookObj = request.getAttribute("book");
    Book book = null;
    if(bookObj instanceof Book) {
        book = (Book)bookObj;
    }
    %>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Library: <%= book.getTitle() %></title>
</head>
<body>
    <ul style="list-style-type:circle">
        <lh><h2><b><%= book.getTitle() %></b></h2></lh>
        <li> Authors: <%= book.getStringWithAuthors() %></li>
        <li> Publisher: <%= book.getPublisher() %></li>
        <li> Year: <%= book.getYear() %></li>
        
        <% if (book.getDescription() != null) { 
        %>
        <li> Edition: <%= book.getEdition() %></li>
        <% 
        } 
        %>
        
        <li> ISBN: <%= book.getISBN() %></li>
        <li> Count: <%= book.getCount() %></li>
        
        <% if (book.getDescription() != null) { 
        %>
        <li> Description: <%= book.getDescription() %></li>
        <% 
        } 
        %>
    </ul> 
</body>
</html>