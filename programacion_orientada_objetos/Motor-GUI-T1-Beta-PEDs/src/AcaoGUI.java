import java.util.ArrayList;

/**
* Esta classe representa as ações que o Motor informa à Interface Gráfica.
* Essas ações incluem todo tipo de atualização de informação na interface gráfica, por exemplo: mostrar uma nova carta na mão, no campo, atualizar vida ou mana, etc.
* Para conferir os diferentes tipos de ações, consulte a classe @TipoAcao.
* @see java.lang.Object
* @author Rafael Arakaki - MC302
*/
public class AcaoGUI {

	private TipoAcao tipo;
	private Carta cartaAlvo;
	private int arg;
	private boolean jogador;
	
	/**
	* Construtor da classe AcaoGUI.
	* 
	* @param tipo Tipo da ação.
	* @param cartaAlvo Carta alvo da ação (opcional dependendo do TipoAção).
	* @param arg Valor de argumento da ação (opcional dependendo do TipoAção).
	* @param jogador Em relação a qual jogador a ação está sendo realizada (true=primeiro jogador, false=segundo jogador).
	* 
	* <p> Relação de @TipoAcao com argumento @cartaAlvo:
	* SET_LACAIO_HP, SET_LACAIO_BUFFATTACK: a carta que representa o lacaio que está tendo seu hp/ataque atualizados.
	* ADICIONAR_MAO, RETIRAR_MAO, ADICIONAR_MESA, RETIRAR_MESA: representa qual carta está sendo adicionada/retirada.
	* demais tipos: nada </p>
	* 
	* <p> Relação de @TipoAcao com argumento @arg:
	* INICIO_TURNO, FIM_TURNO: número do turno que está iniciando/terminando.
	* SET_NUM_BARALHO, SET_HP_HEROI, SET_MANA_HEROI, SET_MAXMANA_HEROI: valor que está sendo atualizado.
	* SET_LACAIO_HP, SET_LACAIO_BUFFATTACK: hp atual do lacaio que deverá ser exibido. </p>
	* 
	* <p> Relação de @TipoAcao com argumento @jogador:
	* INICIO_TURNO, FIM_TURNO: jogador que está tendo o turno iniciado/terminado.
	* FIM_PARTIDA: jogador que venceu o jogo.
	* demais tipos: qual jogador está tendo seu(sua) baralho/hp/mana/lacaio atualizado(a). </p>
	* 
	* @see java.lang.Object
	*/
	public AcaoGUI(TipoAcao tipo, Carta cartaAlvo, int arg, boolean jogador) {
		this.tipo = tipo;
		this.cartaAlvo = cartaAlvo;
		this.arg = arg;
		this.jogador = jogador;
	}
	

	public TipoAcao getTipo() {
		return tipo;
	}

	public void setTipo(TipoAcao tipo) {
		this.tipo = tipo;
	}
	
	public Carta getCartaAlvo() {
		return cartaAlvo;
	}

	public void setCartaAlvo(Carta cartaAlvo) {
		this.cartaAlvo = cartaAlvo;
	}

	public int getArg() {
		return arg;
	}

	public void setArg(int arg) {
		this.arg = arg;
	}

	public boolean isJogador() {
		return jogador;
	}

	public void setJogador(boolean jogador) {
		this.jogador = jogador;
	}
	
}
