package model;

/**
 *
 * @author wellwlds
 */

public class Log {

	private Integer id;
    private Integer user_id;
    private Integer game_id;
    private Boolean house_gain;
    private Integer money;

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }
    
    /**
     * @return the user_id
     */
    public Integer getUserId() {
        return user_id;
    }

    /**
     * @param user_id the user_id to set
     */
    public void setUserId(Integer user_id) {
        this.user_id = user_id;
    }
    
    /**
     * @return the game_id
     */
    public Integer getGameId() {
        return game_id;
    }

    /**
     * @param game_id the game_id to set
     */
    public void setGameId(Integer game_id) {
        this.game_id = game_id;
    }
    
    /**
     * @return the house_gain
     */
    public Boolean getHouseGain() {
        return house_gain;
    }

    /**
     * @param house_gain the house_gain to set
     */
    public void setHouseGain(Boolean house_gain) {
        this.house_gain = house_gain;
    }
    
    /**
     * @return the money
     */
    public Integer getMoney() {
        return money;
    }

    /**
     * @param money the money to set
     */
    public void setMoney(Integer money) {
        this.money = money;
    }
}
