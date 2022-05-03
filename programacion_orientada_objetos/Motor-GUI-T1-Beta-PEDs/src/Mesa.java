import java.util.ArrayList;

/**
* Esta classe representa as informações públicas presentes na Mesa do jogo LaMa (Lacaios & Magias).
* @see java.lang.Object
* @author Rafael Arakaki - MC302
*/

public class Mesa {

	private ArrayList<CartaLacaio> lacaiosJog1;
	private ArrayList<CartaLacaio> lacaiosJog2;
	private int vidaHeroi1;
	private int vidaHeroi2;
	private int numCartasJog1;
	private int numCartasJog2;
	private int manaJog1;
	private int manaJog2;
	
	/**
	  * O método construtor de Mesa.
	  * 
	  * @param lacaiosJog1 Lacaios na mesa do primeiro jogador
	  * @param lacaiosJog2 Lacaios na mesa do segundo jogador
	  * @param vidaHeroi1 Vida do herói do primeiro jogador
	  * @param vidaHeroi2 Vida do herói do segundo jogador
	  * @param numCartasJog1 Número de cartas na mão do primeiro jogador
	  * @param numCartasJog2 Número de cartas na mão do segundo jogador
	  * @param manaJog1 Limite de mana neste turno do primeiro jogador
	  * @param manaJog2 Limite de mana neste turno do segundo jogador
	  */
	public Mesa(ArrayList<CartaLacaio> lacaiosJog1, ArrayList<CartaLacaio> lacaiosJog2, int vidaHeroi1, int vidaHeroi2, int numCartasJog1, int numCartasJog2, int manaJog1, int manaJog2) {
		this.lacaiosJog1 = lacaiosJog1;
		this.lacaiosJog2 = lacaiosJog2;
		this.vidaHeroi1 = vidaHeroi1;
		this.vidaHeroi2 = vidaHeroi2;
		this.numCartasJog1 = numCartasJog1;
		this.numCartasJog2 = numCartasJog2;
		this.manaJog1 = manaJog1;
		this.manaJog2 = manaJog2;
	}
	
	/**
	  * Recupera o atributo lacaiosJog1.
	  * 
	  * @return            o atributo
	  */
	public ArrayList<CartaLacaio> getLacaiosJog1() {
		return lacaiosJog1;
	}

	/**
	  * Modifica o atributo lacaiosJog1.
	  * 
	  * @param lacaiosJog1   Novo valor para o atributo.
	  */
	public void setLacaiosJog1(ArrayList<CartaLacaio> lacaiosJog1) {
		this.lacaiosJog1 = lacaiosJog1;
	}

	/**
	  * Recupera o atributo lacaiosJog2.
	  * 
	  * @return            o atributo
	  */
	public ArrayList<CartaLacaio> getLacaiosJog2() {
		return lacaiosJog2;
	}

	/**
	  * Modifica o atributo lacaiosJog2.
	  * 
	  * @param lacaiosJog2   Novo valor para o atributo.
	  */
	public void setLacaiosJog2(ArrayList<CartaLacaio> lacaiosJog2) {
		this.lacaiosJog2 = lacaiosJog2;
	}

	/**
	  * Recupera o atributo vidaHeroi1.
	  * 
	  * @return            o atributo
	  */
	public int getVidaHeroi1() {
		return vidaHeroi1;
	}

	/**
	  * Modifica o atributo vidaHeroi1.
	  * 
	  * @param vidaHeroi1   Novo valor para o atributo.
	  */
	public void setVidaHeroi1(int vidaHeroi1) {
		this.vidaHeroi1 = vidaHeroi1;
	}

	/**
	  * Recupera o atributo vidaHeroi2.
	  * 
	  * @return            o atributo
	  */
	public int getVidaHeroi2() {
		return vidaHeroi2;
	}

	/**
	  * Modifica o atributo vidaHeroi2.
	  * 
	  * @param vidaHeroi2   Novo valor para o atributo.
	  */
	public void setVidaHeroi2(int vidaHeroi2) {
		this.vidaHeroi2 = vidaHeroi2;
	}

	/**
	  * Recupera o atributo numCartasJog1.
	  * 
	  * @return            o atributo
	  */
	public int getNumCartasJog1() {
		return numCartasJog1;
	}

	/**
	  * Modifica o atributo numCartasJog1.
	  * 
	  * @param numCartasJog1   Novo valor para o atributo.
	  */
	public void setNumCartasJog1(int numCartasJog1) {
		this.numCartasJog1 = numCartasJog1;
	}

	/**
	  * Recupera o atributo numCartasJog2.
	  * 
	  * @return            o atributo
	  */
	public int getNumCartasJog2() {
		return numCartasJog2;
	}

	/**
	  * Modifica o atributo numCartasJog2.
	  * 
	  * @param numCartasJog2   Novo valor para o atributo.
	  */
	public void setNumCartasJog2(int numCartasJog2) {
		this.numCartasJog2 = numCartasJog2;
	}

	/**
	  * Recupera o atributo manaJog1.
	  * 
	  * @return            o atributo
	  */
	public int getManaJog1() {
		return manaJog1;
	}

	/**
	  * Modifica o atributo manaJog1.
	  * 
	  * @param manaJog1   Novo valor para o atributo.
	  */
	public void setManaJog1(int manaJog1) {
		this.manaJog1 = manaJog1;
	}

	/**
	  * Recupera o atributo manaJog2.
	  * 
	  * @return            o atributo
	  */
	public int getManaJog2() {
		return manaJog2;
	}

	/**
	  * Modifica o atributo manaJog2.
	  * 
	  * @param manaJog2   Novo valor para o atributo.
	  */
	public void setManaJog2(int manaJog2) {
		this.manaJog2 = manaJog2;
	}
	
}
