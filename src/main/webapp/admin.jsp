<%-- 
    Document   : admin
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
            <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>

		    <script>
		
		      
		        google.charts.load('current', {'packages':['corechart']});
		
		        function desenharPizza (){
		
	            var tabela = new google.visualization.DataTable();
	              var userList= ${userList};
					console.log(userList);
		            tabela.addColumn('string','nome');
	              tabela.addColumn('number','acessos');
	
	             for (i = 0; i < userList["users"].length; i++) {
	            	  console.log(i, userList["users"][i]["id"], userList["users"][i]["user"]);
	            	  tabela.addRows([
		           		   [userList["users"][i]["user"],userList["users"][i]["id"]]
		           		  ]);
	            	}
			
			        var grafico = new google.visualization.PieChart(document.getElementById('graficoPizza'));
			        grafico.draw(tabela);
			    }
		
		    google.charts.setOnLoadCallback(desenharPizza);
		
		
		    </script>
    </head>
	<body>
		<div class="container">
		<h1 class="text-center">Jogadores que jogaram mais partidas</h1>
	    <div id="graficoPizza"></div>
		</div>
	</body>
</html>