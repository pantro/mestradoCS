public class Carro {
	
	private String nome;
	private String marca;
	private int ano;
	
	@Override
	public String toString() {
		String out = "";
		out = out + "Nome= "+getNome();
		out = out + ",Marca= "+getMarca();
		out = out + ",Ano= "+getAno();
		return out;
		
	}

	// Outras funcoes abaixo (get, set, ...)
}
