package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Log;

public class PgLogDAO {
	
	private Connection connection;
	
	private static final String CREATE_QUERY =
            "INSERT INTO wellson.log(user_id, game_id, house_gain, money) " +
            "VALUES(?, ?, ?, ?);";

private static final String READ_QUERY =
            "SELECT user_id, game_id, house_gain, money " +
            "FROM wellson.log " +
            "WHERE id = ?;";

private static final String ALL_QUERY =
"SELECT id " +
"FROM wellson.log " +
"ORDER BY id;";


public PgLogDAO(Connection connection) {
    this.connection = connection;
}


public void create(Log log) throws SQLException {
    try (PreparedStatement statement = connection.prepareStatement(CREATE_QUERY)) {
        statement.setInt(1, log.getUserId());
        statement.setInt(2, log.getGameId());
        statement.setBoolean(3, log.getHouseGain());
        statement.setInt(4, log.getMoney());

        statement.executeUpdate();
    } catch (SQLException ex) {
        Logger.getLogger(PgUserDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

        if (ex.getMessage().contains("not-null")) {
            throw new SQLException("Erro ao inserir log: pelo menos um campo está em branco.");
        } else {
            throw new SQLException("Erro ao inserir log.");
        }
    }
}


public Log read(Integer id) throws SQLException {
    Log log = new Log();

    try (PreparedStatement statement = connection.prepareStatement(READ_QUERY)) {
        statement.setInt(1, id);
        try (ResultSet result = statement.executeQuery()) {
            if (result.next()) {
                log.setId(id);
                log.setUserId(result.getInt("user_id"));
                log.setGameId(result.getInt("game_id"));
                log.setHouseGain(result.getBoolean("house_gain"));
                log.setMoney(result.getInt("money"));
            } else {
                throw new SQLException("Erro ao visualizar: log não encontrado.");
            }
        }
    } catch (SQLException ex) {
        Logger.getLogger(PgLogDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
        
        if (ex.getMessage().equals("Erro ao visualizar: log não encontrado.")) {
            throw ex;
        } else {
            throw new SQLException("Erro ao visualizar log.");
        }
    }

    return log;
}

public List<Log> all() throws SQLException {
    List<Log> logList = new ArrayList<>();

    try (PreparedStatement statement = connection.prepareStatement(ALL_QUERY);
         ResultSet result = statement.executeQuery()) {
        while (result.next()) {
            Log log = new Log();
            log.setId(result.getInt("id"));

            logList.add(log);
        }
    } catch (SQLException ex) {
        Logger.getLogger(PgUserDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

        throw new SQLException("Erro ao listar logs.");
    }

    return logList;
}

}
