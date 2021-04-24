<%-- 
    Author     : wellwlds
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="/view/include/head.jsp" %>
        <title>Cassino Wellson - Usuários: cadastro</title>
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel="icon" type="image/png" sizes="32x32" href="${pageContext.request.contextPath}/img/favicon_io/favicon-32x32.png">
        <link rel="icon" type="image/png" sizes="16x16" href="${pageContext.request.contextPath}/img/favicon_io/favicon-16x16.png">
        <link rel="manifest" href="${pageContext.request.contextPath}/img/site.webmanifest">
    </head>
    <body>

        <div class="container">
            <h2 class="text-center">Crédito na carteira</h2>

            <form
                class="form"
                action="${pageContext.servletContext.contextPath}/user/carteira"
                enctype="multipart/form-data"
                method="POST">
				<input type="hidden" name="id" value="${usuario.id}">
				<h4 class="text-center">Insira aqui mais crédito para jogar</h4>
                <div class="form-group">
                    <label class="control-label">Moedas</label>
                    <input class="form-control" type="text" name="carteira" value="${usuario.carteira}" required/>
                </div>

                <div class="text-center">
                    <button class="btn btn-lg btn-primary" type="submit">Salvar</button>
                </div>
            </form>
        </div>

        <%@include file="/view/include/scripts.jsp" %>
        <script src="${pageContext.servletContext.contextPath}/assets/js/user.js"></script>
    </body>
</html>