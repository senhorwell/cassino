<%-- 
    Document   : update
    Created on : Sep. 2, 2020, 10:28:54 a.m.
    Author     : wellwlds
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib tagdir="/WEB-INF/tags/session" prefix="session"%>
<session:my_user context="${pageContext.servletContext.contextPath}"/>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="/view/include/head.jsp" %>
        <%@include file="/assets/css/style.jsp"  %>
        <title>Cassino Wellson - Usuários: atualização</title>
    </head>
    <body>
		<%@include file="../../header.jsp"%>
        <div class="container py-5">
            <h2 class="text-center">Edição do usuário <c:out value="${usuario.nome}"/></h2>

            <form
                class="form"
                action="${pageContext.servletContext.contextPath}/user/update"
                enctype="multipart/form-data"
                method="POST">
                
                <input type="hidden" name="id" value="${user.id}">

                <div class="form-group">
                    <label class="control-label" for="usuario-login">Login</label>
                    <input id="usuario-login" class="form-control" type="text" name="login" value="${user.login}" data-value="${user.login}" required autofocus/>

                    <p class="help-block"></p>
                </div>


                <div class="form-group">
                    <label class="control-label">Senha</label>
                    <input class="form-control password-input"
                           type="password" name="senha"
                           pattern=".{4,}" title="Pelo menos 4 caracteres."/>
                </div>

                <div class="form-group pwd-confirm">
                    <label class="control-label">Confirmar senha</label>
                    <input class="form-control password-confirm"
                           type="password" name="senha-confirmacao"
                           pattern=".{4,}" title="Pelo menos 4 caracteres."/>
                    <p class="help-block"></p>
                </div>

                <div class="form-group">
                    <label for="usuario-nome" class="control-label">Nome</label>
                    <input id="usuario-nome" class="form-control" type="text" name="nome" value="${user.nome}" required/>
                </div>


                <div class="form-group">
                    <label for="usuario-nasc" class="control-label">Data de nascimento</label>
                    <input id="usuario-nasc" class="form-control datepicker" type="date" name="nascimento"
                           placeholder="dd/mm/yyyy" value="${user.nascimento}"
                           pattern="\d{2}/\d{2}/\d{4}" required/>
                </div>

                <div class="form-group">
                    <label for="usuario-avatar">Foto do perfil</label>
                    <input type="file"
                           class="form-control" id="usuario-avatar"
                           name="avatar"
                           accept="image/*"/>
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