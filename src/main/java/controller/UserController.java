/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.DAO;
import dao.DAOFactory;
import dao.UserDAO;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.User;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author wellwlds
 */
@WebServlet(
        name = "UserController",
        urlPatterns = {
            "/user",
            "/user/create",
            "/user/update",
            "/user/delete",
            "/user/read",
            "/user/checkLogin",
            "/user/carteira",
            "/user/game/blackjack",
            "/user/game/slotmachine",
        })
public class UserController extends HttpServlet {

    private static int MAX_FILE_SIZE = 1024 * 1024 * 4;

    /**
     * Pasta para salvar os arquivos que foram 'upados'. Os arquivos vão ser
     * salvos na pasta de build do servidor. Ao limpar o projeto (clean),
     * pode-se perder estes arquivos. Façam backup antes de limpar.
     */
    private static String SAVE_DIR = "img";

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        DAO<User> dao;
        User user;
        RequestDispatcher dispatcher;
        HttpSession session = request.getSession();
        switch (request.getServletPath()) {
            case "/user": {
                try ( DAOFactory daoFactory = DAOFactory.getInstance()) {
                    dao = daoFactory.getUserDAO();

                    List<User> userList = dao.all();
                    request.setAttribute("userList", userList);
                } catch (ClassNotFoundException | IOException | SQLException ex) {
                    request.getSession().setAttribute("error", ex.getMessage());
                }

                dispatcher = request.getRequestDispatcher("/view/user/index.jsp");
                dispatcher.forward(request, response);
                break;
            }

            case "/user/create": {
                dispatcher = request.getRequestDispatcher("/view/user/create.jsp");
                dispatcher.forward(request, response);
                break;
            }
            
            case "/user/update": {
                try ( DAOFactory daoFactory = DAOFactory.getInstance()) {
                    dao = daoFactory.getUserDAO();

                    user = dao.read(Integer.parseInt(request.getParameter("id")));
                    request.setAttribute("user", user);

                    dispatcher = request.getRequestDispatcher("/view/user/update.jsp");
                    dispatcher.forward(request, response);
                } catch (ClassNotFoundException | IOException | SQLException ex) {
                    request.getSession().setAttribute("error", ex.getMessage());
                    response.sendRedirect(request.getContextPath() + "/user");
                }
                break;
            }
            
            case "/user/delete": {
                try ( DAOFactory daoFactory = DAOFactory.getInstance()) {
                    dao = daoFactory.getUserDAO();
                    dao.delete(Integer.parseInt(request.getParameter("id")));
                } catch (ClassNotFoundException | IOException | SQLException ex) {
                    request.getSession().setAttribute("error", ex.getMessage());
                }

                response.sendRedirect(request.getContextPath() + "/user");
                break;
            }
            
            case "/user/read": {
                try ( DAOFactory daoFactory = DAOFactory.getInstance()) {
                    dao = daoFactory.getUserDAO();

                    user = dao.read(Integer.parseInt(request.getParameter("id")));

                    Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
                    String json = gson.toJson(user);

                    response.getOutputStream().print(json);
                } catch (ClassNotFoundException | IOException | SQLException ex) {
                    request.getSession().setAttribute("error", ex.getMessage());
                    response.sendRedirect(request.getContextPath() + "/user");
                }
                break;
            }

            case "/user/carteira": {
            	try ( DAOFactory daoFactory = DAOFactory.getInstance()) {
                    dao = daoFactory.getUserDAO();
                    
                    user = dao.read(Integer.parseInt(request.getParameter("id")));
                    request.setAttribute("user", user);

                    dispatcher = request.getRequestDispatcher("/carteira.jsp");
                    dispatcher.forward(request, response);
                    break;
                } catch (ClassNotFoundException | IOException | SQLException ex) {
                    request.getSession().setAttribute("error", ex.getMessage());
                    response.sendRedirect(request.getContextPath() + "/user/carteira");
                }
            }
            
            case "/user/game/blackjack": {
            	try ( DAOFactory daoFactory = DAOFactory.getInstance()) {
                    dao = daoFactory.getUserDAO();
                    
                    dispatcher = request.getRequestDispatcher("/blackjack.jsp");
                    dispatcher.forward(request, response);
                    break;
                } catch (ClassNotFoundException | IOException | SQLException ex) {
                    request.getSession().setAttribute("error", ex.getMessage());
                    response.sendRedirect(request.getContextPath() + "/user/game/blackjack");
                }
            }

            case "/user/game/slotmachine": {
            	try ( DAOFactory daoFactory = DAOFactory.getInstance()) {
                    dao = daoFactory.getUserDAO();
                    
                    dispatcher = request.getRequestDispatcher("/slotmachine.jsp");
                    dispatcher.forward(request, response);
                    break;
                } catch (ClassNotFoundException | IOException | SQLException ex) {
                    request.getSession().setAttribute("error", ex.getMessage());
                    response.sendRedirect(request.getContextPath() + "/user/game/slotmachine");
                }
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
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        DAO<User> dao;
        User user = new User();
        HttpSession session = request.getSession();

        String servletPath = request.getServletPath();

        switch (request.getServletPath()) {
            case "/user/create":
            case "/user/update": {
                DiskFileItemFactory factory = new DiskFileItemFactory();
                // Set factory constraints
                factory.setSizeThreshold(MAX_FILE_SIZE);
                // Set the directory used to temporarily store files that are larger than the configured size threshold
                factory.setRepository(new File("/tmp"));
                // Create a new file upload handler
                ServletFileUpload upload = new ServletFileUpload(factory);
                // Set overall request size constraint
                upload.setSizeMax(MAX_FILE_SIZE);

                try ( DAOFactory daoFactory = DAOFactory.getInstance()) {
                    // Parse the request
                    List<FileItem> items = upload.parseRequest(request);

                    // Process the uploaded items
                    Iterator<FileItem> iter = items.iterator();
                    while (iter.hasNext()) {
                        FileItem item = iter.next();

                        // Process a regular form field
                        if (item.isFormField()) {
                            String fieldName = item.getFieldName();
                            String fieldValue = item.getString();

                            switch (fieldName) {
                                case "login":
                                    user.setLogin(fieldValue);
                                    break;
                                case "senha":
                                    user.setSenha(fieldValue);
                                    break;
                                case "nome":
                                    user.setNome(fieldValue);
                                    break;
                                case "nascimento":
                                    java.util.Date dataNascimento = new SimpleDateFormat("dd/MM/yyyy").parse(fieldValue);
                                    user.setNascimento(new Date(dataNascimento.getTime()));
                                    break;
                                case "carteira":
                                    user.setCarteira(Integer.parseInt(fieldValue));
                                    break;
                                case "id":
                                    user.setId(Integer.parseInt(fieldValue));
                            }
                        } else {
                            String fieldName = item.getFieldName();
                            String fileName = item.getName();
                            if (fieldName.equals("avatar") && !fileName.isBlank()) {
                                // Dados adicionais (não usado nesta aplicação)
                                String contentType = item.getContentType();
                                boolean isInMemory = item.isInMemory();
                                long sizeInBytes = item.getSize();

                                // Pega o caminho absoluto da aplicação
                                String appPath = request.getServletContext().getRealPath("");
                                // Grava novo arquivo na pasta img no caminho absoluto
                                String savePath = appPath + File.separator + SAVE_DIR + File.separator + fileName;
                                File uploadedFile = new File(savePath);
                                item.write(uploadedFile);

                                user.setAvatar(fileName);
                            }
                        }
                    }

                    dao = daoFactory.getUserDAO();

                    if (servletPath.equals("/user/create")) {
                        dao.create(user);
                    } else {
                        servletPath += "?id=" + String.valueOf(user.getId());
                        dao.update(user);
                    }

                    response.sendRedirect(request.getContextPath() + "/user");

                } catch (ParseException ex) {
                    Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, "Controller", ex);
                    session.setAttribute("error", "O formato de data não é válido. Por favor entre data no formato dd/mm/aaaa");
                    response.sendRedirect(request.getContextPath() + servletPath);
                } catch (FileUploadException ex) {
                    Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, "Controller", ex);
                    session.setAttribute("error", "Erro ao fazer upload do arquivo.");
                    response.sendRedirect(request.getContextPath() + servletPath);
                } catch (ClassNotFoundException | IOException | SQLException ex) {
                    Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, "Controller", ex);
                    session.setAttribute("error", ex.getMessage());
                    response.sendRedirect(request.getContextPath() + servletPath);
                } catch (Exception ex) {
                    Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, "Controller", ex);
                    session.setAttribute("error", "Erro ao gravar arquivo no servidor.");
                    response.sendRedirect(request.getContextPath() + servletPath);
                }
                break;
            }
            
            case "/user/delete": {
                String[] users = request.getParameterValues("delete");

                try ( DAOFactory daoFactory = DAOFactory.getInstance()) {
                    dao = daoFactory.getUserDAO();

                    try {
                        daoFactory.beginTransaction();

                        for (String userId : users) {
                            dao.delete(Integer.parseInt(userId));
                        }

                        daoFactory.commitTransaction();
                        daoFactory.endTransaction();
                    } catch (SQLException ex) {
                        session.setAttribute("error", ex.getMessage());
                        daoFactory.rollbackTransaction();
                    }
                } catch (ClassNotFoundException | IOException ex) {
                    Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, "Controller", ex);
                    session.setAttribute("error", ex.getMessage());
                } catch (SQLException ex) {
                    Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, "Controller", ex);
                    session.setAttribute("rollbackError", ex.getMessage());
                }

                response.sendRedirect(request.getContextPath() + "/user");
                break;
            }

            case "/user/checkLogin": {
                try ( DAOFactory daoFactory = DAOFactory.getInstance()) {
                    UserDAO udao = daoFactory.getUserDAO();

                    user = udao.getByLogin(request.getParameter("login"));

                    Gson gson = new Gson();
                    Map<String, String> result = new HashMap<>();
                    if (user != null) {
                        result.put("status", "USADO");
                    } else {
                        result.put("status", "NAO_USADO");
                    }

                    String json = gson.toJson(result);
                    response.setContentType("application/json");
                    response.getOutputStream().print(json);

                } catch (ClassNotFoundException | IOException | SQLException ex) {
                    request.getSession().setAttribute("error", ex.getMessage());
                    response.sendRedirect(request.getContextPath() + "/user");
                }

                break;
            }

            case "/user/carteira": {
            	try ( DAOFactory daoFactory = DAOFactory.getInstance()) {
            	// Read from request
                StringBuilder buffer = new StringBuilder();
                BufferedReader reader = request.getReader();
                String line;
                Integer i = 0,moedas = 0,idUser = 0;
                while ((line = reader.readLine()) != null) {
                	i = i + 1;
                	if (i==8) {
                		moedas = Integer.parseInt(line);
                	}
                	if (i==4) {
                		idUser =  Integer.parseInt(line);
                	}
                }
                
            	dao = daoFactory.getUserDAO();
            	user.setCarteira(moedas);
                user.setId(idUser);
            	dao.updateCarteira(user);
                session.setAttribute("carteira", moedas);

            	} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	response.sendRedirect(request.getContextPath() + "/welcome.jsp");
            	break;
            }
            
            case "/user/game/blackjack": {
            	// Read from request
        	   try ( DAOFactory daoFactory = DAOFactory.getInstance()) {
                StringBuilder buffer = new StringBuilder();
                BufferedReader reader = request.getReader();
                String line;
                dao = daoFactory.getUserDAO();
                
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
                user = dao.read(idUser);
                user.setId(idUser);
                if (resultado > 15 && resultado <= 21) {
                	dao.gameLog(idUser, 1, 0, aposta);
                    carteira = user.getCarteira() + aposta;
                	user.setCarteira(carteira);
                	dao.updateJogo(user);
                	
            	} else {
            		dao.gameLog(idUser, 1, 1, aposta);
            		carteira = user.getCarteira() - aposta;
            		user.setCarteira(carteira);
            		dao.updateJogo(user);
            		
            	}
                session.setAttribute("carteira", user.getCarteira());
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
                dao = daoFactory.getUserDAO();
                Integer i = 0,resultado = 0,idUser = 0, carteira = 0;
                String resultado1 = null,resultado2 = null,resultado3 = null;
                /*while ((line = reader.readLine()) != null) {
                    i = i + 1;
                    if (i==4) {
                        idUser = Integer.parseInt(line);
                	}
                	if (i==8) {
                        resultado = Integer.parseInt(line);
                	}
                }*/
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
        	    user = dao.read(idUser);
                user.setId(idUser);
                if (resultado1.equals(resultado2) && resultado1.equals(resultado3)) {
                	dao.gameLog(idUser, 2, 0, 10);
                    carteira = user.getCarteira() + 10;
                	user.setCarteira(carteira);
                	dao.updateJogo(user);
                	
            	} else {
            		dao.gameLog(idUser, 2, 1, 5);
            		carteira = user.getCarteira() - 5;
            		user.setCarteira(carteira);
            		dao.updateJogo(user);
            		
            	}
                session.setAttribute("carteira", user.getCarteira());
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

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
