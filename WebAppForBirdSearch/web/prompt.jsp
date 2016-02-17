<%-- 
    Document   : prompt
    Created on : Jan 24, 2016, 10:36:49 PM
    Author     : qiuyi
--%>

<%@page import="java.util.List"%>
<%@page import="model.Crawler"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%= request.getAttribute("doctype") %>

<% String url = "http://nationalzoo.si.edu/scbi/migratorybirds/featured_photo/"; %>
<% List<String> birds = new Crawler(url).crawler(); %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%--link style="stylesheet" type="text/css" href="./style.css"/--%>
        <title>Result Page</title>
    </head>
    <body>
        <div class="info">
            <b>Search the</b><br>
            <a href='http://nationalzoo.si.edu/scbi/migratorybirds/featured_photo/'><b>Smithsonian Migratory Bird Center Bird Photo Gallery</b></a><br>
            <b>for images of migratory birds.</b>
        </div>
        
        <div class="search">
            <form action="searchServlet" method="GET">
            Select a bird to display:<br>
            <select name='types'>
            <%
               for (int i = 0; i < birds.size(); i++) {
                   String name = birds.get(i);
               
            %>
                <option><%=name %></option>
            <%}%>
            </select><br><br>
            <input type="submit" value="Click Here">
            </form>
        </div>
    </body>
</html>
