package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Games;

public class PgGamesDAO {
	
private Connection connection;
	
	private static final String CREATE_QUERY =
            "INSERT INTO wellson.games(nome, multiplicador) " +
            "VALUES(?, ?);";

private static final String READ_QUERY =
            "SELECT nome, multiplicador " +
            "FROM wellson.games " +
            "WHERE id = ?;";

private static final String ALL_QUERY =
"SELECT id " +
"FROM wellson.games " +
"ORDER BY id;";


public PgGamesDAO(Connection connection) {
    this.connection = connection;
}


public void create(Games games) throws SQLException {
    try (PreparedStatement statement = connection.prepareStatement(CREATE_QUERY)) {
        statement.setString(1, games.getNome());
        statement.setInt(2, games.getMultiplicador());

        statement.executeUpdate();
    } catch (SQLException ex) {
        Logger.getLogger(PgUserDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

        if (ex.getMessage().contains("not-null")) {
            throw new SQLException("Erro ao inserir game: pelo menos um campo está em branco.");
        } else {
            throw new SQLException("Erro ao inserir game.");
        }
    }
}


public Games read(Integer id) throws SQLException {
    Games games = new Games();

    try (PreparedStatement statement = connection.prepareStatement(READ_QUERY)) {
        statement.setInt(1, id);
        try (ResultSet result = statement.executeQuery()) {
            if (result.next()) {
                games.setId(id);
                games.setNome(result.getString("nome"));
                games.setMultiplicador(result.getInt("multiplicador"));
            } else {
                throw new SQLException("Erro ao visualizar: games não encontrado.");
            }
        }
    } catch (SQLException ex) {
        Logger.getLogger(PgGamesDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
        
        if (ex.getMessage().equals("Erro ao visualizar: games não encontrado.")) {
            throw ex;
        } else {
            throw new SQLException("Erro ao visualizar games.");
        }
    }

    return games;
}

public List<Games> all() throws SQLException {
    List<Games> gamesList = new ArrayList<>();

    try (PreparedStatement statement = connection.prepareStatement(ALL_QUERY);
         ResultSet result = statement.executeQuery()) {
        while (result.next()) {
            Games games = new Games();
            games.setId(result.getInt("id"));

            gamesList.add(games);
        }
    } catch (SQLException ex) {
        Logger.getLogger(PgUserDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

        throw new SQLException("Erro ao listar games.");
    }

    return gamesList;
}

}
