<%-- 
    Document   : namai
    Created on : 21 Sept 2024, 21:41:00
    Author     : zygis
--%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

        <title>My first JSP</title>
        
        <style>                     
            TABLE, TD, TH, TR {                        
                border-collapse:collapse;                         
                border-width: 1px;                         
                border-style: solid;                         
                border-spacing: 5px;                         
                padding: 2px 5px;                     
            }                 
        </style> 
        
    </head>
    <body align="center">
        <h1>Hello World!</h1>
        <p style="color:red;">Komentaras negali buti tuscias</p>
        
        <form action="/javalab1" method="POST">
            
            <label for="vardas">Vartotojo vardas:</label>
            <input type="text" id="vardas" name="name" size="20px">           
            <label for="komentaras">Komentaras</label>
            <input type="text" id="komentaras" name="message" size="20px">
            <input type="submit" value="Siuusti">
            
        </form>
    
        <form action="/javalab1" method="POST">
    <label for="updateName">Vartotojo vardas (keisti visus komentarus į "Ištrintas"):</label>
    <input type="text" id="updateName" name="name" size="20px">
    <input type="hidden" name="action" value="markDeleted"/>
    <input type="submit" value="Pakeisti">
        </form>
        
        <hr>
        <div>
            <c:if test="${not empty msg}">
                <jsp:getProperty name="msg" property="name"/>:
                <jsp:getProperty name="msg" property="msg"/>
            </c:if>
        </div>
        <hr>
        <div align="center">
    <table id="messageTable">
        <tr>
            <th>ID</th>
            <th>Vardas</th>
            <th>Komentaras</th>
            <th>Data</th>
            <th>Ištrinti</th>
        </tr>
        <c:if test="${not empty msg_list}">
            <c:forEach var="m" items="${msg_list}">
                <tr>
                    <td>${m.id}&nbsp;&nbsp;</td>                          
                    <td>${m.name}&nbsp;&nbsp;</td>                          
                    <td>${m.message}&nbsp;&nbsp;</td>                          
                    <td>${m.time}&nbsp;&nbsp;</td> 
                    <td>
                        <!-- Sukuriame formą su Delete mygtuku -->
                        <form action="/javalab1" method="POST">
                            <!-- Paslėptas laukas veiksmo tipui -->
                            <input type="hidden" name="action" value="delete"/>
                            <!-- Paslėptas laukas su pranešimo ID -->
                            <input type="hidden" name="id" value="${m.id}"/>
                            <!-- Delete mygtukas -->
                            <input type="submit" value="Delete"/>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </c:if>
    </table>
</div>
        
    </body>
</html>
