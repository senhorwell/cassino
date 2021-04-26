package model;


public class Games {

	private Integer id;
    private String nome;
    private Integer multiplicador;

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
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    /**
     * @return the multiplicador
     */
    public Integer getMultiplicador() {
        return multiplicador;
    }

    /**
     * @param multiplicador the multiplicador to set
     */
    public void setMultiplicador(Integer multiplicador) {
        this.multiplicador = multiplicador;
    }
    
}
