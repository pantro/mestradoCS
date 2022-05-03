import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
* Esta classe representa um Motor para gerenciar uma partida do jogo LaMa (Lacaios & Magias).
* @see java.lang.Object
* @author Rafael Arakaki - MC302
*/

public class Motor {
	
	private Jogador jogador1;
	private Jogador jogador2;
	
	//Baralho deck1Original;
	//Baralho deck2Original;
	
	private Baralho deck1;
	private Baralho deck2;
	
	private ArrayList<Carta> Mao1;
	private ArrayList<Carta> Mao2;
	
	private ArrayList<CartaLacaio> Lacaios1;
	private ArrayList<CartaLacaio> Lacaios2;
	
	// Apontadores genéricos (mudam conforme o turno do jogador)
	private Baralho deck;
	private ArrayList<Carta> mao;
	
	private ArrayList<CartaLacaio> lacaios;
	private ArrayList<CartaLacaio> lacaiosOponente;
	
	// Cartas iniciais
	static final int cartasIniJogador1 = 3; // equilibrado para jogadores aleatórios
	static final int cartasIniJogador2 = 4;
	
	private Mesa mesa;
	
	private int vidaHeroi1;
	private int vidaHeroi2;
	
	private int manaHeroi1;
	private int manaHeroi2;
	
	private int maxManaHeroi1;
	private int maxManaHeroi2;
	
	private int nCartasHeroi1;
	private int nCartasHeroi2;
	
	private int turno;
	
	private int verbose;
	private int timeLimit;
	
	public int erroJogador1;
	public int erroJogador2;
	
	public boolean limiteTempoJogador1;
	public boolean limiteTempoJogador2;
	
	
	private String nameclasse1;
	private String nameclasse2;
	
	//private int partidaNo;
	//PrintWriter writer1;
	

    private boolean poderHeroicoUsado;
    private HashSet<Integer> lacaiosAtacaramID;
    
    private InterfaceGrafica gui;
	ArrayList<Object> listaAcoesGUI;
	
	//PrintWriter writer2;
	
	/**
	  * Construtor da classe Motor.
	  * 
	  * @param deck1   Um ArrayList inicializado com as cartas do Baralho do Jogador 1 já embaralhado e sem as cartas que estão na mão do jogador1.
	  * @param deck2   Um ArrayList inicializado com as cartas do Baralho do Jogador 2 já embaralhado e sem as cartas que estão na mão do jogador2.
	  * @param mao1   Um ArrayList contendo as cartas na mão do Jogador 1.
	  * @param mao2   Um ArrayList contendo as cartas na mão do Jogador 2.
	  * @param jogador1   Um Jogador construído corretamente: com as cartas na mão que estão no argumento mao1 e com o argumento primeiro como true (primeiro jogador).
	  * @param jogador2   Um Jogador construído corretamente: com as cartas na mão que estão no argumento mao2 e com o argumento primeiro como false (segundo jogador).
	  * @param verbose    Um parâmetro que configure a verbosidade (impressão de mensagens) por parte do Motor. Com o parâmetro (0) não há mensagens e com o parâmetro (1) há mensagens.
	  * Após a inicialização o objeto Motor estará pronto para executar a partida pelo método executarPartida().
	  */
	public Motor(Baralho deck1, Baralho deck2, ArrayList<Carta> mao1, ArrayList<Carta> mao2, Jogador jogador1, Jogador jogador2, int verbose, int tempoLimite, String classe1, String classe2) { //, int partidaNo
		this.deck1 = deck1;
		this.deck2 = deck2;
		this.jogador1 = jogador1;
		this.jogador2 = jogador2;
		Mao1 = mao1;
		Mao2 = mao2;
		this.verbose = verbose;
		this.timeLimit = tempoLimite;
		erroJogador1 = 0;
		erroJogador2 = 0;
		limiteTempoJogador1 = false;
		limiteTempoJogador2 = false;
		this.nameclasse1 = classe1;
		this.nameclasse2 = classe2;
		gui = null;
		listaAcoesGUI = new ArrayList<Object>();
		if( verbose != 0 ){
			if( gui != null)
				System.out.println("Motor Construtor: A gui está ativa.");
			else
				System.out.println("Motor Construtor: A gui está inativa.");
			imprimir("*** Iniciando partida LaMa ***");
		}
		
		//this.partidaNo = partidaNo;
		
		//String namefile = classe1+"_VS_"+classe2;
		//try {
		//	writer1 = new PrintWriter(namefile+"-"+partidaNo+".txt", "UTF-8");
			//writer2 = new PrintWriter("logs/"+classe2+"/"+namefile+"-"+partidaNo, "UTF-8");
		//}
		//catch (FileNotFoundException | UnsupportedEncodingException e) {
			//System.out.println("Erro nos arquivos de LOG da partida de "+namefile+" Numero:"+partidaNo);
			//System.out.println("Erro:"+e.getMessage());
			// TODO Auto-generated catch block
		//	e.printStackTrace();
		//}
	}
	
