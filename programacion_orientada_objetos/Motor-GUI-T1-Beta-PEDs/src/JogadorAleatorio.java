import java.util.ArrayList;
import java.util.Random;

/**
* Esta classe representa um Jogador aleatório (realiza jogadas de maneira aleatória) para o jogo LaMa (Lacaios & Magias).
* @see java.lang.Object
* @author Rafael Arakaki - MC302
*/
public class JogadorAleatorio extends Jogador {
	private ArrayList<CartaLacaio> lacaios;
	private ArrayList<CartaLacaio> lacaiosOponente;
	private static Random aleatorio = new Random(0);
	private static double alfa = 1; // Chance de usar cartas lacaio/magia disponíveis no turno; Do contrário não usa nada (usa apenas poder heróico).
	private static double beta = 0.75; // Chance de atacar face; Do contrário ataca lacaios aleatoriamente.
	
	/**
	  * O método construtor do JogadorAleatorio.
	  * 
	  * @param maoInicial Contém a mão inicial do jogador. Deve conter o número de cartas correto dependendo se esta classe Jogador que está sendo construída é o primeiro ou o segundo jogador da partida. 
	  * @param primeiro   Informa se esta classe Jogador que está sendo construída é o primeiro jogador a iniciar nesta jogada (true) ou se é o segundo jogador (false).
	  */
	public JogadorAleatorio(ArrayList<Carta> maoInicial, boolean primeiro){
		primeiroJogador = primeiro;
		
		mao = maoInicial;
		lacaios = new ArrayList<CartaLacaio>();
		lacaiosOponente = new ArrayList<CartaLacaio>();
		
		// Mensagens de depuração:
		//System.out.println("Sou o " + (primeiro?"primeiro":"segundo") + " jogador (classe: JogadorAleatorio)");
		//System.out.println("Mao inicial:");
		//for(int i = 0; i < mao.size(); i++)
			//System.out.println("ID " + mao.get(i).getID() + ": " + mao.get(i));
	}
	
