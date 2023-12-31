/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;

/**
 *
 * @author wellwlds
 */
public class PgUserDAO implements UserDAO {

    private final Connection connection;

    private static final String CREATE_QUERY =
                                "INSERT INTO wellson.user(login, senha, nome, nascimento, avatar) " +
                                "VALUES(?, md5(?), ?, ?, ?);";
    private static final String GAME_LOG =
                                "INSERT INTO wellson.log(user_id, game_id, house_gain, money) " +
                                "VALUES(?, ?, ?, ?);";
    private static final String READ_QUERY =
                                "SELECT login, nome, nascimento, avatar, carteira, person_type " +
                                "FROM wellson.user " +
                                "WHERE id = ?;";

    private static final String UPDATE_QUERY =
                                "UPDATE wellson.user " +
                                "SET login = ?, nome = ?, nascimento = ? " +
                                "WHERE id = ?;";

    private static final String UPDATE_WITH_PASSWORD_QUERY =
                                "UPDATE wellson.user " +
                                "SET login = ?, nome = ?, nascimento = ?, senha = md5(?) " +
                                "WHERE id = ?;";

    private static final String UPDATE_WITH_AVATAR_QUERY =
                                "UPDATE wellson.user " +
                                "SET login = ?, nome = ?, nascimento = ?, avatar = ? " +
                                "WHERE id = ?;";

    private static final String UPDATE_WITH_AVATAR_AND_PASSWORD_QUERY =
                                "UPDATE wellson.user " +
                                "SET login = ?, nome = ?, nascimento = ?, avatar = ?, senha = md5(?) " +
                                "WHERE id = ?;";

    private static final String UPDATE_CARTEIRA_QUERY = "UPDATE wellson.user SET carteira = ? WHERE id = ?;";
    
    private static final String UPDATE_QUERY_GAME = "UPDATE wellson.user SET carteira = ? WHERE id = ?;";
    
    private static final String DELETE_QUERY =
                                "DELETE FROM wellson.user " +
                                "WHERE id = ?;";

    private static final String ALL_QUERY =
                                "SELECT id, login " +
                                "FROM wellson.user " +
                                "ORDER BY id desc;";

    private static final String AUTHENTICATE_QUERY =
                                "SELECT id, nome, nascimento, avatar,carteira, person_type " +
                                "FROM wellson.user " +
                                "WHERE login = ? AND senha = md5(?);";
    
    private static final String GET_BY_LOGIN_QUERY =
                                "SELECT id, login, nome, nascimento, avatar,carteira, person_type " +
                                "FROM wellson.user " +
                                "WHERE login = ?;";
//    private static final String GET_PERDAS =
//					            "SELECT wellson.totalPerdas(?);";
//    private static final String GET_GANHOS =
//            					"SELECT wellson.totalGanhos(?);";
    private static final String GET_PERDAS =
					            "SELECT count(*) as total FROM wellson.log WHERE house_gain = 1 AND user_id = ?;";
	private static final String GET_GANHOS =
								"SELECT count(*) as total FROM wellson.log WHERE house_gain = 0 AND user_id = ?;";
	private static final String GET_BEST_PLAYERS =
								"SELECT wellson.user.nome, count(*) as total " + 
								"FROM wellson.log " + 
								"INNER JOIN wellson.user " + 
								"ON wellson.user.id = log.user_id AND house_gain = 0 " + 
								"GROUP BY wellson.user.nome ORDER BY total desc;";
	private static final String GET_MOST_PLAYERS =
								"SELECT wellson.user.nome, count(*) as total " + 
								"FROM wellson.log " + 
								"INNER JOIN wellson.user " + 
								"ON wellson.user.id = log.user_id " + 
								"GROUP BY wellson.user.nome ORDER BY total desc;";
    private static final String GET_VITORIAS =
                                "SELECT wellson.user.nome as nome, sum(wellson.log.money) as total " +
                                "FROM wellson.log " +
                                "INNER JOIN wellson.user " +
                                "ON wellson.user.id = log.user_id AND house_gain = 0 " +
                                "GROUP BY wellson.user.nome ORDER BY total desc;";
    private static final String GET_NUM_GAMES = 
    							"SELECT count(*) as total FROM wellson.log WHERE game_id = ?;";
    private static final String GET_BALANCE_GAME = 
    							"SELECT sum(wellson.log.money) as total FROM wellson.log WHERE house_gain = ? AND game_id = ?;";
    private static final String GET_MULTIPLY = 
    							"SELECT multiplicador FROM wellson.games WHERE id = ?;";
    private static final String GET_CARTEIRA = 
								"SELECT carteira FROM wellson.user WHERE id = ?;";
    public PgUserDAO(Connection connection) {
        this.connection = connection;
    }
                    
