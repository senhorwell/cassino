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
        <title>Cassino Wellson - SlotMachine]</title>
        <%@include file="/assets/css/style.jsp"  %>
    </head>
    <body id="slotmachine">
        <%@include file="header.jsp"  %>
        <div class="container main">
            <div class="wrapper">
	            <div class="jumbotron">
	                <h1>Bem-vindo a Slot machine,
	                <c:out value="${usuario.nome}"/>!</h1>
	                <p>Insira o resultado desejado </p>
           	        <p>Carteira: <c:out value="${carteira}"/> </p>
	                	<form id = "game" class="form-signin" onsubmit="return showAlertSM();" action="${pageContext.servletContext.contextPath}/user/game/slotmachine?id=${usuario.id}" method="POST">
						<input type="hidden" name="id" value="${usuario.id}">
	                		<select id="resultA_id" name="resultado1" class="form-control" required>
	                        <option value="" class="disabled" disabled selected>Selecione</option>
	                        <option value="Maça">Maça</option>
	                        <option value="Pera">Pera</option>
	                        <option value="Melancia">Melancia</option>
	                        <option value="Melão">Melão</option>
	                    	</select>
	                    	
	                    	<select id="resultB_id" name="resultado2" class="form-control" required>
	                        <option value="" class="disabled" disabled selected>Selecione</option>
	                        <option value="Maça">Maça</option>
	                        <option value="Pera">Pera</option>
	                        <option value="Melancia">Melancia</option>
	                        <option value="Melão">Melão</option>
	                    	</select>
	                    	
	                    	<select id="resultC_id" name="resultado3" class="form-control" required>
	                        <option value="" class="disabled" disabled selected>Selecione</option>
	                        <option value="Maça">Maça</option>
	                        <option value="Pera">Pera</option>
	                        <option value="Melancia">Melancia</option>
	                        <option value="Melão">Melão</option>
	                    	</select>
	                    	
	                		<p> </p>
	                    	<button class="btn btn-lg btn-primary btn-block" type="submit">
	                        	Rodar jogo
	                    	</button>
	                    </form>
	                    <p> </p>
	                    	<a class="btn btn-lg btn-primary" href="${pageContext.servletContext.contextPath}/welcome.jsp">
	                        Volta a tela principal
	                    	</a> 
	            </div>
            </div>
        </div>

        <%@include file="/view/include/scripts.jsp"%>
        <script src="${pageContext.servletContext.contextPath}/assets/js/user.js"></script>
        <script src="${pageContext.servletContext.contextPath}/assets/js/popup.js"></script>   
    </body>
</html>