	/**
	  * Construtor da classe Motor com interface gráfica.
	  * 
	  * @param deck1   Um ArrayList inicializado com as cartas do Baralho do Jogador 1 já embaralhado e sem as cartas que estão na mão do jogador1.
	  * @param deck2   Um ArrayList inicializado com as cartas do Baralho do Jogador 2 já embaralhado e sem as cartas que estão na mão do jogador2.
	  * @param mao1   Um ArrayList contendo as cartas na mão do Jogador 1.
	  * @param mao2   Um ArrayList contendo as cartas na mão do Jogador 2.
	  * @param jogador1   Um Jogador construído corretamente: com as cartas na mão que estão no argumento mao1 e com o argumento primeiro como true (primeiro jogador).
	  * @param jogador2   Um Jogador construído corretamente: com as cartas na mão que estão no argumento mao2 e com o argumento primeiro como false (segundo jogador).
	  * @param verbose    Um parâmetro que configure a verbosidade (impressão de mensagens) por parte do Motor. Com o parâmetro (0) não há mensagens e com o parâmetro (1) há mensagens.
	  * @param gui    O objeto que implementa a interface gráfica para o Motor.
	  * Após a inicialização o objeto Motor pronto para executar a partida pelo método executarPartida().
	  */
	public Motor(Baralho deck1, Baralho deck2, ArrayList<Carta> mao1, ArrayList<Carta> mao2, Jogador jogador1, Jogador jogador2, int verbose, int tempoLimite, String classe1, String classe2, InterfaceGrafica gui) {
		this.deck1 = deck1;
		this.deck2 = deck2;
		this.jogador1 = jogador1;
		this.jogador2 = jogador2;
		Mao1 = mao1;
		Mao2 = mao2;
		this.verbose = verbose;
		this.timeLimit = tempoLimite;
		erroJogador1 = 0;
		erroJogador2 = 0;
		limiteTempoJogador1 = false;
		limiteTempoJogador2 = false;
		this.nameclasse1 = classe1;
		this.nameclasse2 = classe2;
		this.gui = gui;
		if( verbose != 0 ){
			if( gui != null)
				System.out.println("Motor Construtor: A gui está ativa.");
			else
				System.out.println("Motor Construtor: A gui está inativa.");
		}
			
		//this.partidaNo = partidaNo;
		
		// Motor informa à GUI o início de uma partida de LaMa
		listaAcoesGUI = new ArrayList<Object>();
		listaAcoesGUI.add(new AcaoGUI(TipoAcao.INICIO_PARTIDA, null, 0, true));
		// Inicializa dados do Jogador1
		listaAcoesGUI.add(new AcaoGUI(TipoAcao.SET_HP_HEROI, null, 30, true));
		listaAcoesGUI.add(new AcaoGUI(TipoAcao.SET_MANA_HEROI, null, 0, true));
		listaAcoesGUI.add(new AcaoGUI(TipoAcao.SET_MAXMANA_HEROI, null, 0, true));
		listaAcoesGUI.add(new AcaoGUI(TipoAcao.SET_NUM_BARALHO, null, 27, true));
		// Inicializa dados do Jogador2
		listaAcoesGUI.add(new AcaoGUI(TipoAcao.SET_HP_HEROI, null, 30, false));
		listaAcoesGUI.add(new AcaoGUI(TipoAcao.SET_MANA_HEROI, null, 0, false));
		listaAcoesGUI.add(new AcaoGUI(TipoAcao.SET_MAXMANA_HEROI, null, 0, false));
		listaAcoesGUI.add(new AcaoGUI(TipoAcao.SET_NUM_BARALHO, null, 26, false));
		// Adiciona cartas do Jogador1
		for(Carta card: mao1){
			//System.out.println("Carta em mao1: "+card.getID());
			listaAcoesGUI.add(new AcaoGUI(TipoAcao.ADICIONAR_MAO, (Carta) UnoptimizedDeepCopy.copy(card), 0, true));
		}
		// Adiciona cartas do Jogador2
		for(Carta card: mao2){
			//System.out.println("Carta em mao2: "+card.getID());
			listaAcoesGUI.add(new AcaoGUI(TipoAcao.ADICIONAR_MAO, (Carta) UnoptimizedDeepCopy.copy(card), 0, false));
		}
		// Por convenção precisa ter um FIM_TURNO mesmo na inicialização dos dados
		listaAcoesGUI.add(new AcaoGUI(TipoAcao.FIM_TURNO, null, 0, true));
		
		if( verbose != 0 ) imprimir("*** Iniciando partida LaMa ***");
		if( gui != null )
			gui.processarAcoesGUI(listaAcoesGUI);
		
		// writer1 init aqui (se for o caso)
		
	}
	
	public int getVerbose() {
		return verbose;
	}

	public void setVerbose(int verbose) {
		this.verbose = verbose;
	}
	
	void imprimir(String texto){
	//	writer1.println(texto);
		System.out.println(texto);
		if( gui != null)
			listaAcoesGUI.add(new String(texto));
	}
	
