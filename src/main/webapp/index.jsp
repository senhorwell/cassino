<%-- 
    Document   : index
    Created on : Sep. 8, 2020, 10:23:38 a.m.
    Author     : wellwlds
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html >
    <head>
        <%@include file="/view/include/head.jsp"%>
        <%@include file="/assets/css/style.jsp"  %>
        <title>Cassino Wellson - Login</title>
    </head>
    <body id="login" class="main container">
        <div class="wrapper">
        	<div class="d-flex justify-content-center">
        		<span class="logo"></span>
        	</div>
            <form class="form-signin" action="${pageContext.servletContext.contextPath}/login" method="POST">
                <h2 class="form-signin-heading text-center">Por favor, faça login.</h2>

                <input class="form-control" type="text" name="login" placeholder="Usuário" required autofocus>
                <input class="form-control" type="password" name="senha" placeholder="Senha" required>
                <p class="help-block">Ainda não é cadastrado?
                    <a href="${pageContext.servletContext.contextPath}/user/create">
                        Clique aqui
                    </a>
                </p>

                <button class="btn btn-lg btn-primary btn-block" type="submit">Login</button>
            </form>
                        
        </div>

        <%@include file="/view/include/scripts.jsp"%>
        <script src="${pageContext.servletContext.contextPath}/assets/js/base.js"></script>
    </body>
</html>