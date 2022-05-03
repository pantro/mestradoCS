public ArrayList<Jogada> processarTurno (Mesa mesa, Carta cartaComprada, ArrayList<Jogada> jogadasOponente){
	int minhaMana, oponenteMana;
	ArrayList<Carta> lacaiosMeus;
	ArrayList<Carta> lacaiosOponente;
	
	if(cartaComprada != null)
		mao.add(cartaComprada);
	
	if(primeiroJogador){
		minhaMana = mesa.getManaHeroi1();
		oponenteMana = mesa.getManaHeroi2();
		lacaiosMeus = mesa.getLacaiosHeroi1();
		lacaiosOponente = mesa.getLacaiosHeroi2();
	}
	else{
		minhaMana = mesa.getManaHeroi2();
		oponenteMana = mesa.getManaHeroi1();
		lacaiosMeus = mesa.getLacaiosHeroi2();
		lacaiosOponente = mesa.getLacaiosHeroi1();
	}
	
	ArrayList<Jogada> minhasJogadas = new ArrayList<Jogada>();
	// Aqui decide quais serao as jogadas
	return minhasJogadas;
}