	/**
	  * Método que executa uma partida com os dados deste Motor.
	  * 
	  * @return            retorna true quando o primeiro jogador vence a partida, e false caso contrário.
	  */
	public boolean executarPartida(){
		// Primeiro turno: 
		// 0) Embaralha os Decks e inicializa as estruturas
		// 1) Determina mão de cada jogador (5 primeiras cartas do Deck)
		// 2) Inicializa a mão e os dados de cada jogador, método iniciarPartida().
		// 3) Processa um turno com os dados da mesa de cada jogador, método processaTurno().
		// 4) Processa os efeitos do turno do jogador com suas jogadas.
		// 5) Faz o mesmo do passo 3 e 4 para o jogador 2.
		
		// Inicializa as estruturas
		////Campeonato.loopinfinitoEmPartida.put(nameclasse1+"_VS_"+nameclasse2, new Integer(0));
		vidaHeroi1 = vidaHeroi2 = 30;
		maxManaHeroi1 = maxManaHeroi2 = 0;
		nCartasHeroi1 = cartasIniJogador1; 
		nCartasHeroi2 = cartasIniJogador2;
		Lacaios1 = new ArrayList<CartaLacaio>();
		Lacaios2 = new ArrayList<CartaLacaio>();
		ArrayList<Jogada> movimentos = new ArrayList<Jogada>();
		int noCardDmgCounter1 = 1;
		int noCardDmgCounter2 = 1;
		turno = 1;
		erroJogador1 = 0;
		erroJogador2 = 0;
		limiteTempoJogador1 = false;
		limiteTempoJogador2 = false;
		manaHeroi1 = 0;
		manaHeroi2 = 0;

		for(int k = 0; k < 60; k++){
			if(verbose!=0) imprimir("\n=== TURNO "+turno+" ===\n");
			
			// Apontadores para jogador1
			deck = deck1;
			mao = Mao1;
			lacaios = Lacaios1;
			lacaiosOponente = Lacaios2;
			if(maxManaHeroi1 < 10) maxManaHeroi1++;
			manaHeroi1 = maxManaHeroi1;
			
			// Atualiza mesa
			@SuppressWarnings("unchecked")
			ArrayList<CartaLacaio> lacaios1clone = (ArrayList<CartaLacaio>) UnoptimizedDeepCopy.copy(Lacaios1);
			@SuppressWarnings("unchecked")
			ArrayList<CartaLacaio> lacaios2clone = (ArrayList<CartaLacaio>) UnoptimizedDeepCopy.copy(Lacaios2);
			mesa = new Mesa(lacaios1clone, lacaios2clone, vidaHeroi1, vidaHeroi2, nCartasHeroi1+1, nCartasHeroi2, maxManaHeroi1, maxManaHeroi2);	
			
			// *** INFORMA À GUI: INICIO DE TURNO JOGADOR 1 ***
			listaAcoesGUI.clear();
			listaAcoesGUI.add(new AcaoGUI(TipoAcao.INICIO_TURNO, null, k+1, true));
			// Inicializa dados do Jogador1
			listaAcoesGUI.add(new AcaoGUI(TipoAcao.SET_MANA_HEROI, null, manaHeroi1, true));
			listaAcoesGUI.add(new AcaoGUI(TipoAcao.SET_MAXMANA_HEROI, null, mesa.getManaJog1(), true));
			listaAcoesGUI.add(new AcaoGUI(TipoAcao.SET_NUM_BARALHO, null, (27-k > 0? (27-k) : 0), true));
			// Inicializa dados do Jogador2
			listaAcoesGUI.add(new AcaoGUI(TipoAcao.SET_MANA_HEROI, null, manaHeroi2, false));
			listaAcoesGUI.add(new AcaoGUI(TipoAcao.SET_MAXMANA_HEROI, null, mesa.getManaJog2(), false));
			listaAcoesGUI.add(new AcaoGUI(TipoAcao.SET_NUM_BARALHO, null, (26-k > 0? (26-k) : 0), false));
				
			
			// Processa o turno 1 do Jogador1
			if(verbose!=0) imprimir("\n----------------------- Começo de turno "+(k+1)+" para Jogador 1:");
			long startTime, endTime, totalTime;
			//startTime = System.currentTimeMillis();
			
			// Copia de movimentos da mão (do contrário pode referenciar cartas do campo do outro jogador e confundi-lo).
			@SuppressWarnings("unchecked")
			ArrayList<Jogada> cloneMovimentos1 = (ArrayList<Jogada>) UnoptimizedDeepCopy.copy(movimentos);
			
			startTime = System.nanoTime();
			////Campeonato.loopinfinitoEmPartida.put(nameclasse1+"_VS_"+nameclasse2, new Integer(1));
			try {
				if( deck.getCartas().size() > 0){
					if(nCartasHeroi1 >= 7) // TODO: verificar depois se o limite será de 7 cartas na mão mesmo.
						movimentos = jogador1.processarTurno(mesa, null, cloneMovimentos1);
					else{
						Carta cartaComprada = comprarCarta(true);
						Carta cartaComprada2 = (Carta) UnoptimizedDeepCopy.copy(cartaComprada);
						movimentos = jogador1.processarTurno(mesa, cartaComprada, cloneMovimentos1);
						// GUI: Adiciona cartas do Jogador1
						listaAcoesGUI.add(new AcaoGUI(TipoAcao.ADICIONAR_MAO, cartaComprada2, 0, true));
					}
					listaAcoesGUI.add(new AcaoGUI(TipoAcao.SET_NUM_BARALHO, null, deck.getCartas().size(), true));
				}
				else{
					vidaHeroi1 -= noCardDmgCounter1++;
					listaAcoesGUI.add(new AcaoGUI(TipoAcao.SET_HP_HEROI, null, vidaHeroi1, true));
					if( vidaHeroi1 <= 0){
						if(verbose!=0) imprimir("\n\n*** ACABOU no TURNO "+turno+"***\n");
						if(verbose!=0) imprimir("VidaFinal\nHeroi1: "+vidaHeroi1+". Heroi2:"+vidaHeroi2);
						if(verbose!=0) imprimir("Vitoria do Jogador2 ! (classe: "+nameclasse2+")");
						//writer1.close();
						listaAcoesGUI.add(new AcaoGUI(TipoAcao.FIM_PARTIDA, null, 0, false));
						if( gui != null ) gui.processarAcoesGUI(listaAcoesGUI);
						return false;
					}
					movimentos = jogador1.processarTurno(mesa, null, cloneMovimentos1);
				}
			}
			catch (Exception e) {
				imprimir("(Motor) Erro durante o turno do jogador1:\n:"+ e.getMessage());
				imprimir("Vitoria do Jogador2 por erros do outro jogador ! (classe: "+nameclasse2+")");
				imprimir("Erro print: "+e);
				e.printStackTrace();
				erroJogador1 = 1;
				//writer1.close();
				listaAcoesGUI.add(new AcaoGUI(TipoAcao.FIM_PARTIDA, null, 0, false));
				if( gui != null ) gui.processarAcoesGUI(listaAcoesGUI);
				return false; // O segundo jogador automaticamente é declarado vencedor *ERRO CODIFICAÇÃO*
			}
			//endTime = System.currentTimeMillis();
			////Campeonato.loopinfinitoEmPartida.put(nameclasse1+"_VS_"+nameclasse2, new Integer(0));
			endTime = System.nanoTime();
			totalTime = endTime - startTime;
			if( timeLimit > 0 && totalTime > 3e8){ // 300ms
				imprimir("(Motor) Tempo excedido pelo Primeiro Jogador no metodo processarTurno(): Usou tempo de "+totalTime/1e6+"ms. Limite: 100ms");
				imprimir("Vitoria do Jogador2 por erros do outro jogador ! (classe: "+nameclasse2+")");
				limiteTempoJogador1 = true;
				//writer1.close();
				listaAcoesGUI.add(new AcaoGUI(TipoAcao.FIM_PARTIDA, null, 0, false));
				if( gui != null ) gui.processarAcoesGUI(listaAcoesGUI);
				return false; // O segundo jogador automaticamente é declarado vencedor *LIMITE TEMPO*
			}
			else
				if(verbose!=0) imprimir("Tempo usado em processarTurno(): "+totalTime/1e6+"ms");

			try {
				this.poderHeroicoUsado = false;
	            this.lacaiosAtacaramID = new HashSet<Integer>();
				for(int i = 0; i < movimentos.size(); i++){
					listaAcoesGUI.add(new Jogada(movimentos.get(i))); // GUI: enfileira Jogada
					processarJogada (movimentos.get(i), true);
				}
				
				if(manaHeroi1 < 0)
					throw new RuntimeException("Jogadas excederam a mana. Mana após jogadas:"+manaHeroi1);
			}
			catch (Exception e) {
				imprimir("(Motor) Erro durante jogadas do jogador1:\n:"+ e.getMessage());
				imprimir("Vitoria do Jogador2 por erros do outro jogador ! (classe: "+nameclasse2+")");
				imprimir("Erro print: "+e);
				e.printStackTrace();
				erroJogador1 = 2;
				//writer1.close();
				listaAcoesGUI.add(new AcaoGUI(TipoAcao.FIM_PARTIDA, null, 0, false));
				if( gui != null ) gui.processarAcoesGUI(listaAcoesGUI);
				return false; // O segundo jogador automaticamente é declarado vencedor *ERRO CODIFICAÇÃO/JOGADA INVÁLIDA*
			}
			
			if( vidaHeroi2 <= 0){
				if(verbose!=0) imprimir("\n\n*** ACABOU no TURNO "+turno+"***\n");
				if(verbose!=0) imprimir("VidaFinal\nHeroi1: "+vidaHeroi1+". Heroi2:"+vidaHeroi2);
				if(verbose!=0) imprimir("Vitoria do Jogador1 ! (classe: "+nameclasse1+")");
				//writer1.close();
				listaAcoesGUI.add(new AcaoGUI(TipoAcao.FIM_PARTIDA, null, 0, true));
				if( gui != null ) gui.processarAcoesGUI(listaAcoesGUI);
				return true;
			}
			
			// GUI: *** Fim de turno ***
			listaAcoesGUI.add(new AcaoGUI(TipoAcao.FIM_TURNO, null, 0, true)); // GUI: Fim de turno
			if( gui != null ){ 
				//System.out.println("Chamando processarAcoesGUi Jogador1, size="+listaAcoesGUI.size());
				gui.processarAcoesGUI(listaAcoesGUI);
			}
			listaAcoesGUI.clear();
			
			// Apontadores para jogador2
			deck = deck2;
			mao = Mao2;
			lacaios = Lacaios2;
			lacaiosOponente = Lacaios1;
			if(turno == 1 ) maxManaHeroi2 = 2; // Tratamento especial para primeiro turno do jogador2
			else if(turno == 2 ) maxManaHeroi2 = 2;
			else if(maxManaHeroi2 < 10) maxManaHeroi2++;
			manaHeroi2 = maxManaHeroi2;
			
			// Atualiza mesa
			@SuppressWarnings("unchecked")
			ArrayList<CartaLacaio> lacaios1clone2 = (ArrayList<CartaLacaio>) UnoptimizedDeepCopy.copy(Lacaios1);
			@SuppressWarnings("unchecked")
			ArrayList<CartaLacaio> lacaios2clone2 = (ArrayList<CartaLacaio>) UnoptimizedDeepCopy.copy(Lacaios2);
			mesa = new Mesa(lacaios1clone2, lacaios2clone2, vidaHeroi1, vidaHeroi2, nCartasHeroi1, nCartasHeroi2+1, maxManaHeroi1, maxManaHeroi2);
			
			// *** INFORMA À GUI: INICIO DE TURNO JOGADOR 2 ***
			listaAcoesGUI.clear();
			listaAcoesGUI.add(new AcaoGUI(TipoAcao.INICIO_TURNO, null, k+1, false));
			// Inicializa dados do Jogador1
			listaAcoesGUI.add(new AcaoGUI(TipoAcao.SET_MANA_HEROI, null, manaHeroi1, true));
			listaAcoesGUI.add(new AcaoGUI(TipoAcao.SET_MAXMANA_HEROI, null, mesa.getManaJog1(), true));
			listaAcoesGUI.add(new AcaoGUI(TipoAcao.SET_NUM_BARALHO, null, (27-k-1 > 0? (27-k-1) : 0), true));
			// Inicializa dados do Jogador2
			listaAcoesGUI.add(new AcaoGUI(TipoAcao.SET_MANA_HEROI, null, manaHeroi2, false));
			listaAcoesGUI.add(new AcaoGUI(TipoAcao.SET_MAXMANA_HEROI, null, mesa.getManaJog2(), false));
			listaAcoesGUI.add(new AcaoGUI(TipoAcao.SET_NUM_BARALHO, null, (26-k > 0? (26-k) : 0), false));
			
			// Processa o turno 1 do Jogador2
			if(verbose!=0) imprimir("\n\n----------------------- Começo de turno "+(k+1)+" para Jogador 2:");
			//startTime = System.currentTimeMillis();
			
			// Copia de movimentos da mão (do contrário pode referenciar cartas do campo do outro jogador e confundi-lo).
			@SuppressWarnings("unchecked")
			ArrayList<Jogada> cloneMovimentos2 = (ArrayList<Jogada>) UnoptimizedDeepCopy.copy(movimentos);
			
			startTime = System.nanoTime();

			////Campeonato.loopinfinitoEmPartida.put(nameclasse1+"_VS_"+nameclasse2, new Integer(2));
			try{
				if( deck.getCartas().size() > 0){
					if(nCartasHeroi2 >= 7)
						movimentos = jogador2.processarTurno(mesa, null, cloneMovimentos2);
					else{
						Carta cartaComprada = comprarCarta(false);
						Carta cartaComprada2 = (Carta) UnoptimizedDeepCopy.copy(cartaComprada);
						movimentos = jogador2.processarTurno(mesa, cartaComprada, cloneMovimentos2);
						// GUI: Adiciona cartas do Jogador2
						listaAcoesGUI.add(new AcaoGUI(TipoAcao.ADICIONAR_MAO, cartaComprada2, 0, false));
					}
					listaAcoesGUI.add(new AcaoGUI(TipoAcao.SET_NUM_BARALHO, null, deck.getCartas().size(), false));
				}
				else{
					vidaHeroi2 -= noCardDmgCounter2++;
					listaAcoesGUI.add(new AcaoGUI(TipoAcao.SET_HP_HEROI, null, vidaHeroi2, false));
					if( vidaHeroi2 <= 0){
						if(verbose!=0) imprimir("\n\n*** ACABOU no TURNO "+turno+"***\n");
						if(verbose!=0) imprimir("VidaFinal\nHeroi1: "+vidaHeroi1+". Heroi2:"+vidaHeroi2);
						imprimir("Vitoria do Jogador1 ! (classe: "+nameclasse1+")");
						//writer1.close();
						listaAcoesGUI.add(new AcaoGUI(TipoAcao.FIM_PARTIDA, null, 0, true));
						if( gui != null ) gui.processarAcoesGUI(listaAcoesGUI);
						return true;
					}
					movimentos = jogador2.processarTurno(mesa, null, cloneMovimentos2);
				}
			}
			catch (Exception e) {
				imprimir("(Motor) Erro durante o turno do jogador2:\n:"+ e.getMessage());
				imprimir("Vitoria do Jogador1 por erros do outro jogador ! (classe: "+nameclasse1+")");
				imprimir("Erro print: "+e);
				e.printStackTrace();
				erroJogador2 = 1;
				//writer1.close();
				listaAcoesGUI.add(new AcaoGUI(TipoAcao.FIM_PARTIDA, null, 0, true));
				if( gui != null ) gui.processarAcoesGUI(listaAcoesGUI);
				return true; // O primeiro jogador automaticamente é declarado vencedor *ERRO CODIFICAÇÃO*
			}
			//endTime = System.currentTimeMillis();
			/////Campeonato.loopinfinitoEmPartida.put(nameclasse1+"_VS_"+nameclasse2, new Integer(0));
			
			endTime = System.nanoTime();
			totalTime = endTime - startTime;
			if( timeLimit > 0 && totalTime > 3e8){ // 300ms
				imprimir("(Motor) Tempo excedido pelo Segundo Jogador no metodo processarTurno(): Usou tempo de "+totalTime/1e6+"ms. Limite: 100ms");
				imprimir("Vitoria do Jogador1 por erros do outro jogador ! (classe: "+nameclasse1+")");
				limiteTempoJogador2 = true;
				//writer1.close();
				listaAcoesGUI.add(new AcaoGUI(TipoAcao.FIM_PARTIDA, null, 0, true));
				if( gui != null ) gui.processarAcoesGUI(listaAcoesGUI);
				return true; // O primeiro jogador automaticamente é declarado vencedor *LIMITE TEMPO*
			}
			else
				if(verbose!=0) imprimir("Tempo usado em processarTurno(): "+totalTime/1e6+"ms");
			try{
				this.poderHeroicoUsado = false;
	            this.lacaiosAtacaramID = new HashSet<Integer>();
				for(int i = 0; i < movimentos.size(); i++){
					listaAcoesGUI.add(new Jogada(movimentos.get(i))); // GUI: enfileira Jogada
					processarJogada (movimentos.get(i), false);
				}
				
				if(manaHeroi2 < 0)
					throw new RuntimeException("Jogadas excederam a mana. Mana após jogadas:"+manaHeroi2);
			}
			catch (Exception e) {
				imprimir("(Motor) Erro durante jogadas do jogador2:\n:"+ e.getMessage());
				imprimir("Vitoria do Jogador1 por erros do outro jogador ! (classe: "+nameclasse1+")");
				imprimir("Erro print: "+e);
				e.printStackTrace();
				erroJogador2 = 2;
				//writer1.close();
				listaAcoesGUI.add(new AcaoGUI(TipoAcao.FIM_PARTIDA, null, 0, true));
				if( gui != null ) gui.processarAcoesGUI(listaAcoesGUI);
				return true; // O primeiro jogador automaticamente é declarado vencedor *ERRO CODIFICAÇÃO/JOGADA INVÁLIDA*
			}
			
			if( vidaHeroi1 <= 0){
				if(verbose!=0) imprimir("\n\n*** ACABOU no TURNO "+turno+"***\n");
				if(verbose!=0) imprimir("VidaFinal\nHeroi1: "+vidaHeroi1+". Heroi2:"+vidaHeroi2);
				if(verbose!=0) imprimir("Vitoria do Jogador2 ! (classe: "+nameclasse2+")");
				//writer1.close();
				listaAcoesGUI.add(new AcaoGUI(TipoAcao.FIM_PARTIDA, null, 0, false));
				if( gui != null ) gui.processarAcoesGUI(listaAcoesGUI);
				return false;
			}
			
			// GUI: *** Fim de turno ***
			listaAcoesGUI.add(new AcaoGUI(TipoAcao.FIM_TURNO, null, 0, false)); // GUI: Fim de turno
			if( gui != null ){ 
				//System.out.println("Chamando processarAcoesGUi Jogador2, size="+listaAcoesGUI.size());
				gui.processarAcoesGUI(listaAcoesGUI);
			}
			listaAcoesGUI.clear();
			
			turno++;
			
		}
		
		// Nunca vai chegar aqui dependendo do número de rodadas
		if(verbose!=0) imprimir("VidaFinal\nHeroi1: "+vidaHeroi1+". Heroi2:"+vidaHeroi2);
		if( vidaHeroi1 > vidaHeroi2){
			imprimir("Vitoria do Jogador1 ! (classe: "+nameclasse1+")");
			//writer1.close();
			return true;
		}
		else{
			imprimir("Vitoria do Jogador2 ! (classe: "+nameclasse2+")");
			//writer1.close();
			return false;
		}
	}
	
