ArrayList<Carta> lacaiosOponente = primeiroJogador ? mesa.getLacaiosJog2() : mesa.getLacaiosJog1();
for(int i = 0; i < lacaiosOponente.size(); i++){
	Carta lacaioOpo = lacaiosOponente.get(i);
	System.out.println("Lacaio do oponente descoberto: "+ lacaioOpo);
}
