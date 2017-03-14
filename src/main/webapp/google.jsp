<%-- 
    Document   : fblogin
    Created on : Feb 6, 2017, 4:18:25 PM
    Author     : dfChicken
--%>

<%@page import="service.gg.GGConnection"%>
<%@page import="service.gg.GooglePojo"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<html>
    <head>
        <title>DEMO - Login With Google using Java</title>
        <link href='bootstrap.min.css' rel='stylesheet' type='text/css'/>
    </head>
    <body>

        <%GooglePojo gp = (GooglePojo) request.getAttribute(GGConnection.AUTH);%>
        <div style="width:400px;margin:auto;padding-top:30px;">
            <table class="table table-bordered">
                <tr>
                    <td>User ID</td>
                    <td><%=gp.getId()%></td>
                </tr>
                <tr>
                    <td>Name</td>
                    <td><%=gp.getName()%></td>
                </tr>
                <tr>
                    <td>Email</td>
                    <td><%=gp.getEmail()%></td>
                </tr>


            </table> 
        </div>

    </body>
</html>