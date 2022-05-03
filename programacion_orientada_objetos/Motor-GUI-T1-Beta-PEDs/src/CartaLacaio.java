/**
* Esta classe representa uma CartaLacaio.
* @see java.lang.Object
* @author Rafael Arakaki - MC302
*/
public class CartaLacaio extends Carta {
	
	/**
	  * Construtor da classe CartaLacaio.
	  * 
	  * @param id   Um inteiro identificador ÚNICO para esta carta
	  * @param nome   O nome da carta
	  * @param mana   O custo de mana deste lacaio
	  * @param ataque   O ataque deste lacaio
	  * @param vidaAtual   A vida atual deste lacaio
	  * @param vidaMaxima   A vida total deste lacaio (desconsiderando danos)
	  * @param efeito   Algum efeito especial do lacaio (se houver)
	  * @param turno   (Usado pelo Motor) Turno em que o lacaio entra em jogo
	  */
	public CartaLacaio(int id, String nome, int mana, int ataque, int vidaAtual, int vidaMaxima, TipoEfeito efeito, int turno) {
		super(id, nome, mana);
		this.ataque = ataque;
		this.vidaAtual = vidaAtual;
		this.vidaMaxima = vidaMaxima;
		this.efeito = efeito;
		this.turno = turno;
	}

	private int ataque;
	private int vidaAtual;
	private int vidaMaxima;
	private TipoEfeito efeito;
	private int turno;
	
	/**
	  * Recupera o atributo ataque.
	  * 
	  * @return            o atributo
	  */
	public int getAtaque() {
		return ataque;
	}

	/**
	  * Modifica o atributo ataque.
	  * 
	  * @param ataque   Novo valor para o atributo.
	  */
	public void setAtaque(int ataque) {
		this.ataque = ataque;
	}
	/**
	  * Recupera o atributo vidaAtual.
	  * 
	  * @return            o atributo
	  */
	public int getVidaAtual() {
		return vidaAtual;
	}

	/**
	  * Modifica o atributo vidaAtual.
	  * 
	  * @param vidaAtual   Novo valor para o atributo.
	  */
	public void setVidaAtual(int vidaAtual) {
		this.vidaAtual = vidaAtual;
	}
	/**
	  * Recupera o atributo vidaMaxima.
	  * 
	  * @return            o atributo
	  */
	public int getVidaMaxima() {
		return vidaMaxima;
	}

	/**
	  * Modifica o atributo vidaMaxima.
	  * 
	  * @param vidaMaxima   Novo valor para o atributo.
	  */
	public void setVidaMaxima(int vidaMaxima) {
		this.vidaMaxima = vidaMaxima;
	}
	/**
	  * Recupera o atributo efeito.
	  * 
	  * @return            o atributo
	  */
	public TipoEfeito getEfeito() {
		return efeito;
	}

	/**
	  * Modifica o atributo efeito.
	  * 
	  * @param efeito   Novo valor para o atributo.
	  */
	public void setEfeito(TipoEfeito efeito) {
		this.efeito = efeito;
	}
	/**
	  * Recupera o atributo turno.
	  * 
	  * @return            o atributo
	  */
	public int getTurno() {
		return turno;
	}

	/**
	  * Modifica o atributo turno.
	  * 
	  * @param turno   Novo valor para o atributo.
	  */
	public void setTurno(int turno) {
		this.turno = turno;
	}
	


	/**
	  * Cria uma String com os dados de uma CartaLacaio.
	  * 
	  * return String   uma String com algumas informações da carta descritas de uma maneira estética.
	  */
	@Override
	public String toString() {
		String out = "";
			out = out + getNome();
			out = out + " (Atq = "+getAtaque()+", ";
			out = out + "Vida = ("+getVidaAtual() + "/" +getVidaMaxima()+")). ";
			out = out + "Mana = "+getMana() + "\n";
		return out;
	}
	
}