	private Carta comprarCarta(boolean jogador){
		if(deck.getCartas().size() <= 0)
			throw new RuntimeException("Não há mais cartas no baralho para serem compradas.");
		Carta nova = deck.getCartas().get(0);
		deck.getCartas().remove(0);
		mao.add(nova);
		if(jogador){
			nCartasHeroi1++;
		}
		else{
			nCartasHeroi2++;
		}
		return (Carta) UnoptimizedDeepCopy.copy(nova);
	}
	
	private Carta usarCartaMao(int idCarta, boolean jogador){
		String dbg = "";
		for(int i = 0; i < mao.size(); i++){
			if( mao.get(i).getID() == idCarta){
				Carta ret = mao.get(i);
				mao.remove(i);
				if(jogador)
					nCartasHeroi1--;
				else
					nCartasHeroi2--;
				return ret;
			}
			else
				dbg = dbg + mao.get(i).getID()+ " ";
		}
		throw new RuntimeException("Tentou usar carta que nao possui na mao. ID carta=:"+idCarta+". IDs de cartas na mao= "+dbg);
	}
	
	private void processarJogada (Jogada umaJogada, boolean jogador){
		switch(umaJogada.getTipo()){
		case LACAIO:
			// Dropar um lacaio
			if(umaJogada.getCartaJogada() instanceof CartaLacaio){
				if(verbose!=0) imprimir("JOGADA: Um lacaio entrou na mesa, lacaio_id="+umaJogada.getCartaJogada().getID()+" ("+umaJogada.getCartaJogada().getNome()+")");
				baixarCartaLacaio(umaJogada, jogador);
			}
			else{
				throw new RuntimeException("Tentou usar uma jogada LACAIO, mas a cartaJogada nao era lacaio.");
			}
			break;
		case MAGIA:
			// Usar uma magia
			if(umaJogada.getCartaJogada() instanceof CartaMagia){
				if(((CartaMagia) umaJogada.getCartaJogada()).getMagiaTipo() == TipoMagia.ALVO){
					if(verbose!=0) imprimir("JOGADA: Uma magia usada de id="+umaJogada.getCartaJogada().getID()+" mirando no lacaio_id= "+(umaJogada.getCartaAlvo() == null?"Heroi":umaJogada.getCartaAlvo().getID()));
				}
				else if(((CartaMagia) umaJogada.getCartaJogada()).getMagiaTipo() == TipoMagia.AREA){
					if(verbose!=0) imprimir("JOGADA: Uma magia usada de id="+umaJogada.getCartaJogada().getID()+" de dano em area.");
				}
				else{
					if(verbose!=0) imprimir("JOGADA: Uma magia usada de id="+umaJogada.getCartaJogada().getID()+" de buff.");
				}
				usoCartaMagia(umaJogada, jogador);
			}
			else{
				throw new RuntimeException("Tentou usar uma jogada MAGIA, mas a cartaJogada nao era magia.");
			}
			break;
		case ATAQUE:
			// Ataque com lacaio
			if(umaJogada.getCartaJogada() instanceof CartaLacaio){
				if(verbose!=0) imprimir("JOGADA: Um ataque do lacaio_id ="+umaJogada.getCartaJogada().getID()+" ("+umaJogada.getCartaJogada().getNome()+") no lacaio_id ="+(umaJogada.getCartaAlvo() == null?"Heroi":umaJogada.getCartaAlvo().getID()));
				if (this.lacaiosAtacaramID.contains(new Integer(umaJogada.getCartaJogada().getID()))) {
	                throw new RuntimeException("Nao se pode atacar com um lacaio mais de uma vez por turno.");
	            }
	            this.lacaiosAtacaramID.add(new Integer(umaJogada.getCartaJogada().getID()));
				ataqueLacaio(umaJogada, jogador);
			}
			else{
				throw new RuntimeException("Tentou usar uma jogada ATAQUE, mas a cartaJogada nao era lacaio.");
			}
			break;
		case PODER:
			// Poder heróico
			if(verbose!=0) imprimir("JOGADA: Um poder heroico usado no lacaio_id="+(umaJogada.getCartaAlvo() == null?"Heroi":umaJogada.getCartaAlvo().getID()));
			 if (this.poderHeroicoUsado) {
                 throw new RuntimeException("Nao pode utilizar mais de um poder heroico por turno.");
             }
             this.poderHeroicoUsado = true;
			poderHeroico(umaJogada, jogador);
			break;
		default:
			throw new RuntimeException("JOGADA de tipo invalido: tipo="+umaJogada.getTipo());
		}
	}
	
