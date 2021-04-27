/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.SQLException;
import java.util.List;

import model.User;

/**
 *
 * @author wellwlds
 */
public interface DAO<T> {
    public void create(T t) throws SQLException;
    public T read(Integer id) throws SQLException;
    public void update(T t) throws SQLException;
    public void updateCarteira(User user) throws SQLException;
	void updateJogo(User user) throws SQLException;
    public void delete(Integer id) throws SQLException;

    public List<T> all() throws SQLException;
	public List<T> allPlayers() throws SQLException;
	
	public Integer getGanhos(Integer userId) throws SQLException;
	public Integer getPerdas(Integer userId) throws SQLException;
	List<User> getVitoriasList() throws SQLException;
	Integer getNumGames(Integer gameId) throws SQLException;
	Integer getBalanceGame(Integer who, Integer gameId) throws SQLException;
	void gameLog(Integer userId, Integer gameId, Integer who, Integer money) throws SQLException;
	List<User> allPlayersPlay() throws SQLException;
}
