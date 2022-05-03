@Override
	public String toString() {
		String out = getNome()+" (ID: "+getID()+")\n";
		out = out + "Ataque = "+getAtaque()+"\n";
		out = out + "Vida Atual = "+getVidaAtual()+"\n";
		out = out + "Vida Maxima = "+getVidaMaxima()+"\n";
		out = out + "Custo de Mana = "+getCustoMana()+"\n";
		return out;
		
	}
