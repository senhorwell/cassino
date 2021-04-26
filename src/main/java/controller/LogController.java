package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.DAOFactory;
import dao.PgLogDAO;
import model.Log;

public class LogController {
	
	// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PgLogDAO dao;
        Log log;
        RequestDispatcher dispatcher;

        switch (request.getServletPath()) {
            case "/user/game/blackjack": {
                try ( DAOFactory daoFactory = DAOFactory.getInstance()) {
                    dao = daoFactory.getLogDAO();

                    List<Log> logList = dao.all();
                    request.setAttribute("logList", logList);
                } catch (ClassNotFoundException | IOException | SQLException ex) {
                    request.getSession().setAttribute("error", ex.getMessage());
                }

                dispatcher = request.getRequestDispatcher("/user/game/blackjack");
                dispatcher.forward(request, response);
                break;
            }

            case "/user/game/slotmachine": {
                try ( DAOFactory daoFactory = DAOFactory.getInstance()) {
                    dao = daoFactory.getLogDAO();

                    List<Log> logList = dao.all();
                    request.setAttribute("logList", logList);
                } catch (ClassNotFoundException | IOException | SQLException ex) {
                    request.getSession().setAttribute("error", ex.getMessage());
                }

                dispatcher = request.getRequestDispatcher("/user/game/slotmachine");
                dispatcher.forward(request, response);
                break;
            }
            
        }
    }
    
    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PgLogDAO Logdao;
        Log log = new Log();
        HttpSession session = request.getSession();

        String servletPath = request.getServletPath();

        switch (request.getServletPath()) {
            case "/user/game/blackjack": {
            	// Read from request
         	   try ( DAOFactory daoFactory = DAOFactory.getInstance()) {
                 StringBuilder buffer = new StringBuilder();
                 BufferedReader reader = request.getReader();
                 String line;
                 Logdao = daoFactory.getLogDAO();
                 Integer i = 0,aposta = 0,resultado = 0,idUser = 0, carteira = 0;
                 while ((line = reader.readLine()) != null) {
                     i = i + 1;
                     if (i==4) {
                         idUser = Integer.parseInt(line);
                 	}
                 	if (i==8) {
                 		aposta = Integer.parseInt(line);
                 	}
                 	if (i==12) {
                         resultado = Integer.parseInt(line);
                 	}
                 }
                 log.setUserId(idUser);
                 log.setGameId(1);
                 if (resultado > 15 && resultado <= 21) {

                     log.setHouseGain(true);
                     log.setMoney(aposta);
                 	
             	} else {

                    log.setHouseGain(false);
                    log.setMoney(aposta);
             		
             	}
                 Logdao.create(log);
         	   } catch (ClassNotFoundException e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			} catch (SQLException e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			}

                     response.sendRedirect(request.getContextPath() + "/user/game/blackjack");
                     break;
            }
            
            case "/user/game/slotmachine": {
            	// Read from request
         	   try ( DAOFactory daoFactory = DAOFactory.getInstance()) {
                 StringBuilder buffer = new StringBuilder();
                 BufferedReader reader = request.getReader();
                 String line;
                 Logdao = daoFactory.getLogDAO();
                 Integer i = 0,resultado = 0,idUser = 0, carteira = 0;
                 String resultado1 = null,resultado2 = null,resultado3 = null;
                 StringTokenizer t = new StringTokenizer(reader.readLine(), "&");
                 
         	    while (t.hasMoreTokens()){
         		  String ret = t.nextToken();
         		  if (i == 0)
         		  {
         			  StringTokenizer t2 = new StringTokenizer(ret, "=");
         			  t2.nextToken();
         			  idUser = Integer.parseInt(t2.nextToken());
         			  i++;
         		  }
         		  else if (i == 1)
         		  {
         			  StringTokenizer t2 = new StringTokenizer(ret, "=");
         			  t2.nextToken();
         			  resultado1 = t2.nextToken();
         			  i++;
         		  }
         		  else if (i == 2)
         		  {
         			  StringTokenizer t2 = new StringTokenizer(ret, "=");
         			  t2.nextToken();
         			  resultado2 = t2.nextToken();
         			  i++;
         		  }
         		  else if (i == 3)
         		  {
         			  StringTokenizer t2 = new StringTokenizer(ret, "=");
         			  t2.nextToken();
         			  resultado3 = t2.nextToken();
         		  }
         	    }
                 log.setUserId(idUser);
                 log.setGameId(2);
                 if (resultado1.equals(resultado2) && resultado1.equals(resultado3)) {
                	 log.setHouseGain(true);
                     log.setMoney(log.getMoney()+10);
                 	
             	} else {
             		log.setHouseGain(false);
                    log.setMoney(log.getMoney()-5);
             		
             	}
                 Logdao.create(log);
         	   } catch (ClassNotFoundException e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			} catch (SQLException e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			}

                     response.sendRedirect(request.getContextPath() + "/user/game/slotmachine");
                     break;
            }
        }
    }

}