	private void poderHeroico(Jogada jogPoder, boolean jogador){
		int alvo = (jogPoder.getCartaAlvo() == null ? -1 : jogPoder.getCartaAlvo().getID());
		if(alvo == -1)
			danoHeroi(1, jogador);
		else
		{
			int atkLacaio = danoLacaio(alvo, lacaiosOponente, 1, !jogador);
			danoHeroi(atkLacaio, !jogador);
		}
		
		if(jogador){
			manaHeroi1 -= 2;
			listaAcoesGUI.add(new AcaoGUI(TipoAcao.SET_MANA_HEROI, null, manaHeroi1, jogador));
		}
		else{
			manaHeroi2 -= 2;
			listaAcoesGUI.add(new AcaoGUI(TipoAcao.SET_MANA_HEROI, null, manaHeroi2, jogador));
		}
	}
	
	private void ataqueLacaio (Jogada jogAtaque, boolean jogador){
		int lacaioOrigem = jogAtaque.getCartaJogada().getID();
		int lacaioDestino = jogAtaque.getCartaAlvo() == null ? -1 : jogAtaque.getCartaAlvo().getID();
		
		int i, j;
		
		// Procura o lacaio e valida
		for(i = 0; i < lacaios.size(); i++)
			if(lacaios.get(i).getID() == lacaioOrigem) break;
		if( i >= lacaios.size() ) // Update: Pequena correção para mostrar mensagem.
			throw new RuntimeException("Lacaio invalido de origem do ataque. ID Lacaio="+lacaioOrigem);
		if( lacaios.get(i).getTurno() == turno )
			throw new RuntimeException("Lacaio nao pode atacar no mesmo turno que entrou na mesa. ID Lacaio="+lacaioOrigem);
		
		if(lacaioDestino == -1){
			danoHeroi(lacaios.get(i).getAtaque(), jogador);
		}
		else{
			for(j = 0; j < lacaiosOponente.size(); j++)
				if(lacaiosOponente.get(j).getID() == lacaioDestino) break;
			if( j >= lacaiosOponente.size() || lacaiosOponente.get(j).getID() != lacaioDestino)
				throw new RuntimeException("Lacaio invalido de alvo do ataque. ID Alvo="+lacaioDestino+". ID Origem="+lacaioOrigem);
			
			// Salva danos porque os objetos lacaios poderão ser deletados
			int atkLacaio1 = lacaios.get(i).getAtaque();
			int atkLacaio2 = lacaiosOponente.get(j).getAtaque();
			
			// Causa dano no lacaio oponente
			danoLacaio(lacaioDestino, lacaiosOponente, atkLacaio1, !jogador);

			// Causa dano no lacaio origem
			danoLacaio(lacaioOrigem,  lacaios, atkLacaio2, jogador);
		}
	}
	
