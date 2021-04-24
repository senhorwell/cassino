<%-- 
    Document   : welcome
    Author     : wellwlds
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="/view/include/head.jsp"  %>
        <title>Cassino Wellson - Blackjack]</title>
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel="icon" type="image/png" sizes="32x32" href="${pageContext.request.contextPath}/img/favicon_io/favicon-32x32.png">
        <link rel="icon" type="image/png" sizes="16x16" href="${pageContext.request.contextPath}/img/favicon_io/favicon-16x16.png">
        <link rel="manifest" href="${pageContext.request.contextPath}/img/site.webmanifest">
    </head>
    <body>
        
        <div class="container">
            
            <div class="jumbotron">
                <h1>Bem-vindo ao jogo 21,
                <c:out value="${usuario.nome}"/>!</h1>
                <p>Insira sua aposta e o resultado desejado (>15 vitoria) </p>
                <p>Carteira: <c:out value="${usuario.carteira}"/> </p>
                	<form enctype='multipart/form-data' id = "game" class="form-signin" onsubmit="return showAlertBJ();" action="${pageContext.servletContext.contextPath}/user/game/blackjack" method="POST">
                		<input type="hidden" name="id" value="${usuario.id}">
                		<input class="form-control" type="text" name="aposta" placeholder="Aposta" required>
                		<input id="result_id" class="form-control" type="text" name="resultado" placeholder="Resultado" required>
                		<p> </p>
                    	<button class="btn btn-lg btn-primary btn-block" type="submit">
                        	Concluir jogo
                    	</button>
                    </form>
                    <p> </p>
                    	<a class="btn btn-lg btn-primary" href="${pageContext.servletContext.contextPath}/welcome.jsp">
                        Volta a tela principal
                    	</a>
            </div>
        </div>

        <%@include file="/view/include/scripts.jsp"%>
        <script src="${pageContext.servletContext.contextPath}/assets/js/user.js"></script>
        <script src="${pageContext.servletContext.contextPath}/assets/js/popup.js"></script>   
    </body>
</html>