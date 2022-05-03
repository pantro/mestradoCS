import java.util.ArrayList;
import java.util.Random;

/**
* Esta classe contém um Baralho, com suas cartas em ordem.
* @see java.lang.Object
* @author Rafael Arakaki - MC302
*/
public class Baralho implements java.io.Serializable {
	
	private ArrayList<Carta> cartas;
	private static Random geradorAleatorio = new Random();
	private static boolean deterministico = false;
	
	/**
	  * Método que retorna se a classe Baralho está em modo determinístico (true) ou não (false).
	  * 
	  * @return            um boolean, onde true quando está no modo determinístico, e false quando não.
	  */
	public static boolean isDeterministico() {
		return deterministico;
	}
	
	/**
	  * Método que muda a configuração de determinismo da classe Baralho. Com o determinismo, o embaralhamento é operado sempre da mesma maneira, facilitando depurações.
	  * 
	  * @param deterministico   Um booleano para dizer se o Baralho deve ser determinístico (true) ou não (false).
	  */
	public static void setDeterministico(boolean deterministico) {
		Baralho.deterministico = deterministico;
		if (deterministico) {
			geradorAleatorio = new Random(0);
		} else {
			geradorAleatorio = new Random();
		}
	}
	

	/**
	  * Construtor da classe Baralho.
	  * 
	  * @param cartas   Um ArrayList contendo todas as cartas do baralho
	  */
	public Baralho(ArrayList<Carta> cartas) {
		this.cartas = cartas;
	}

	/**
	  * Recupera o atributo cartas.
	  * 
	  * @return            o atributo cartas desta classe.
	  */
	public ArrayList<Carta> getCartas() {
		return cartas;
	}

	/**
	  * Modifica o atributo cartas.
	  * 
	  * @param cartas   Novo valor para o atributo.
	  */
	public void setCartas(ArrayList<Carta> cartas) {
		this.cartas = cartas;
	}
	
	/**
	  * Gera um inteiro pseudoaleatório.
	  * 
	  * @param num   Um limitante para o número pseudoaleatório
	  * @return 		Um inteiro entre 0 e num-1 (inclusive).
	  */
	static int gerarInt(int num){
		return geradorAleatorio.nextInt(num);
	}
	
	/**
	  * Embaralha o Baralho, alterando a ordem das cartas no ArrayList.
	  * 
	  */
	public void embaralhar(){
		int i, j;
		
		for(i = 1; i < cartas.size(); i++){
			j = gerarInt(i+1); // Sorteia uma posição dentre [0,i]
			if(j != i){ // Se a posição sorteada for diferente da atual, troque com os elementos destas posições.
				Carta a = cartas.get(i);
				Carta b = cartas.get(j);
				cartas.remove(i);
				cartas.add(i, b);
				cartas.remove(j);
				cartas.add(j, a);
			}
		}
	}
	
	/**
	  * Compra um carta do Baralho - isto é, remove a carta do "topo" do Baralho e devolve no retorno deste método.
	  * 
	  * @return 		um objeto Carta que é a carta comprada.
	  */
	public Carta comprarCarta (){
		if( cartas.size() > 0){
			Carta ret = cartas.get(0);
			cartas.remove(0);
			return ret;
		}
		else{
			throw new RuntimeException ("ERRO Baralho, no metodo comprarCarta(): Nao há mais cartas para comprar do Deck.");
		}
	}

}
