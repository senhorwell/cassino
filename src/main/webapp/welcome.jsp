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
        <title>Cassino Wellson - Início</title>
        <%@include file="/view/include/head.jsp"  %>
        <%@include file="/assets/css/style.jsp"  %>
    </head>
    <body id="welcome">
        
		<%@include file="header.jsp"  %>
		
        <div id="card-box" class="container" >
            <div>
                <div class="col-3 perda">
                    <p><strong>Perdas</strong></p>
                    <h4><c:out value=" ${usuario.id}"/></h4>
                </div>
                
                <a href="${pageContext.servletContext.contextPath}/user/carteira?id=${usuario.id}" class="col-3 carteira">
	                <input type="hidden" name="id" value="${user.id}">
                    <div>
                        <p><strong>Carteira</strong></p>
                       <h4><c:out value=" ${carteira} Moedas"/></h4>
                    </div>
                </a>
                <div class="col-3 ganho">
                    <p><strong>Ganhos</strong></p>
                    <h4><c:out value=" ${usuario.id}"/></h4>
                </div>

            </div>
        </div>

        <div id="best-players" class="container">
            <h4>Melhores jogadores</h4>
            <table>
                <tr>
                  <th>Nome</th>
                  <th>Vitórias</th>
                </tr>
                <c:forEach var="usuario" items="${requestScope.userList}">
                    <tr>
                        <td>
                            <span class="h4"><c:out value="${usuario.login}"/></span>
                        </td>
                        <td>
                            <span class="h4"><c:out value="${usuario.id}"/></span>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>

            <div class="container p-0 pt-5">
                <p>
                	<c:if test= "${personType == 1}">
	                    <a class="btn btn-lg btn-primary" href="${pageContext.servletContext.contextPath}/user">
	                        Lista de usuários
	                    </a>                 
                    </c:if>
                </p>
            </div>
        <%@include file="/view/include/scripts.jsp"%>
        <script src="${pageContext.servletContext.contextPath}/assets/js/user.js"></script>
    </body>
</html>