	// Processar turno deve receber uma lista de jogadas, e as jogadas devem ter o nome das cartas e não apenas os IDs.
	// (Porque os jogadores não sabem que ID corresponde ao nome das cartas de seus oponentes - apenas de si próprio).
	/**
	  * Um método que processa o turno de cada jogador. Este método deve retornar as jogadas do Jogador correspondente para o turno atual (ArrayList de Jogada).
	  * 
	  * @param mesa   O "estado do jogo" imediatamente antes do início do turno corrente. Este objeto de mesa contém todas as informações 'públicas' do jogo (lacaios vivos e suas vidas, vida dos heróis, etc).
	  * @param cartaComprada   A carta que o Jogador recebeu neste turno (comprada do Baralho). Obs: pode ser null se o Baralho estiver vazio ou o Jogador possuir mais de 10 cartas na mão.
	  * @param jogadasOponente   Um ArrayList de Jogada que foram os movimentos utilizados pelo oponente no último turno, em ordem.
	  * @return            um ArrayList com as Jogadas decididas
	  */
	public ArrayList<Jogada> processarTurno (Mesa mesa, Carta cartaComprada, ArrayList<Jogada> jogadasOponente){
		int minhaMana, minhaVida;
		if(cartaComprada != null)
			mao.add(cartaComprada);
		if(primeiroJogador){
			minhaMana = mesa.getManaJog1();
			minhaVida = mesa.getVidaHeroi1();
			lacaios = mesa.getLacaiosJog1();
			lacaiosOponente = mesa.getLacaiosJog2();
			//System.out.println("--------------------------------- Começo de turno pro jogador1");
		}
		else{
			minhaMana = mesa.getManaJog2();
			minhaVida = mesa.getVidaHeroi2();
			lacaios = mesa.getLacaiosJog2();
			lacaiosOponente = mesa.getLacaiosJog1();
			//System.out.println("--------------------------------- Começo de turno pro jogador2");
		}
		
		ArrayList<Jogada> minhasJogadas = new ArrayList<Jogada>();
		ArrayList<Jogada> possiveisDrops = new ArrayList<Jogada>();
		ArrayList<Integer> possiveisDrops_CartasIdx = new ArrayList<Integer>();
		
		// Cria lista com cartas da mão possíveis
		int numMeusLacaiosLimite = lacaios.size();
		if( alfa >= aleatorio.nextDouble()){
			for(int i = 0; i < mao.size(); i++){
				Carta card = mao.get(i);
				if(card instanceof CartaLacaio && card.getMana() <= minhaMana && numMeusLacaiosLimite < 7){
					Jogada dropLacaio = new Jogada(TipoJogada.LACAIO, card, null);
					possiveisDrops.add(dropLacaio);
					possiveisDrops_CartasIdx.add(new Integer(i));
					//System.out.println("#Adicionando na lista("+i+"): "+card.getNome()+" de custo "+card.getMana()+" id:"+card.getID());
				}
				else if (card instanceof CartaMagia && card.getMana() <= minhaMana){
					if( ((CartaMagia) card).getMagiaTipo() != TipoMagia.BUFF ){ // Essa classe nao usa magias de buff
						// Magia de alvo: precisa escolher um alvo
						int alvoRand = aleatorio.nextInt(lacaiosOponente.size()+1);
						Jogada dropMagia;
						//alvoRand = lacaiosOponente.size(); // estrategia face //if( primeiroJogador == true) 
						if( alvoRand == lacaiosOponente.size())
							dropMagia = new Jogada(TipoJogada.MAGIA, card, null); // null como alvo = Herói do oponente
						else
							dropMagia = new Jogada(TipoJogada.MAGIA, card, lacaiosOponente.get(alvoRand));
						possiveisDrops.add(dropMagia);
						possiveisDrops_CartasIdx.add(new Integer(i));
						//System.out.println("*Adicionando na lista("+i+"): "+card.getNome()+" de custo "+card.getMana()+" id:"+card.getID()+" alvo id: "+lacaiosOponente.get(alvoRand).getID());
					}
				}
			}
			while(possiveisDrops.size() > 0){
				int chosen = aleatorio.nextInt(possiveisDrops.size()); // obs: colocar +1 depois do .size() para ter chance de nao fazer nada (jogador mais ingenuo ainda!)
				if(chosen < possiveisDrops.size()){
					Jogada jogChosen = possiveisDrops.get(chosen);
					minhaMana -= mao.get(possiveisDrops_CartasIdx.get(chosen)).getMana();
					Carta cardUsada = mao.get(possiveisDrops_CartasIdx.get(chosen));
					//System.out.println("Escolhida a carta: "+cardUsada+" id:"+cardUsada.getID());
					//System.out.println("Removendo o indice "+possiveisDrops_CartasIdx.get(chosen).intValue());
					mao.remove(possiveisDrops_CartasIdx.get(chosen).intValue());
					minhasJogadas.add(jogChosen);
					if(cardUsada instanceof CartaMagia){
						if( jogChosen.getCartaAlvo() != null)
							processaDanosMagia((CartaMagia) cardUsada, jogChosen.getCartaAlvo().getID());
						else
							processaDanosMagia((CartaMagia) cardUsada, -1);
					}
					else
						numMeusLacaiosLimite++; // contador para não ultrapassar 7 lacaios em mesa
					// Apaga as listas
					possiveisDrops.clear();
					possiveisDrops_CartasIdx.clear();
					
					// Cria lista com cartas da mão possíveis
					for(int i = 0; i < mao.size(); i++){
						Carta card = mao.get(i);
						if(card instanceof CartaLacaio && card.getMana() <= minhaMana && numMeusLacaiosLimite < 7){
							Jogada dropLacaio = new Jogada(TipoJogada.LACAIO, card, null);
							possiveisDrops.add(dropLacaio);
							possiveisDrops_CartasIdx.add(new Integer(i));
							//System.out.println("#Adicionando na lista("+i+"): "+card.getNome()+" de custo "+card.getMana()+" id:"+card.getID());
						}
						else if (card instanceof CartaMagia && card.getMana() <= minhaMana){
							if( ((CartaMagia)card).getMagiaTipo() == TipoMagia.BUFF ){
								// Trata-se de uma magia de buff, precisa ter um alvo lacaio vivo
								Jogada dropMagia;
								if( lacaios.size() > 0){
									int alvoRand = aleatorio.nextInt(lacaios.size());
									dropMagia = new Jogada(TipoJogada.MAGIA, card, lacaios.get(alvoRand));
									possiveisDrops.add(dropMagia);
									possiveisDrops_CartasIdx.add(new Integer(i));
								}
							}
							else{
								// Trata-se de uma magia de dano (alvo ou area)
								// Magia de alvo: precisa escolher um alvo
								int alvoRand = aleatorio.nextInt(lacaiosOponente.size()+1);
								//alvoRand = lacaiosOponente.size(); // estrategia face //if( primeiroJogador == true) 
								Jogada dropMagia;
								if( alvoRand == lacaiosOponente.size())
									dropMagia = new Jogada(TipoJogada.MAGIA, card, null);
								else
									dropMagia = new Jogada(TipoJogada.MAGIA, card, lacaiosOponente.get(alvoRand));
								possiveisDrops.add(dropMagia);
								possiveisDrops_CartasIdx.add(new Integer(i));
								//System.out.println("*Adicionando na lista("+i+"): "+card.getNome()+" de custo "+card.getMana()+" id:"+card.getID()+" alvo id: "+lacaiosOponente.get(alvoRand).getID());
							}
						}
					}
				}
				else{
					// Resolveu não dropar nenhuma carta a mais neste turno (chance aleatoria)
					possiveisDrops.clear();
					possiveisDrops_CartasIdx.clear();
				}
			}
		}
		
		// 2) Verifica uso do poder heróico
		if (minhaMana >= 2 && lacaiosOponente.size() > 0){
			int alvoRand = aleatorio.nextInt(lacaiosOponente.size()+1);
			Jogada poderHeroico;
			//alvoRand = lacaiosOponente.size(); // estrategia face //if( primeiroJogador == true)
			
			if(alvoRand == lacaiosOponente.size()){
				alvoRand = -1;
				poderHeroico = new Jogada(TipoJogada.PODER, null, null);
			}
			else{
				if(lacaiosOponente.get(alvoRand).getAtaque() >= minhaVida) // Não ataca se isto for fazer você perder o jogo
					poderHeroico = new Jogada(TipoJogada.PODER, null, null);
				else
					poderHeroico = new Jogada(TipoJogada.PODER, null, lacaiosOponente.get(alvoRand));
			}
			//System.out.println("Usando poder heroico no lacaio id="+lacaiosOponente.get(alvoRand).getID());
			minhasJogadas.add(poderHeroico);
			minhaMana -= 2;
			if( alvoRand != -1){
				lacaiosOponente.get(alvoRand).setVidaAtual(lacaiosOponente.get(alvoRand).getVidaAtual()-1);
				if( lacaiosOponente.get(alvoRand).getVidaAtual() <= 0)
					lacaiosOponente.remove(alvoRand); // Lacaio oponente morreu
			}
		}
		
		// 3) Faz os ataques com os lacaios em alvos aleatórios (inclusive o herói do oponente!)
		for(int k = 0; k < lacaios.size(); k++){
			CartaLacaio meuLacaio = lacaios.get(k);
			//alvoRand = lacaiosOponente.size(); // estrategia face //if( primeiroJogador == true) 
			if( beta >= aleatorio.nextDouble() || lacaiosOponente.size() == 0){
				// Ataque ao heroi do oponente
				Jogada ataque = new Jogada(TipoJogada.ATAQUE, meuLacaio, null);
				minhasJogadas.add(ataque);
			}
			else{
				// Ataque ao lacaio do oponente
				int alvoRand = aleatorio.nextInt(lacaiosOponente.size());
				CartaLacaio lacaioOpo = lacaiosOponente.get(alvoRand);
				Jogada ataque = new Jogada(TipoJogada.ATAQUE, meuLacaio, lacaioOpo);
				minhasJogadas.add(ataque);
				processaDanosAtaque(meuLacaio, lacaioOpo, alvoRand);
			}
		}
		return minhasJogadas;
	}
	
