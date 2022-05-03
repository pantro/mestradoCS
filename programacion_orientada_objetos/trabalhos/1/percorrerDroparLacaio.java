int minhaMana = primeiroJogador ? mesa.getManaJog1() : mesa.getManaJog2();
for(int i = 0; i < mao.size(); i++){
	Carta card = mao.get(i);
	if(card instanceof CartaLacaio && card.getMana() <= minhaMana){
		Jogada lac = new Jogada(TipoJogada.LACAIO, card, null);
		minhasJogadas.add(lac);
		minhaMana -= card.getMana();
		System.out.println("Jogada: Baixei o lacaio: "+ card);
		mao.remove(i);
		i--;
	}
}
