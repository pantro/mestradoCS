public void iniciarPartida (ArrayList<Carta> maoInicial, boolean primeiro){
	primeiroJogador = primeiro;
	mao = maoInicial;
	// Inicializacao de outros atributos aqui
	// ...
	
	// Mensagens de depuracao:
	System.out.println("Sou o " + (primeiro?"primeiro":"segundo") + " jogador(a)");
	System.out.println("Mao inicial:");
	for(int i = 0; i < mao.size(); i++)
		System.out.println("ID " + mao.get(i).getID() + ": " + mao.get(i));
}