	// Necessário para calcular quando um lacaio do oponente morrer.
	// Quando um lacaio do oponente morrer, não podemos mais tê-lo como alvo, obviamente.
	private void processaDanosMagia(CartaMagia magiaUsada, int alvoID){
		if(magiaUsada.getMagiaTipo() == TipoMagia.ALVO){
			if( alvoID != -1){
				int i;
				for(i = 0; i < lacaiosOponente.size(); i++)
					if(lacaiosOponente.get(i).getID() == alvoID) break;
				if( i >= lacaiosOponente.size() || lacaiosOponente.get(i).getID() != alvoID )
					throw new RuntimeException("Erro JogadorAleatorio: Tentou usar magia em alvo invalido. Alvo id:"+lacaiosOponente.get(i).getID());
				lacaiosOponente.get(i).setVidaAtual(lacaiosOponente.get(i).getVidaAtual()-magiaUsada.getMagiaDano());
				//System.out.println("Jogador. Lacaio id="+lacaiosOponente.get(i).getID()+" Tomou uma magia com "+magiaUsada.getMagiaDano()+" de dano e agora tem só vida ="+lacaiosOponente.get(i).getVidaMesa());
				if( lacaiosOponente.get(i).getVidaAtual() <= 0){
					//System.out.println("Jogador. Lacaio id="+lacaiosOponente.get(i).getID()+" morreu");
					lacaiosOponente.remove(i); // Lacaio oponente morreu
				}
			}
			else{
				// dano apenas no heroi... nada  a fazer
			}
		}
		else{
			int i;
			for(i = 0; i < lacaiosOponente.size(); i++){
				lacaiosOponente.get(i).setVidaAtual(lacaiosOponente.get(i).getVidaAtual()-magiaUsada.getMagiaDano());
				//System.out.println("Jogador. Lacaio id="+lacaiosOponente.get(i).getID()+" Tomou uma AREA com "+magiaUsada.getMagiaDano()+" de dano e agora tem só vida ="+lacaiosOponente.get(i).getVidaMesa());
				if( lacaiosOponente.get(i).getVidaAtual() <= 0){
					//System.out.println("Jogador. Lacaio id="+lacaiosOponente.get(i).getID()+" morreu");
					lacaiosOponente.remove(i); // Lacaio oponente morreu
					i--;
				}
			}
			// Dano também no heroi: nada a fazer
		}
	}
	
