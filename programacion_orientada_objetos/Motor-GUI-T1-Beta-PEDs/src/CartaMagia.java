/**
* Esta classe representa uma CartaMagia.
* @see java.lang.Object
* @author Rafael Arakaki - MC302
*/
public class CartaMagia extends Carta {
	
	/**
	  * Construtor da classe CartaLacaio.
	  * 
	  * @param id   Um inteiro identificador ÚNICO para esta carta
	  * @param nome   O nome da carta
	  * @param mana   O custo de mana da magia
	  * @param magiaTipo   O tipo da magia (Area, Alvo ou Buff)
	  * @param magiaDano   Valor de dano ou buff da magia
	  */
	public CartaMagia(int id, String nome, int mana, TipoMagia magiaTipo, int magiaDano) {
		super(id, nome, mana);
		this.magiaTipo = magiaTipo;
		this.magiaDano = magiaDano;
	}

	private TipoMagia magiaTipo; // TipoMagia.ALVO ==Dano em alvo, TipoMagia.AREA == Dano em area.
	private int magiaDano;

	
	/**
	  * Recupera o atributo magiaTipo.
	  * 
	  * @return            o atributo
	  */
	public TipoMagia getMagiaTipo() {
		return magiaTipo;
	}

	/**
	  * Modifica o atributo magiaTipo.
	  * 
	  * @param magiaTipo   Novo valor para o atributo.
	  */
	public void setMagiaTipo(TipoMagia magiaTipo) {
		this.magiaTipo = magiaTipo;
	}
	
	/**
	  * Recupera o atributo magiaDano.
	  * 
	  * @return            o atributo
	  */
	public int getMagiaDano() {
		return magiaDano;
	}

	/**
	  * Modifica o atributo magiaDano.
	  * 
	  * @param magiaDano   Novo valor para o atributo.
	  */
	public void setMagiaDano(int magiaDano) {
		this.magiaDano = magiaDano;
	}

	/**
	  * Cria uma String com os dados de uma carta.
	  * 
	  * return String   uma String com algumas informações da carta descritas de uma maneira estética.
	  */
	@Override
	public String toString() {
		String out = "";
		out = out + getNome()+" ";
		if (magiaTipo == TipoMagia.ALVO){
			out = out + "("+getMagiaDano()+" dano em Alvo). ";
			out = out + "Mana = "+getMana() + "\n";
		}
		else if (magiaTipo == TipoMagia.BUFF){
			out = out + "(+"+getMagiaDano()+"/+"+getMagiaDano()+" buff). ";
			out = out + "Mana = "+getMana() + "\n";
		}
		else{
			out = out + "("+getMagiaDano()+" dano em Area). ";
			out = out + "Mana = "+getMana() + "\n";
		}
		return out;
	}
	
}