	private void baixarCartaLacaio(Jogada jogLacaio, boolean jogador){
		if( !(jogLacaio.getCartaJogada() instanceof CartaLacaio)){
			throw new RuntimeException("Erro em baixarCartaLacaio(): Tentou baixar uma carta que nao e lacaio. ID: "+jogLacaio.getCartaJogada().getID());
		}
		CartaLacaio lacaio = (CartaLacaio) usarCartaMao(jogLacaio.getCartaJogada().getID(), jogador);
		lacaio.setTurno(turno);
		if(lacaios.size() >= 7) // RETIRADO PORQUE ESQUECI DE POR ESSA REGRA NO ENUNCIADO.
			throw new RuntimeException("Erro em baixarCartaLacaio(): Nao se pode ter mais de sete lacaios na mesa !");
		lacaios.add(lacaio);
		listaAcoesGUI.add(new AcaoGUI(TipoAcao.RETIRAR_MAO, jogLacaio.getCartaJogada(), 0, jogador));
		listaAcoesGUI.add(new AcaoGUI(TipoAcao.ADICIONAR_MESA, jogLacaio.getCartaJogada(), 0, jogador));
		if(jogador){
			manaHeroi1 -= lacaio.getMana();
			listaAcoesGUI.add(new AcaoGUI(TipoAcao.SET_MANA_HEROI, null, manaHeroi1, jogador));
		}
		else{
			manaHeroi2 -= lacaio.getMana();
			listaAcoesGUI.add(new AcaoGUI(TipoAcao.SET_MANA_HEROI, null, manaHeroi2, jogador));
		}
	}
	
