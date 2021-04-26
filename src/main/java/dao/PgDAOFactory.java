/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;

/**
 *
 * @author wellwlds
 */
public class PgDAOFactory extends DAOFactory {

    public PgDAOFactory(Connection connection) {
        this.connection = connection;
    }

    @Override
    public UserDAO getUserDAO() {
        return new PgUserDAO(this.connection);
    }

	@Override
	public PgLogDAO getLogDAO() {
		return new PgLogDAO(this.connection);
	}

	@Override
	public PgGamesDAO getGamesDAO() {
		return new PgGamesDAO(this.connection);
	}

}
