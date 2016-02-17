<%-- 
    Document   : result
    Created on : Jan 24, 2016, 10:37:11 PM
    Author     : qiuyi
--%>

<%@page import="model.Crawler"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%= request.getAttribute("doctype") %>
<% String birdType = (String) request.getAttribute("birdType");%>
<% List rets = (List)request.getAttribute("rets");%>
<% String url = "http://nationalzoo.si.edu/scbi/migratorybirds/featured_photo/"; %>
<% List<String> birds = new Crawler(url).crawler(); %>
<% String path = "http://nationalzoo.si.edu/";%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link style="stylesheet" type="text/css" href="style.css"/>
        <title>Bird Page</title>
    </head>
    <body class="body">
        <h3>Here is a picture of a  <%= birdType%></h3>
        <h3>from the <a href="<%= url%>">
                <b>Smithsonian Migratory Bird Center Bird Photo Gallery</b>
            </a>
        </h3>
        <% Map cur = (HashMap)rets.get((int)(Math.random() * rets.size())); %>
        <% String imgUrl = (String)cur.get("src");%>
        <% String author = (String)cur.get("author");%>
        <img src="<%= imgUrl %>"><br>
        <%= author%>
        
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
