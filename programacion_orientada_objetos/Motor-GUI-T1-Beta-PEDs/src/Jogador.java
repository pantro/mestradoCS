import java.util.ArrayList;

/**
* Esta classe representa uma classe Jogador abstrata.
* @see java.lang.Object
* @author Rafael Arakaki - MC302
*/
public abstract class Jogador {
	protected boolean primeiroJogador;
	protected ArrayList<Carta> mao;
	
	/**
	  * Construtor da classe abstrata Jogador.
	  * 
	  */
	public Jogador (){
	}
	
	/**
	  * Uma função que processa o turno de cada jogador. Esta função deve retornar as jogadas do Jogador correspondente para o turno atual (ArrayList de Jogada).
	  * 
	  * @param mesa   O "estado do jogo" imediatamente antes do início do turno corrente. Este objeto de mesa contém todas as informações 'públicas' do jogo (lacaios vivos e suas vidas, vida dos heróis, etc).
	  * @param cartaComprada   A carta que o Jogador recebeu neste turno (comprada do Baralho). Obs: pode ser null se o Baralho estiver vazio ou o Jogador possuir mais de 10 cartas na mão.
	  * @param jogadasOponente   Um ArrayList de Jogada que foram os movimentos utilizados pelo oponente no último turno, em ordem.
	  * @return            um ArrayList de Jogada
	  */
	abstract public ArrayList<Jogada> processarTurno (Mesa mesa, Carta cartaComprada, ArrayList<Jogada> jogadasOponente);
	/*{
		ArrayList<Jogada> jogadas = new ArrayList<Jogada>();
		return jogadas;
	}*/
}