    @Override
    public void create(User user) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(CREATE_QUERY)) {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getSenha());
            statement.setString(3, user.getNome());
            statement.setDate(4, user.getNascimento());
            statement.setString(5, user.getAvatar());

            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PgUserDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if (ex.getMessage().contains("uq_user_login")) {
                throw new SQLException("Erro ao inserir usuário: login já existente.");
            } else if (ex.getMessage().contains("not-null")) {
                throw new SQLException("Erro ao inserir usuário: pelo menos um campo está em branco.");
            } else {
                throw new SQLException("Erro ao inserir usuário.");
            }
        }
    }
    @Override
    public void gameLog(Integer userId, Integer gameId, Integer who, Integer money) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(GAME_LOG)) {
            statement.setInt(1, userId);
            statement.setInt(2, gameId);
            statement.setInt(3, who);
            statement.setInt(4, money);

            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PgUserDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if (ex.getMessage().contains("uq_user_login")) {
                throw new SQLException("Erro ao inserir usuário: login já existente.");
            } else if (ex.getMessage().contains("not-null")) {
                throw new SQLException("Erro ao inserir usuário: pelo menos um campo está em branco.");
            } else {
                throw new SQLException("Erro ao inserir o dado.");
            }
        }
    }
    @Override
    public User read(Integer id) throws SQLException {
        User user = new User();

        try (PreparedStatement statement = connection.prepareStatement(READ_QUERY)) {
            statement.setInt(1, id);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    user.setId(id);
                    user.setLogin(result.getString("login"));
                    user.setNome(result.getString("nome"));
                    user.setNascimento(result.getDate("nascimento"));
                    user.setAvatar(result.getString("avatar"));
                    user.setCarteira(result.getInt("carteira"));
                    user.setPersonType(result.getInt("person_type"));
                } else {
                    throw new SQLException("Erro ao visualizar: usuário não encontrado.");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgUserDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
            
            if (ex.getMessage().equals("Erro ao visualizar: usuário não encontrado.")) {
                throw ex;
            } else {
                throw new SQLException("Erro ao visualizar usuário.");
            }
        }

        return user;
    }

    @Override
    public void update(User user) throws SQLException {
        String query;

        if ((user.getSenha() == null) || (user.getSenha().isBlank())) {
            if ((user.getAvatar() == null) || (user.getAvatar().isBlank()))
                query = UPDATE_QUERY;
            else
                query = UPDATE_WITH_AVATAR_QUERY;
        } else {
            if ((user.getAvatar() == null) || (user.getAvatar().isBlank()))
                query = UPDATE_WITH_PASSWORD_QUERY;
            else
                query = UPDATE_WITH_AVATAR_AND_PASSWORD_QUERY;
        }

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getNome());
            statement.setDate(3, user.getNascimento());

            if ((user.getSenha() == null) || (user.getSenha().isBlank())) {
                if ((user.getAvatar() == null) || (user.getAvatar().isBlank())) {
                    statement.setInt(4, user.getId());
                } else {
                    statement.setString(4, user.getAvatar());
                    statement.setInt(5, user.getId());
                }
            } else {
                if ((user.getAvatar() == null) || (user.getAvatar().isBlank())) {
                    statement.setString(4, user.getSenha());
                    statement.setInt(5, user.getId());
                } else {
                    statement.setString(4, user.getAvatar());
                    statement.setString(5, user.getSenha());
                    statement.setInt(6, user.getId());
                }
            }

            if (statement.executeUpdate() < 1) {
                throw new SQLException("Erro ao editar: usuário não encontrado.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgUserDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if (ex.getMessage().equals("Erro ao editar: usuário não encontrado.")) {
                throw ex;
            } else if (ex.getMessage().contains("uq_user_login")) {
                throw new SQLException("Erro ao editar usuário: login já existente.");
            } else if (ex.getMessage().contains("not-null")) {
                throw new SQLException("Erro ao editar usuário: pelo menos um campo está em branco.");
            } else {
                throw new SQLException("Erro ao editar usuário.");
            }
        }
    }

    @Override
    public void updateCarteira(User user) throws SQLException {
		try (PreparedStatement statement = connection.prepareStatement(UPDATE_CARTEIRA_QUERY)) {
			statement.setInt(1, user.getCarteira());
			statement.setInt(2, user.getId());
			if (statement.executeUpdate() < 1){
                throw new SQLException("Erro ao editar: usuario não encontrada.");
            }
		} catch (NullPointerException e) {
			System.out.println("ID da carteira: " + user.getId());
		}
	}
    
    @Override
	public void updateJogo(User user) throws SQLException {
		try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY_GAME)) {
			statement.setInt(1, user.getCarteira());
			statement.setInt(2, user.getId());		
			if (statement.executeUpdate() < 1) { throw new
			SQLException("Erro ao editar: usuário não encontrado."); }
		} catch (SQLException ex) {
			Logger.getLogger(PgUserDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
		}

	}

    @Override
    public void delete(Integer id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setInt(1, id);

            if (statement.executeUpdate() < 1) {
                throw new SQLException("Erro ao excluir: usuário não encontrado.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgUserDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if (ex.getMessage().equals("Erro ao excluir: usuário não encontrado.")) {
                throw ex;
            } else {
                throw new SQLException("Erro ao excluir usuário.");
            }
        }
    }

    @Override
    public List<User> all() throws SQLException {
        List<User> userList = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(ALL_QUERY);
             ResultSet result = statement.executeQuery()) {
            while (result.next()) {
                User user = new User();
                user.setId(result.getInt("id"));
                user.setLogin(result.getString("login"));

                userList.add(user);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgUserDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            throw new SQLException("Erro ao listar usuários.");
        }

        return userList;
    }
    @Override
    public List<User> allPlayers() throws SQLException {
        List<User> userList = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(GET_BEST_PLAYERS);
             ResultSet result = statement.executeQuery()) {
            while (result.next()) {
                User user = new User();
                user.setNome(result.getString("nome"));
                user.setVitorias(result.getInt("total"));

                userList.add(user);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgUserDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            throw new SQLException("Erro ao listar usuários.");
        }

        return userList;
    }
    @Override
    public List<User> allPlayersPlay() throws SQLException {
        List<User> userList = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(GET_MOST_PLAYERS);
             ResultSet result = statement.executeQuery()) {
            while (result.next()) {
                User user = new User();
                user.setNome(result.getString("nome"));
                user.setVitorias(result.getInt("total"));

                userList.add(user);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgUserDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            throw new SQLException("Erro ao listar usuários.");
        }

        return userList;
    }
    @Override
    public List<User> getVitoriasList() throws SQLException {
        List<User> userList = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(GET_VITORIAS);
             ResultSet result = statement.executeQuery()) {
            while (result.next()) {
                User user = new User();
                user.setNome(result.getString("nome"));
                user.setVitorias(result.getInt("total"));

                userList.add(user);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgUserDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            throw new SQLException("Erro ao listar usuários.");
        }

        return userList;
    }
    @Override
    public void authenticate(User user) throws SQLException, SecurityException {
        try (PreparedStatement statement = connection.prepareStatement(AUTHENTICATE_QUERY)) {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getSenha());

            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    user.setId(result.getInt("id"));
                    user.setNome(result.getString("nome"));
                    user.setNascimento(result.getDate("nascimento"));
                    user.setAvatar(result.getString("avatar"));
                    user.setCarteira(result.getInt("carteira"));
                    user.setPersonType(result.getInt("person_type"));
                } else {
                    throw new SecurityException("Login ou senha incorretos.");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgUserDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            throw new SQLException("Erro ao autenticar usuário.");
        }
    }
    
    @Override
    public Integer getGanhos(Integer userId) throws SQLException{
        try (PreparedStatement statement = connection.prepareStatement(GET_GANHOS)) {
            statement.setInt(1, userId);

            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return result.getInt("total");
                } else {
                    return null;
                }
            }
        } catch (SQLException ex) {            
            throw new SQLException("Erro ao obter ganhos");
        }
    }
    @Override
    public Integer getNumGames(Integer gameId) throws SQLException{
        try (PreparedStatement statement = connection.prepareStatement(GET_NUM_GAMES)) {
            statement.setInt(1, gameId);

            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return result.getInt("total");
                } else {
                    return null;
                }
            }
        } catch (SQLException ex) {            
            throw new SQLException("Erro ao obter numero");
        }
    }
    @Override
    public Integer getBalanceGame(Integer who,Integer gameId) throws SQLException{
        try (PreparedStatement statement = connection.prepareStatement(GET_BALANCE_GAME)) {
            statement.setInt(1, who);
            statement.setInt(2, gameId);

            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return result.getInt("total");
                } else {
                    return null;
                }
            }
        } catch (SQLException ex) {            
            throw new SQLException("Erro ao obter numero");
        }
    }
    @Override
    public Integer getMultiply(Integer gameId) throws SQLException{
        try (PreparedStatement statement = connection.prepareStatement(GET_MULTIPLY)) {
            statement.setInt(1, gameId);

            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return result.getInt("multiplicador");
                } else {
                    return null;
                }
            }
        } catch (SQLException ex) {            
            throw new SQLException("Erro ao obter numero");
        }
    }
    @Override
    public Integer getCarteira(Integer userId) throws SQLException{
        try (PreparedStatement statement = connection.prepareStatement(GET_CARTEIRA)) {
            statement.setInt(1, userId);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return result.getInt("carteira");
                } else {
                    return null;
                }
            }
        } catch (SQLException ex) {            
            throw new SQLException("Erro ao obter numero");
        }
    }
    @Override
    public Integer getPerdas(Integer userId) throws SQLException{
        try (PreparedStatement statement = connection.prepareStatement(GET_PERDAS)) {
            statement.setInt(1, userId);

            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return result.getInt("total");
                } else {
                    return null;
                }
            }
        } catch (SQLException ex) {            
            throw new SQLException("Erro ao obter ganhos");
        }
    }
    @Override
    public User getByLogin(String login) throws SQLException {

        try (PreparedStatement statement = connection.prepareStatement(GET_BY_LOGIN_QUERY)) {
            statement.setString(1, login);

            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    User user = new User();
                    user.setId(result.getInt("id"));
                    user.setNome(result.getString("nome"));
                    user.setNascimento(result.getDate("nascimento"));
                    user.setAvatar(result.getString("avatar"));
                    user.setLogin(login);
                    user.setCarteira(result.getInt("carteira"));
                    user.setPersonType(result.getInt("person_type"));
                    return user;

                } else {

                    return null;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgUserDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
            
            throw new SQLException("Erro ao obter usuário.");
        }
    }

  
}
