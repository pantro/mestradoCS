/**
* Esta classe representa uma Jogada, que pode ser qualquer movimento válido no jogo LaMa (Lacaios & Magias).
* @see java.lang.Object
* @author Rafael Arakaki - MC302
*/
public class Jogada implements java.io.Serializable {
	private TipoJogada tipo; // TipoJogada.LACAIO == carta lacaio jogada da mão, TipoJogada.MAGIA == carta magia jogada da mão, TipoJogada.ATAQUE == ataque de lacaio da mesa, TipoJogada.PODER == poder heróico.
	private Carta cartaJogada;
	private Carta cartaAlvo;
	
	/**
	  * Construtor da classe Jogada - que é um movimento qualquer no jogo - que pode ser dos tipos: baixar lacaio para a mesa (TipoJogada.LACAIO), usar cartas de magia (TipoJogada.MAGIA),
	  * atacar com lacaio da mesa (TipoJogada.ATAQUE) ou utilizar poder heróico (TipoJogada.PODER).
	  * 
	  * @param tipo   O tipo da jogada (ver enumeração TipoJogada)
	  * @param cartaJogada    Carta utilizada nesta jogada (se houver)
	  * @param cartaAlvo Carta alvo do oponente nesta jogada (se houver), ou null para mirar no herói inimigo.
	  */
	public Jogada(TipoJogada tipo, Carta cartaJogada, Carta cartaAlvo) {
		this.tipo = tipo;
		this.cartaJogada = cartaJogada;
		this.cartaAlvo = cartaAlvo;
	}
	
	public Jogada(Jogada jogada) {
		tipo = jogada.getTipo();
		cartaJogada = jogada.getCartaJogada();
		cartaAlvo = jogada.getCartaAlvo();
	}

	/**
	  * Recupera o atributo tipo.
	  * 
	  * @return            o atributo
	  */
	public TipoJogada getTipo() {
		return tipo;
	}
	/**
	  * Modifica o atributo tipo.
	  * 
	  * @param tipo   Novo valor para o atributo.
	  */
	public void setTipo(TipoJogada tipo) {
		this.tipo = tipo;
	}
	
	/**
	  * Recupera o atributo cartaJogada.
	  * 
	  * @return            o atributo
	  */
	public Carta getCartaJogada() {
		return cartaJogada;
	}
	/**
	  * Modifica o atributo cartaJogada.
	  * 
	  * @param cartaJogada   Novo valor para o atributo.
	  */
	public void setCartaJogada(Carta cartaJogada) {
		this.cartaJogada = cartaJogada;
	}
	
	/**
	  * Recupera o atributo cartaAlvo.
	  * 
	  * @return            o atributo
	  */
	public Carta getCartaAlvo() {
		return cartaAlvo;
	}
	/**
	  * Modifica o atributo cartaAlvo.
	  * 
	  * @param cartaAlvo   Novo valor para o atributo.
	  */
	public void setCartaAlvo(Carta cartaAlvo) {
		this.cartaAlvo = cartaAlvo;
	}
}
