<div id="header">
 	<div class="container">
		<div class="d-flex justify-content-center align-items-center">
			<span class="logo"/>
		</div>
		<a class="btn btn-default" href="${pageContext.servletContext.contextPath}">
		    <p>Home</p>
		</a>
		<a class="btn btn-default" href="${pageContext.servletContext.contextPath}/user/game/blackjack?id=${usuario.id}">
		<input type="hidden" name="id" value="${user.id}">
		    <p>Blackjack</p>
		</a>
		<a class="btn btn-default" href="${pageContext.servletContext.contextPath}/user/game/slotmachine?id=${usuario.id}">
		<input type="hidden" name="id" value="${user.id}">
		    <p>Slot Machine</p>
		</a>
		<c:choose>
		   <c:when test="${personType=='1'}">
				<a class="btn btn-default" href="${pageContext.servletContext.contextPath}/admin">
			        <p>Administrador</p>
			    </a>
			</c:when>
		</c:choose>
		<a class="btn btn-default"
           href="${pageContext.servletContext.contextPath}/logout"
           data-toggle="tooltip"
           data-original-title="Logout">
           <p>
           	Sair
            <i class="fa fa-sign-out"></i>
           </p>
        </a>		
	</div>
 </div>