	private void usoCartaMagia(Jogada jogMagia, boolean jogador){
		if( !(jogMagia.getCartaJogada() instanceof CartaMagia)){
			throw new RuntimeException("Erro em usarCartaMagia(): Tentou usar uma carta como magia, mas nao e magia. ID: "+jogMagia.getCartaJogada().getID());
		}
		CartaMagia magia = (CartaMagia) usarCartaMao(jogMagia.getCartaJogada().getID(), jogador);
		listaAcoesGUI.add(new AcaoGUI(TipoAcao.RETIRAR_MAO, jogMagia.getCartaJogada(), 0, jogador));
		if(magia.getMagiaTipo() == TipoMagia.ALVO){
			int alvo = jogMagia.getCartaAlvo() == null ? -1 : jogMagia.getCartaAlvo().getID();
			if(alvo == -1)
				danoHeroi(magia.getMagiaDano(), jogador);
			else
				danoLacaio(alvo, lacaiosOponente, magia.getMagiaDano(), !jogador);
		}
		else if(magia.getMagiaTipo() == TipoMagia.AREA){
			if(verbose!=0) imprimir("Usando a magia: "+magia.getNome()+" em area ...");
			// Copia os IDs e depois aplica os danos (não pode simplesmente percorrer o array porque entradas poderão ser deletadas - quando o lacaio morre).
			ArrayList<Integer> alvos = new ArrayList<Integer>();
			for(Carta card : lacaiosOponente)
				alvos.add(new Integer(card.getID()));
			for(Integer alvo : alvos)
				danoLacaio(alvo.intValue(), lacaiosOponente, magia.getMagiaDano(), !jogador);
			// Magias de área atingem lacaios e heroi do oponente
			danoHeroi(magia.getMagiaDano(), jogador);
		}
		else{
			// magia de buff precisa ter alvo
			if( jogMagia.getCartaAlvo() == null){
				throw new RuntimeException("Erro em usarCartaMagia(): Tentou usar carta de buff, mas sem alvo! ID buff: "+jogMagia.getCartaJogada().getID());
			}
			buffarLacaio(jogMagia.getCartaAlvo().getID(), lacaios, magia.getMagiaDano(), jogador);
		}
		if(jogador){
			manaHeroi1 -= magia.getMana();
			listaAcoesGUI.add(new AcaoGUI(TipoAcao.SET_MANA_HEROI, null, manaHeroi1, jogador));
		}
		else{
			manaHeroi2 -= magia.getMana();
			listaAcoesGUI.add(new AcaoGUI(TipoAcao.SET_MANA_HEROI, null, manaHeroi2, jogador));
		}
	}
	
	private int danoLacaio (int idAlvo, ArrayList<CartaLacaio> grupoLacaios, int dano, boolean jogador){
		String dbg = "";
		for(int i = 0; i < grupoLacaios.size(); i++){
			if( grupoLacaios.get(i).getID() == idAlvo ){
				// Este é o lacaio
				int vidaLacaio = grupoLacaios.get(i).getVidaAtual();
				int atkLacaio = grupoLacaios.get(i).getAtaque();
				if( vidaLacaio-dano > 0){
					// Lacaio sobreviveu
					grupoLacaios.get(i).setVidaAtual(vidaLacaio-dano); 
					if(verbose!=0) imprimir("Lacaio id="+idAlvo+" ("+grupoLacaios.get(i).getNome()+") sofreu dano mas esta vivo (Vida antes="+vidaLacaio+". dano="+dano+". Vida agora="+(vidaLacaio-dano)+").");
					listaAcoesGUI.add(new AcaoGUI(TipoAcao.SET_LACAIO_HP, grupoLacaios.get(i), grupoLacaios.get(i).getVidaAtual(), jogador));
				}
				else{
					// Lacaio morreu
					if(verbose!=0) imprimir("Lacaio id="+idAlvo+" ("+grupoLacaios.get(i).getNome()+") sofreu dano e morreu. (Vida antes="+vidaLacaio+". dano="+dano+").");
					listaAcoesGUI.add(new AcaoGUI(TipoAcao.RETIRAR_MESA, grupoLacaios.get(i), 0, jogador));
					grupoLacaios.remove(i);
				}
				return atkLacaio;
			}
			else
				dbg = dbg + grupoLacaios.get(i).getID() + " ";
		}
		throw new RuntimeException("Tentativa de causar dano em lacaio invalido. ID Lacaio alvo="+idAlvo+". Alvos validos: "+dbg);
	}
	
	private void danoHeroi (int dano, boolean jogador){
		if(jogador){
			vidaHeroi2 -= dano;
			listaAcoesGUI.add(new AcaoGUI(TipoAcao.SET_HP_HEROI, null, vidaHeroi2, false));
			if(verbose!=0) imprimir("O heroi 2 tomou "+dano+" de dano (vida restante: "+vidaHeroi2+").");
		}
		else{
			vidaHeroi1 -= dano;
			listaAcoesGUI.add(new AcaoGUI(TipoAcao.SET_HP_HEROI, null, vidaHeroi1, true));
			if(verbose!=0) imprimir("O heroi 1 tomou "+dano+" de dano (vida restante: "+vidaHeroi1+").");
		}
	}
	
