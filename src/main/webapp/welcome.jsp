<%-- 
    Document   : welcome
    Author     : wellwlds
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<session:my_user context="${pageContext.servletContext.contextPath}"/>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="/view/include/head.jsp"  %>
        <title>Cassino Wellson - Início</title>
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel="icon" type="image/png" sizes="32x32" href="${pageContext.request.contextPath}/img/favicon_io/favicon-32x32.png">
        <link rel="icon" type="image/png" sizes="16x16" href="${pageContext.request.contextPath}/img/favicon_io/favicon-16x16.png">
        <link rel="manifest" href="${pageContext.request.contextPath}/img/site.webmanifest">

        <style>
            table {
              font-family: arial, sans-serif;
              border-collapse: collapse;
              width: 100%;
            }

            td, th {
              border: 1px solid #dddddd;
              text-align: left;
              padding: 8px;
            }

            tr:nth-child(even) {
              background-color: #dddddd;
            }
            </style>
    </head>
    <body>
		<div class="container">
			<h1>Área do Administrador</h1>
			<a href="href="${pageContext.servletContext.contextPath}/admin"></a>
		</div>
        <div class="container" style="padding:0px;background: #ccc;text-align:center;position: relative;">
            <h1>Cassino Wellson</h1>
            <a class="btn btn-default" style="color:black;position:relative;right:150px;top: 10px;font-weight: bold;" href="${pageContext.servletContext.contextPath}/user/game/blackjack?id=${usuario.id}">
            <input type="hidden" name="id" value="${user.id}">
                <p>Blackjack</p>
            </a>
            <a class="btn btn-default" style="color:black;position:relative;right:10px;top: 10px;font-weight: bold;" href="${pageContext.servletContext.contextPath}/user/game/slotmachine?id=${usuario.id}">
            <input type="hidden" name="id" value="${user.id}">
                <p>Slot Machine</p>
            </a>
        </div>

        <div class="container" style="padding:0px;margin-top: 20px;" >
            <div style="display:inline-flex; justify-content: space-between;width: 100%;">

                <div class="col-3" style="background:red;height: 100px;text-align: center;box-shadow: 10px 10px 5px grey;">
                    <p><strong>Perdas</strong></p>
                    <h4><c:out value=" ${usuario.id}"/></h4>
                </div>
                
                <a href="${pageContext.servletContext.contextPath}/user/carteira?id=${usuario.id}" class="col-3" style="color: black;width: 100%;padding: 0px;">
	                <input type="hidden" name="id" value="${user.id}">
                    <div  style="background:yellow;height: 100px;text-align: center;box-shadow: 10px 10px 5px grey;">
                        <p><strong>Carteira</strong></p>
                       <h4><c:out value=" ${carteira} Moedas"/></h4>
                    </div>
                </a>
                <div class="col-3" style="background:green;height: 100px;text-align: center;box-shadow: 10px 10px 5px grey;">
                    <p><strong>Ganhos</strong></p>
                    <h4><c:out value=" ${usuario.id}"/></h4>
                </div>

            </div>
        </div>

        <div class="container" style="margin-top: 40px;padding: 0px;">
            <h4>Melhores jogadores</h4>
            <table style="box-shadow: 10px 10px 5px grey;">
                <tr>
                  <th>Nome</th>
                  <th>Vitórias</th>
                </tr>
                <%--= <c:forEach var="usuario" items="${requestScope.userGanhosList}">
                    <tr>
                        <td>
                            <span class="h4"><c:out value="${usuario.nome}"/></span>
                        </td>
                        <td>
                            <span class="h4"><c:out value="${usuario.ganhos}"/></span>
                        </td>
                    </tr>
                </c:forEach> --%>
            </table>
        </div>

            <div class="container">
                <p>
                    <a class="btn btn-lg btn-primary" href="${pageContext.servletContext.contextPath}/user">
                        Lista de usuários
                    </a>                 
                    <a class="btn btn-default"
                    href="${pageContext.servletContext.contextPath}/logout"
                    data-toggle="tooltip"
                    data-original-title="Logout">
                        <i class="fa fa-sign-out"></i>
                    </a>
                </p>
            </div>
        <%@include file="/view/include/scripts.jsp"%>
        <script src="${pageContext.servletContext.contextPath}/assets/js/user.js"></script>
    </body>
</html>