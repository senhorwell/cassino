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
        <%@include file="/assets/css/style.jsp"  %>
        <title>Cassino Wellson - Início</title>	
        <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
		
		<link rel="stylesheet" href="/assets/css/style.css">
	    <script>
        	google.charts.load('current', {'packages':['corechart']});
        	function bestPlayers(){
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
			
				var grafico = new google.visualization.PieChart(document.getElementById('bestPlayers'));
				grafico.draw(tabela);
			}
		    google.charts.setOnLoadCallback(bestPlayers);
		    
		    function bestGame(){
            	var tabela = new google.visualization.DataTable();
              	var userList= ${userList};
				console.log(userList);
	            tabela.addColumn('string','nome');
              	tabela.addColumn('number','acessos');
	
             	/*for (i = 0; i < userList["users"].length; i++) {
					console.log(i, userList["users"][i]["id"], userList["users"][i]["user"]);
					tabela.addRows([
						[userList["users"][i]["user"],userList["users"][i]["id"]]
					]);
          		}*/
             	tabela.addRows([
             		["Blackjack",2000],
             		["Slotmachine",1500]
				]);
				var grafico = new google.visualization.PieChart(document.getElementById('bestGame'));
				grafico.draw(tabela);
			}
		    google.charts.setOnLoadCallback(bestGame);
		    
		    function balancaBlackjack(){
            	var tabela = new google.visualization.DataTable();
              	var userList= ${userList};
				console.log(userList);
	            tabela.addColumn('string','nome');
              	tabela.addColumn('number','balanca');
	
             	/*for (i = 0; i < userList["users"].length; i++) {
					console.log(i, userList["users"][i]["id"], userList["users"][i]["user"]);
					tabela.addRows([
						[userList["users"][i]["user"],userList["users"][i]["id"]]
					]);
          		}*/
             	tabela.addRows([
             		["Ganhos",1234],
             		["Perdas",500]
				]);
				var grafico = new google.visualization.PieChart(document.getElementById('balancaBlackjack'));
				grafico.draw(tabela,options);
			}
		    google.charts.setOnLoadCallback(balancaBlackjack);
		    
		    function balancaSlotmachine(){
            	var tabela = new google.visualization.DataTable();
              	var userList= ${userList};
				console.log(userList);
	            tabela.addColumn('string','nome');
              	tabela.addColumn('number','balanca');
	
             	/*for (i = 0; i < userList["users"].length; i++) {
					console.log(i, userList["users"][i]["id"], userList["users"][i]["user"]);
					tabela.addRows([
						[userList["users"][i]["user"],userList["users"][i]["id"]]
					]);
          		}*/
             	tabela.addRows([
             		["Ganhos",183],
             		["Perdas",1500]
				]);
				var grafico = new google.visualization.PieChart(document.getElementById('balancaSlotmachine'));
				grafico.draw(tabela,options);
			}
		    google.charts.setOnLoadCallback(balancaSlotmachine);
	    </script>
    </head>
	<body id="admin">
		<%@include file="header.jsp"  %>
		
		<div class="container">		
			<div class="p-0 pt-5 col-12">
				<h1 class="text-center">Jogadores que jogaram mais partidas</h1>
			    <div id="bestPlayers" class="graph"></div>
			</div>
			
			<div class="p-0 pt-5 col-12">
				<h1 class="text-center">Jogo mais jogado</h1>
			    <div id="bestGame" class="graph"></div>
			</div>
			
			<div class="p-0 pt-5 col-12">
				<h1 class="text-center">Balança Blackjack</h1>
			    <div id="balancaBlackjack" class="graph"></div>
			</div>
			
			<div class="p-0 pt-5 col-12">
				<h1 class="text-center">Balança Slot Machine</h1>
			    <div id="balancaSlotmachine" class="graph"></div>
			</div>
		</div>
		<div class="container p-0 pt-5">
             <p>
                <a class="btn btn-lg btn-primary" href="${pageContext.servletContext.contextPath}/user">
                    Lista de usuários
                </a>
             </p>
         </div>
	</body>
</html>