	private int buffarLacaio (int idAlvo, ArrayList<CartaLacaio> grupoLacaios, int valorBuff, boolean jogador){
		String dbg = "";
		for(int i = 0; i < grupoLacaios.size(); i++){
			if( grupoLacaios.get(i).getID() == idAlvo ){
				// Este é o lacaio
				int vidaLacaio = grupoLacaios.get(i).getVidaAtual()+valorBuff;
				int atkLacaio =  grupoLacaios.get(i).getAtaque()+valorBuff;
				grupoLacaios.get(i).setVidaAtual(vidaLacaio); 
				grupoLacaios.get(i).setAtaque(atkLacaio); 
				int vidaMax = grupoLacaios.get(i).getVidaMaxima()+valorBuff; // altera a vida maxima tambem, mesmo esse atributo não sendo usado nessa versão do motor ainda! (Acho que só será utilizada se implementarmos a mecanica de cura algum dia)
				grupoLacaios.get(i).setVidaMaxima(vidaMax); 
				if(verbose!=0) imprimir("Lacaio id="+idAlvo+" ("+grupoLacaios.get(i).getNome()+") buffado +"+valorBuff+"/+"+valorBuff+" (antes="+(atkLacaio-valorBuff)+"/"+(vidaLacaio-valorBuff)+". buff=+"+valorBuff+"+/"+valorBuff+". Agora="+atkLacaio+"/"+vidaLacaio+").");
				listaAcoesGUI.add(new AcaoGUI(TipoAcao.SET_LACAIO_BUFFATTACK, grupoLacaios.get(i), grupoLacaios.get(i).getVidaAtual(), jogador));
				return atkLacaio;
			}
			else
				dbg = dbg + grupoLacaios.get(i).getID() + " ";
		}
		throw new RuntimeException("Tentativa de buffar em lacaio invalido. ID Lacaio alvo="+idAlvo+". Alvos validos: "+dbg);
	}
	
	
	/**
	  * Devolve o Baralho padrão para o jogo LaMa (Lacaios & Magias).
	  * 
	  * @param player   Um identificador para dizer se este é o baralho para o primeiro jogador (player=0) ou o segundo jogador (player=1) para a criação de IDs únicos em cada baralho.
	  * @return            retorna um ArrayList<Carta> contendo todas as cartas de um baralho padrão do LaMa - um total de 30 cartas (22 Lacaios e 8 Magias).
	  */
	public static ArrayList<Carta> gerarListaCartasPadrao (int player){
		ArrayList<Carta> retorno = new ArrayList<Carta>();
		Carta c1 = new CartaLacaio(player*100+0,    "Gnomo",				1, 2, 1, 1, TipoEfeito.NADA, -1); // [1] (2/1)
		Carta c2 = new CartaLacaio(player*100+2,    "Guerreiro Orc",		2, 3, 2, 2, TipoEfeito.NADA, -1); // [2] (3/2)
		Carta c3 = new CartaLacaio(player*100+4,    "Guerreiro Espadachim",	2, 2, 3, 3, TipoEfeito.NADA, -1); // [2] (2/3)
		Carta c4 = new CartaLacaio(player*100+6,    "Mestre Orc",			3, 4, 2, 2, TipoEfeito.NADA, -1); // [3] (4/2)
		Carta c5 = new CartaLacaio(player*100+8,    "Filhote de Dragão",	3, 3, 4, 4, TipoEfeito.NADA, -1); // [3] (3/4)
		Carta c6 = new CartaLacaio(player*100+10,   "Cavaleiro",			3, 3, 1, 1, TipoEfeito.NADA, -1); // [3] (3/1) INVESTIDA
		Carta c7 = new CartaLacaio(player*100+12,   "Gigante de Pedra",		4, 4, 5, 5, TipoEfeito.NADA, -1); // [4] (4/5)
		Carta c8 = new CartaLacaio(player*100+14,   "Arqueira Experiente",	4, 3, 5, 5, TipoEfeito.NADA, -1); // [4] (3/5) FURIA DOS VENTOS
		Carta c9 = new CartaLacaio(player*100+16,   "Mestre Espadachim",   	5, 3, 9, 9, TipoEfeito.NADA, -1); // [5] (3/9) PROVOCAR
		Carta c10 = new CartaLacaio(player*100+18,  "Mestre Mago",          5, 7, 3, 3, TipoEfeito.NADA, -1); // [5] (7/3)
		Carta c11 = new CartaLacaio(player*100+20,  "Dragão",               7, 7, 7, 7, TipoEfeito.NADA, -1); // [7] (7/7)
		
		Carta z1 = new CartaMagia(player*100+22,   "Rajada Congelante",    	3, TipoMagia.ALVO, 3); // [2] 3 dano no alvo
		Carta z2 = new CartaMagia(player*100+24,   "Raio",					5, TipoMagia.ALVO, 7); // [5] 7 dano no alvo
		Carta z3 = new CartaMagia(player*100+26,   "Benção dos Deuses",		2, TipoMagia.BUFF, 2); // [2] +2/+2 para um lacaio (+2 ataque, +2 vida)
		Carta z4 = new CartaMagia(player*100+28,   "Mininova",				7, TipoMagia.AREA, 4); // [7] 4 dano em area
		
		Carta c1_2 = new CartaLacaio(player*100+1,  "Gnomo",				1, 2, 1, 1, TipoEfeito.NADA, -1);
		Carta c2_2 = new CartaLacaio(player*100+3,  "Guerreiro Orc",		2, 3, 2, 2, TipoEfeito.NADA, -1);
		Carta c3_2 = new CartaLacaio(player*100+5,  "Guerreiro Espadachim",	2, 2, 3, 3, TipoEfeito.NADA, -1);
		Carta c4_2 = new CartaLacaio(player*100+7,  "Mestre Orc",			3, 4, 2, 2, TipoEfeito.NADA, -1);
		Carta c5_2 = new CartaLacaio(player*100+9,  "Filhote de Dragão",	3, 3, 4, 4, TipoEfeito.NADA, -1);
		Carta c6_2 = new CartaLacaio(player*100+11, "Cavaleiro",			3, 3, 1, 1, TipoEfeito.NADA, -1);
		Carta c7_2 = new CartaLacaio(player*100+13, "Gigante de Pedra",		4, 4, 5, 5, TipoEfeito.NADA, -1);
		Carta c8_2 = new CartaLacaio(player*100+15, "Arqueira Experiente",	4, 3, 5, 5, TipoEfeito.NADA, -1);
		Carta c9_2 = new CartaLacaio(player*100+17, "Mestre Espadachim",   	5, 3, 9, 9, TipoEfeito.NADA, -1);
		Carta c10_2 = new CartaLacaio(player*100+19,"Mestre Mago",          5, 7, 3, 3, TipoEfeito.NADA, -1);
		Carta c11_2 = new CartaLacaio(player*100+21,"Dragão",               7, 7, 7, 7, TipoEfeito.NADA, -1);
		
		Carta z1_2 = new CartaMagia(player*100+23, "Rajada Congelante",    	3, TipoMagia.ALVO, 3);
		Carta z2_2 = new CartaMagia(player*100+25, "Raio",					5, TipoMagia.ALVO, 7);
		Carta z3_2 = new CartaMagia(player*100+27, "Benção dos Deuses",		2, TipoMagia.BUFF, 2);
		Carta z4_2 = new CartaMagia(player*100+29, "Mininova",				7, TipoMagia.AREA, 4);
		
		
		retorno.add(c1);
		retorno.add(c2);
		retorno.add(c3);
		retorno.add(c4);
		retorno.add(c5);
		retorno.add(c6);
		retorno.add(c7);
		retorno.add(c8);
		retorno.add(c9);
		retorno.add(c10);
		retorno.add(c11);
		retorno.add(z1);
		retorno.add(z2);
		retorno.add(z3);
		retorno.add(z4);
		
		retorno.add(c1_2);
		retorno.add(c2_2);
		retorno.add(c3_2);
		retorno.add(c4_2);
		retorno.add(c5_2);
		retorno.add(c6_2);
		retorno.add(c7_2);
		retorno.add(c8_2);
		retorno.add(c9_2);
		retorno.add(c10_2);
		retorno.add(c11_2);
		retorno.add(z1_2);
		retorno.add(z2_2);
		retorno.add(z3_2);
		retorno.add(z4_2);
		
		return retorno;
	}

}