/**
* Esta classe representa uma Carta, que pode ser do tipo Lacaio ou Magia.
* @see java.lang.Object
* @author Rafael Arakaki - MC302
*/
public abstract class Carta implements java.io.Serializable {
	
	private int ID; // número identificador da carta (gerado pelo Motor).
	private String nome;
	private int mana;
	
	/**
	  * Construtor da classe Carta.
	  * 
	  * @param id   Um inteiro identificador ÚNICO para esta carta
	  * @param nome   O nome da carta
	  * @param mana   O custo de mana deste lacaio
	  */
	public Carta(int id, String nome, int mana) {
		this.ID = id;
		this.nome = nome;
		this.mana = mana;
	}
	
	/**
	  * Recupera o atributo ID.
	  * 
	  * @return            o atributo
	  */
	public int getID() {
		return ID;
	}

	/**
	  * Modifica o atributo ID.
	  * 
	  * @param iD   Novo valor para o atributo.
	  */
	public void setID(int iD) {
		ID = iD;
	}

	/**
	  * Recupera o atributo nome.
	  * 
	  * @return            o atributo
	  */
	public String getNome() {
		return nome;
	}

	/**
	  * Modifica o atributo nome.
	  * 
	  * @param nome   Novo valor para o atributo.
	  */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	  * Recupera o atributo mana.
	  * 
	  * @return            o atributo
	  */
	public int getMana() {
		return mana;
	}

	/**
	  * Modifica o atributo mana.
	  * 
	  * @param mana   Novo valor para o atributo.
	  */
	public void setMana(int mana) {
		this.mana = mana;
	}

}