	private void processaDanosAtaque (CartaLacaio origemAtk, CartaLacaio destinoAtk, int idxDestino){
		//System.out.println("Lacaio="+origemAtk.getID()+" atacou lacaio="+destinoAtk.getID()+" com ataque "+origemAtk.getAtaque());
		destinoAtk.setVidaAtual(destinoAtk.getVidaAtual() - origemAtk.getAtaque());
		if(destinoAtk.getVidaAtual() <= 0) // Lacaio oponente morreu
			lacaiosOponente.remove(idxDestino);
		// Não precisamos saber se nosso lacaio morreu.
	}
	
	// ProcessaTurno() aleatório:
	// 1) Para cada carta, verifica se é possível ser utilizada no momento. Se for possível, é colocada em um array de cartas possiveis.
	// Então sorteie uma das faças e faça seu movimento. Se precisar de um alvo, também faça aleatoriamente um alvo.
	// Agora re-avalie todas as cartas que ainda é possível ser utilizada (mana) e repita o processo... até que não hajam cartas possíveis.
	// 2) Se houver mana para poder heróico, utilize em um alvo aleatório.
	// 3) Agora por fim, verifique os lacaios. Se houver lacaio que pode atacar, ataque em um alvo aleatório (dentre o herói e os lacaios do oponente)
}