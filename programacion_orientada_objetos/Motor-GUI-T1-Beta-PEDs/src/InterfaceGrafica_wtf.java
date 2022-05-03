import java.awt.Color;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.ScrollPaneConstants;

/**
* Essa classe é a Interface Gráfica (GUI) para o jogo LaMa (Lacaios & Magias).
* @see java.lang.Object
* @author Rafael Arakaki - MC302
*/
public class InterfaceGrafica implements InterfaceGUI {

	JFrame frmTituloDeTeste;
    JTextPane textPane;
    boolean atacou = false;
    final String BASE_RESOURCE_PATH = "./resources/";
    ArrayList<Object> listaAcoesMemory; // Salva as ações no objeto
    private int acoesCounter = 0; // Um indice para salvar quais ações já foram processadas pela GUI e quais ainda irão ser processadas
	
    JLabel lblCartas1;
    JLabel lblCartas2;
    JButton btnClearMessages;
    JButton btnAtacar2;    
    JButton btnAlvo2;
    JLabel handLabels[] = new JLabel[HAND_SIZE];
    JLabel handOpoLabels[] = new JLabel[HAND_SIZE];
    JLabel lacaioLabels[] = new JLabel[FIELD_SIZE];
    JLabel lacaioOpoLabels[] = new JLabel[FIELD_SIZE];
    
    // Status de mensagem por lacaio
    JLabel msg1;
    JLabel msg2;
    JLabel msg3;
    JLabel msg4;
    JLabel msg5;
    JLabel msg6;
    JLabel msg7;
    
    // Botão de atacar e alvo por lacaio (funcionalidade não utilizada no momento)
    JButton btnAlvo3;
    JButton btnAtacar3;
    JButton btnAlvo4;
    JButton btnAtacar4;
    JButton btnAlvo5;
    JButton btnAtacar5;
    JButton btnAlvo6;
    JButton btnAtacar6;
    JButton btnAlvo7;
    JButton btnAtacar7;
    
    JButton btnNext;
    JLabel lblTurno;
    
    // Informacoes de mana e vida dos herois
    JLabel mana_1;
    JLabel vida_2;
    JLabel vida_1;
    JLabel mana_2;
    
    static final private int HAND_SIZE = 7;
    static final private int FIELD_SIZE = 7;
    
    private int[] hand = new int[HAND_SIZE];
    private int[] handOpo = new int[HAND_SIZE];
    private int[] lacaios = new int[FIELD_SIZE];
    private int[] lacaiosOpo = new int[FIELD_SIZE];
    private boolean[] lacaiosBuff = new boolean[FIELD_SIZE];
    private boolean[] lacaiosOpoBuff = new boolean[FIELD_SIZE];
    
    private int manaAtual1, manaAtual2, maxMana1, maxMana2;
    
    ArrayList<Carta> cartasBuffadas = new ArrayList<Carta>();
    
    private boolean inverte_campo;
    
	/**
     * Create the application.
     */
    public InterfaceGrafica(boolean campo_invertido) {
    	inverte_campo = campo_invertido;
        initialize();
        for(int i = 0; i < HAND_SIZE; i++){
        	hand[i] = -1;
        	handOpo[i] = -1;
        }
        for(int i = 0; i < FIELD_SIZE; i++){
        	lacaios[i] = -1;
        	lacaiosOpo[i] = -1;
        	lacaiosBuff[i] = lacaiosOpoBuff[i] = false;
        	manaAtual1 = manaAtual2 = maxMana1 = maxMana2 = 0;
        }
    }
	
	@Override
	public void processarAcoesGUI(ArrayList<Object> listaAcoes) {
		// Processa as ações recebidas por callback
		//System.out.println("**** PROCESSANDO ACOES GUI ****\n");
		int strings = 0, acoes = 0, jogadas = 0;
		for(int i = 0; i < listaAcoes.size(); i++){
			Object obj = listaAcoes.get(i);
			if( obj instanceof String){
				////System.out.println("strign detected: "+ (String) obj);
                //textPane.setText(textPane.getText() + (String) obj + "\n");
                strings++;
			}
			else if (obj instanceof Jogada){
				jogadas++;
			}
			else if( obj instanceof AcaoGUI){
				if( inverte_campo ){
					// inverte todos os campos nesse momento
					((AcaoGUI) obj).setJogador(!((AcaoGUI) obj).isJogador());
				}
				acoes++;
			}
			else{
				throw new RuntimeException("processarAcoesGui(): Objeto de classe desconhecida no ArrayList<Object> listaAcoes.");
			}
		}
		//System.out.println("size: "+listaAcoes.size());
		//System.out.println("jogadas= "+jogadas+", acoes="+acoes+", strings="+strings);
		// Habilita botao next
		listaAcoesMemory = listaAcoes;
		acoesCounter = 0;
		btnNext.setText("Next");
		processarNextAcoes(false);
		btnNext.setEnabled(true);
		while(btnNext.isEnabled()){
			try {
				//System.out.println("waiting...\n");
				Thread.sleep(300);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				throw new RuntimeException("processarAcoesGui(): Erro ao aguardar por sleep a acao do usuario.");
				//e.printStackTrace();
			}
		}
		//System.out.println("Sai da espera do usuario\n");
		return;
	}
	
	// Processa ações de uma jogada e aguarda o pressionamento do próximo botão next
	// Usa acoesCounter e listaAcoesMemory.
	// processaJogadaOuNao == false, se você quer apenas processar as ações mas não a jogada (usado no inico do turno pra atualizar cartas da mao, mana, etc)
	// processaJogadaOuNao == true, se você quer processar uma jogada
	private void processarNextAcoes(boolean processaProximaJogada){
		//int j = listaAcoesMemory.size()-1;
		if( btnNext.getText() == "Next"){
			boolean processouUmaJogada = !processaProximaJogada;
			boolean processouFimTurno = false;
			boolean processouFimPartida = false;
			int iter;
			for(iter = acoesCounter; iter < listaAcoesMemory.size(); iter++){
				// Se a ação for uma Jogada pela segunda vez, para antes de processá-la
				if( listaAcoesMemory.get(iter) instanceof Jogada && processouUmaJogada == true ){
					break;
				}
				// Processa a ação normalmente
				else {
					Object obj = listaAcoesMemory.get(iter);
					if( obj instanceof String){
		                textPane.setText(textPane.getText() + (String) obj + "\n");
					}
					else if (obj instanceof Jogada){
						processouUmaJogada = true;
		                //textPane.setText(textPane.getText() +  "processamos uma Jogada\n");
					}
					else if( obj instanceof AcaoGUI){
		                //textPane.setText(textPane.getText() +  "processamos uma Ação\n");
		                AcaoGUI acao = (AcaoGUI) obj;
		                switch(acao.getTipo()){
						case ADICIONAR_MAO:
							//System.out.println("AddMao. ID da carta:" + acao.getCartaAlvo().getID() + ". nome= " + acao.getCartaAlvo().getNome() );
							if(acao.isJogador()){
								for(int j =0; j < HAND_SIZE; j++){
									if(hand[j] == -1){
										hand[j] = acao.getCartaAlvo().getID();
										//System.out.println("Mostrar carta em hand "+j+" IDnovo=" + acao.getCartaAlvo().getID());
										if( acao.getCartaAlvo() instanceof CartaLacaio)
											mudarIcone(handLabels[j], acao.getCartaAlvo(), ((CartaLacaio) acao.getCartaAlvo()).getVidaMaxima(), false);
										else
											mudarIcone(handLabels[j], acao.getCartaAlvo(), 0, false);
										break;
									}
								}
							}
							else{
								for(int j =0; j < HAND_SIZE; j++){
									if(handOpo[j] == -1){
										handOpo[j] = acao.getCartaAlvo().getID();
										//System.out.println("Mostrar carta em handOpo "+j+" IDnovo=" + acao.getCartaAlvo().getID());
										if( acao.getCartaAlvo() instanceof CartaLacaio)
											mudarIcone(handOpoLabels[j], acao.getCartaAlvo(), ((CartaLacaio) acao.getCartaAlvo()).getVidaMaxima(), false);
										else
											mudarIcone(handOpoLabels[j], acao.getCartaAlvo(), 0, false);
										break;
									}
								}
							}
							break;
						case ADICIONAR_MESA:
							//System.out.println("AddMesa. ID da carta:" + acao.getCartaAlvo().getID() + ". nome= " + acao.getCartaAlvo().getNome() );
							if(acao.isJogador()){
								for(int j =0; j < FIELD_SIZE; j++){
									if(lacaios[j] == -1){
										lacaios[j] = acao.getCartaAlvo().getID();
										//System.out.println("Mostrar carta em lacaios "+j+" IDnovo=" + acao.getCartaAlvo().getID());
										if( acao.getCartaAlvo() instanceof CartaLacaio)
											mudarIcone(lacaioLabels[j], acao.getCartaAlvo(), ((CartaLacaio) acao.getCartaAlvo()).getVidaMaxima(), false);
										else
											throw new RuntimeException("processarNextAcoes(): Erro ao tentar adicionar carta na mesa que nao eh lacaio. ID:"+acao.getCartaAlvo().getID());
										lacaiosBuff[j] = false;
										break;
									}
								}
							}
							else{
								for(int j =0; j < FIELD_SIZE; j++){
									if(lacaiosOpo[j] == -1){
										lacaiosOpo[j] = acao.getCartaAlvo().getID();
										//System.out.println("Mostrar carta em lacaiosOpo "+j+" IDnovo=" + acao.getCartaAlvo().getID());
										if( acao.getCartaAlvo() instanceof CartaLacaio)
											mudarIcone(lacaioOpoLabels[j], acao.getCartaAlvo(), ((CartaLacaio) acao.getCartaAlvo()).getVidaMaxima(), false);
										else
											throw new RuntimeException("processarNextAcoes(): Erro ao tentar adicionar carta na mesa que nao eh lacaio. ID:"+acao.getCartaAlvo().getID());
										lacaiosOpoBuff[j] = false;
										break;
									}
								}
							}
							break;
						case FIM_PARTIDA:
							processouFimPartida = true;
							if(acao.isJogador())
								JOptionPane.showMessageDialog(null, "O jogador de baixo venceu o jogo !");
							else
								JOptionPane.showMessageDialog(null, "O jogador de cima venceu o jogo !");
							break;
						case FIM_TURNO:
							processouFimTurno = true;
							break;
						case INICIO_PARTIDA:
							if(acao.isJogador())
								JOptionPane.showMessageDialog(null, "Uma partida LaMa será iniciada !");
							else
								JOptionPane.showMessageDialog(null, "Uma partida LaMa será iniciada !");
							break;
						case INICIO_TURNO:
							if(acao.isJogador()){
								lblTurno.setText("TURNO: "+acao.getArg());
								lblTurno.setForeground(Color.BLUE);
							}
							else{
								lblTurno.setText("TURNO: "+acao.getArg());
								lblTurno.setForeground(Color.RED);
							}
							break;
						case RETIRAR_MAO:
							//System.out.println("RetiraMao. ID da carta:" + acao.getCartaAlvo().getID() + ". nome= " + acao.getCartaAlvo().getNome() );
							if(acao.isJogador()){
								for(int j =0; j < HAND_SIZE; j++){
									if(hand[j] == acao.getCartaAlvo().getID()){
										hand[j] = -1;
										//System.out.println("Retira carta em hand "+j+" ID=" + acao.getCartaAlvo().getID());
										mudarIcone(handLabels[j], null, 0, false); // (null, 0) significa torne aquele espaço invisivel | (null, 1) significa coloque backcard.png naquele espaço
										break;
									}
								}
							}
							else{
								for(int j =0; j < HAND_SIZE; j++){
									if(handOpo[j] == acao.getCartaAlvo().getID()){
										handOpo[j] = -1;
										//System.out.println("Retirar carta em handOpo "+j+" ID" + acao.getCartaAlvo().getID());
										mudarIcone(handOpoLabels[j], null, 0, false);
										break;
									}
								}
							}
							break;
						case RETIRAR_MESA:
							//System.out.println("RetiraMesa. ID da carta:" + acao.getCartaAlvo().getID() + ". nome= " + acao.getCartaAlvo().getNome() );
							if(acao.isJogador()){
								for(int j =0; j < FIELD_SIZE; j++){
									if(lacaios[j] == acao.getCartaAlvo().getID()){
										lacaios[j] = -1;
										//System.out.println("Retira carta em lacaios "+j+" ID=" + acao.getCartaAlvo().getID());
										mudarIcone(lacaioLabels[j], null, 0, false); // (null, 0) significa torne aquele espaço invisivel | (null, 1) significa coloque backcard.png naquele espaço
										break;
									}
								}
							}
							else{
								for(int j =0; j < FIELD_SIZE; j++){
									if(lacaiosOpo[j] == acao.getCartaAlvo().getID()){
										lacaiosOpo[j] = -1;
										//System.out.println("Retirar carta em lacaiosOpo "+j+" ID" + acao.getCartaAlvo().getID());
										mudarIcone(lacaioOpoLabels[j], null, 0, false);
										break;
									}
								}
							}
							break;
						case SET_HP_HEROI:
							if(acao.isJogador()){
								vida_1.setText("Vida: "+acao.getArg());
								if(acao.getArg() <= 15)
									vida_1.setForeground(Color.red);
							}
							else{
								vida_2.setText("Vida: "+acao.getArg());
								if(acao.getArg() <= 15)
									vida_2.setForeground(Color.red);
							}
							break;
						case SET_LACAIO_BUFFATTACK:
							// verifica se e'buffado procurando em uma pequena memoria
							if(acao.isJogador()){
								for(int j =0; j < FIELD_SIZE; j++){
									if(lacaios[j] == acao.getCartaAlvo().getID()){
										//System.out.println("Buffado em lacaios "+j+" ID=" + acao.getCartaAlvo().getID());
										//lacaiosBuff[j] = true; // TODO: Descomentar essas linhas quando as imagens de cartas buffadas estiverem prontas!
										//mudarIcone(lacaioLabels[j], acao.getCartaAlvo(), acao.getArg(), lacaiosBuff[j]);
										break;
									}
								}
							}
							else{
								for(int j =0; j < FIELD_SIZE; j++){
									if(lacaiosOpo[j] == acao.getCartaAlvo().getID()){
										//System.out.println("Buffado em lacaiosOpo "+j+" ID=" + acao.getCartaAlvo().getID());
										//lacaiosOpoBuff[j] = true;
										//mudarIcone(lacaioOpoLabels[j], acao.getCartaAlvo(), acao.getArg(), lacaiosOpoBuff[j]);
										break;
									}
								}
							}
							break;
						case SET_LACAIO_HP:
							// verifica se e'buffado procurando em uma pequena memoria
							if(acao.isJogador()){
								for(int j =0; j < FIELD_SIZE; j++){
									if(lacaios[j] == acao.getCartaAlvo().getID()){
										//System.out.println("Atualiza HP em lacaios "+j+" ID=" + acao.getCartaAlvo().getID());
										mudarIcone(lacaioLabels[j], acao.getCartaAlvo(), acao.getArg(), lacaiosBuff[j]);
										break;
									}
								}
							}
							else{
								for(int j =0; j < FIELD_SIZE; j++){
									if(lacaiosOpo[j] == acao.getCartaAlvo().getID()){
										//System.out.println("Atualiza HP em lacaiosOpo "+j+" ID=" + acao.getCartaAlvo().getID());
										mudarIcone(lacaioOpoLabels[j], acao.getCartaAlvo(), acao.getArg(), lacaiosOpoBuff[j]);
										break;
									}
								}
							}
							break;
						case SET_MANA_HEROI:
							if(acao.isJogador()){
								manaAtual1 = acao.getArg();
								mana_1.setText("Mana: "+manaAtual1+"/"+maxMana1);
							}
							else{
								manaAtual2 = acao.getArg();
								mana_2.setText("Mana: "+manaAtual2+"/"+maxMana2);
							}
							break;
						case SET_MAXMANA_HEROI:
							if(acao.isJogador()){
								maxMana1 = acao.getArg();
								mana_1.setText("Mana: "+manaAtual1+"/"+maxMana1);
							}
							else{
								maxMana2 = acao.getArg();
								mana_2.setText("Mana: "+manaAtual2+"/"+maxMana2);
							}
							break;
						case SET_NUM_BARALHO:
							if(acao.isJogador()){
								lblCartas1.setText("Cartas: "+acao.getArg());
								if( acao.getArg() <= 5)
									lblCartas1.setText("<html><span bgcolor=\"yellow\">Cartas: "+acao.getArg()+"</span></html>");
							}
							else{
								lblCartas2.setText("Cartas: "+acao.getArg());
								if( acao.getArg() <= 5)
									lblCartas2.setText("<html><span bgcolor=\"yellow\">Cartas: "+acao.getArg()+"</span></html>");
							}
							break;
						default:
							break;
		                	
		                }
					}
					else{
						throw new RuntimeException("processarNextAcoes(): Objeto de classe desconhecida no ArrayList<Object> listaAcoes.");
					}
				}
			}
			acoesCounter = iter;
			if( processouFimPartida ){
				btnNext.setText("Encerrado");
				btnNext.setEnabled(false);
			}
			else if( processouFimTurno ){
				btnNext.setText("Fim Turno");
				btnNext.setEnabled(true);
			}
		}
		else if( btnNext.getText() == "Fim Turno" ){
			btnNext.setEnabled(false);
		}
	}
	
	// O Motor é responsável por sempre informar quanto tem de HP o lacaio nas ações ADICIONAR_MAO, ADICIONAR_MESA, SET_LACAIO_HP e SET_LACAIO_BUFFATTACK (campo 'arg').
	void mudarIcone(JLabel label, Carta card, int hp, boolean buffed){
		BufferedImage img = null;
		ImageIcon imageIcon = new ImageIcon();
		String nome = new String();
		
		if( card != null){
			int ID = card.getID();
			if( ID < 100) ID += 100;
			if( ID % 2 == 1) ID -= 1;
			
			if( card instanceof CartaMagia){
				nome = "Magia" + ID + ".png";
			}
			else{
				if( !buffed ){
					nome = "Lacaio" + ID + "_hp" + hp + ".png";
				}
				else{
					nome = "Lacaio" + ID + "_hp" + hp + "_buff.png";
				}
			}

	        try {
	        	//System.out.println("Tentando ler o nome: "+nome+". width: " +handLabels[0].getWidth()+" height: " +handLabels[0].getHeight()+ "");
	            img = ImageIO.read(new File(BASE_RESOURCE_PATH + nome));
	            img.getScaledInstance(handLabels[0].getWidth(), handLabels[0].getHeight(), Image.SCALE_SMOOTH); //lblNewLabel_1.getWidth(), lblNewLabel_1.getHeight()
	            BufferedImage dimg = new BufferedImage(handLabels[0].getWidth(), handLabels[0].getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
	            dimg.getGraphics().drawImage(img, 0, 0, handLabels[0].getWidth(), handLabels[0].getHeight(), null);
	            imageIcon = new ImageIcon(dimg);
	            label.setIcon(imageIcon);
	            label.setVisible(true);
	        }
	        catch (IOException e) {
	            e.printStackTrace();
	        }
		}
		// Coloca o JLabel como invisivel ou carta virada pra baixo
		else{
			if( hp == 0 ){
				// torna o JLabel invisivel
				label.setVisible(false);
			}
			else{
				// torna o JLabel com o 'back' da carta.
	            try {
	            	img = ImageIO.read(new File(BASE_RESOURCE_PATH + "back2.png"));
		            img.getScaledInstance(handLabels[0].getWidth(), handLabels[0].getHeight(), Image.SCALE_SMOOTH); //lblNewLabel_1.getWidth(), lblNewLabel_1.getHeight()
		            BufferedImage dimg = new BufferedImage(handLabels[0].getWidth(), handLabels[0].getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
		            dimg.getGraphics().drawImage(img, 0, 0, handLabels[0].getWidth(), handLabels[0].getHeight(), null);
		            imageIcon = new ImageIcon(dimg);
		            label.setIcon(imageIcon);
		            label.setVisible(true);
		        }
		        catch (IOException e) {
		            e.printStackTrace();
		        }
			}
		}
	}

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frmTituloDeTeste = new JFrame();
        frmTituloDeTeste.setFont(new Font("DejaVu Sans", Font.PLAIN, 14));
        frmTituloDeTeste.setTitle("LaMa: Lacaios e Magias");
        frmTituloDeTeste.setBounds(100, 100, 1600, 1000);
        frmTituloDeTeste.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmTituloDeTeste.getContentPane().setLayout(null);
        //frmTituloDeTeste.getContentPane().setLayout(null);
        
        // Panel global que irá conter tudo
        JPanel globalPanel = new JPanel();

        // JScrollPane global que irá conter o panel que contém tudo (com a vantagem de ter scrolls!)
        JScrollPane scrollGrande = new JScrollPane(globalPanel);
        scrollGrande.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollGrande.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        //scrollGrande.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        //scrollGrande.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollGrande.setBounds(0, 0, 800, 600);
        scrollGrande.setLayout(null);
        globalPanel.setLayout(null);

        // Scroll pane para caixa de texto
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(5, 5, 9, 24);
        //frmTituloDeTeste.getContentPane().add(scrollPane);
        globalPanel.add(scrollPane);
        
        // Tudo daqui pra baixo deverá ser adicionado direto ao 'globalPanel', que por fim, adicionamos o 'scrollGrande', que contém o 'globalPanel' ao frame.
        
        // Iniciando caixa de texto
        textPane = new JTextPane();
        scrollPane.setViewportView(textPane);

        // Memory
        atacou = false;
        
                JButton btnAtacar1 = new JButton("Atacar");
                btnAtacar1.setEnabled(false);
                btnAtacar1.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent arg0) {
                        if (msg1.getText() == "zzz") {
                            JOptionPane.showMessageDialog(null, "Esse lacaio não pode atacar neste turno.");
                            return;
                        }
                        msg1.setText("Atacando");
                        msg1.setBackground(new Color(100, 100, 0));
                        textPane.setText(textPane.getText() + "LOL\n");
                        if (atacou) {
                            msg1.setForeground(new Color(255, 0, 0)); //red
                            msg1.setText("Atacou");
                        }
                        atacou = true;

                    }
                });
                btnAtacar1.setBounds(12, 499, 117, 25);
                frmTituloDeTeste.getContentPane().add(btnAtacar1);

        msg1 = new JLabel("New label");
        msg1.setHorizontalAlignment(SwingConstants.CENTER);
        msg1.setBounds(12, 484, 127, 15);
        frmTituloDeTeste.getContentPane().add(msg1);
        msg1.setText("");

        handLabels[0] = new JLabel("");

        //lblNewLabel_1.setIcon(new ImageIcon(BASE_RESOURCE_PATH + "drboom.png"));
        handLabels[0].setBounds(9, 755, 150, 206);

        BufferedImage img = null;
        ImageIcon imageIcon = new ImageIcon();
        ImageIcon imageIcon2 = new ImageIcon();
        ImageIcon imageIcon3 = new ImageIcon();
        ImageIcon backIcon = new ImageIcon();

        try {
            img = ImageIO.read(new File(BASE_RESOURCE_PATH + "warrior_0.png"));
            img.getScaledInstance(handLabels[0].getWidth(), handLabels[0].getHeight(), Image.SCALE_SMOOTH); //lblNewLabel_1.getWidth(), lblNewLabel_1.getHeight()
            BufferedImage dimg = new BufferedImage(handLabels[0].getWidth(), handLabels[0].getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
            dimg.getGraphics().drawImage(img, 0, 0, handLabels[0].getWidth(), handLabels[0].getHeight(), null);
            imageIcon = new ImageIcon(dimg);

            //image 2
            img = ImageIO.read(new File(BASE_RESOURCE_PATH + "warrior_1.png"));
            img.getScaledInstance(handLabels[0].getWidth(), handLabels[0].getHeight(), Image.SCALE_SMOOTH); //lblNewLabel_1.getWidth(), lblNewLabel_1.getHeight()
            BufferedImage dimg2 = new BufferedImage(handLabels[0].getWidth(), handLabels[0].getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
            dimg2.getGraphics().drawImage(img, 0, 0, handLabels[0].getWidth(), handLabels[0].getHeight(), null);
            imageIcon2 = new ImageIcon(dimg2);

            //image 3
            img = ImageIO.read(new File(BASE_RESOURCE_PATH + "drboom.png"));
            img.getScaledInstance(handLabels[0].getWidth(), handLabels[0].getHeight(), Image.SCALE_SMOOTH); //lblNewLabel_1.getWidth(), lblNewLabel_1.getHeight()
            BufferedImage dimg3 = new BufferedImage(handLabels[0].getWidth(), handLabels[0].getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
            dimg3.getGraphics().drawImage(img, 0, 0, handLabels[0].getWidth(), handLabels[0].getHeight(), null);
            imageIcon3 = new ImageIcon(dimg3);

            //back
            img = ImageIO.read(new File(BASE_RESOURCE_PATH + "back2.png"));
            img.getScaledInstance(handLabels[0].getWidth(), handLabels[0].getHeight(), Image.SCALE_SMOOTH); //lblNewLabel_1.getWidth(), lblNewLabel_1.getHeight()
            BufferedImage dimg4 = new BufferedImage(handLabels[0].getWidth(), handLabels[0].getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
            dimg4.getGraphics().drawImage(img, 0, 0, handLabels[0].getWidth(), handLabels[0].getHeight(), null);
            backIcon = new ImageIcon(dimg4);

        } catch (IOException e) {
            e.printStackTrace();
        }
        //frmTituloDeTeste.getContentPane().add(handLabels[0]);
        globalPanel.add(handLabels[0]);
        
        // Cartas na mão
        handLabels[0].setIcon(backIcon);
        
        handLabels[1] = new JLabel("");
        handLabels[1].setBounds(170, 755, 150, 206);
        handLabels[1].setIcon(backIcon);
        frmTituloDeTeste.getContentPane().add(handLabels[1]);

        handLabels[2] = new JLabel("");
        handLabels[2].setBounds(332, 755, 150, 206);
        handLabels[2].setIcon(backIcon);
        frmTituloDeTeste.getContentPane().add(handLabels[2]);

        handLabels[3] = new JLabel("");
        handLabels[3].setBounds(494, 755, 150, 206);
        handLabels[3].setIcon(backIcon);
        frmTituloDeTeste.getContentPane().add(handLabels[3]);

        handLabels[4] = new JLabel("");
        handLabels[4].setBounds(656, 755, 150, 206);
        handLabels[4].setIcon(backIcon);
        frmTituloDeTeste.getContentPane().add(handLabels[4]);

        handLabels[5] = new JLabel("");
        handLabels[5].setBounds(818, 755, 150, 206);
        handLabels[5].setIcon(backIcon);
        frmTituloDeTeste.getContentPane().add(handLabels[5]);

        handLabels[6] = new JLabel("");
        handLabels[6].setBounds(980, 755, 150, 206);
        handLabels[6].setIcon(backIcon);
        frmTituloDeTeste.getContentPane().add(handLabels[6]);

        handOpoLabels[0] = new JLabel("");
        handOpoLabels[0].setBounds(12, 12, 150, 206);
        handOpoLabels[0].setIcon(backIcon);
        frmTituloDeTeste.getContentPane().add(handOpoLabels[0]);

        handOpoLabels[1] = new JLabel("");
        handOpoLabels[1].setBounds(171, 12, 150, 206);
        handOpoLabels[1].setIcon(backIcon);
        frmTituloDeTeste.getContentPane().add(handOpoLabels[1]);

        handOpoLabels[2] = new JLabel("");
        handOpoLabels[2].setBounds(332, 12, 150, 206);
        handOpoLabels[2].setIcon(backIcon);
        frmTituloDeTeste.getContentPane().add(handOpoLabels[2]);

        handOpoLabels[3] = new JLabel("");
        handOpoLabels[3].setBounds(494, 12, 150, 206);
        handOpoLabels[3].setIcon(backIcon);
        frmTituloDeTeste.getContentPane().add(handOpoLabels[3]);

        handOpoLabels[4] = new JLabel("");
        handOpoLabels[4].setBounds(656, 12, 150, 206);
        handOpoLabels[4].setIcon(backIcon);
        frmTituloDeTeste.getContentPane().add(handOpoLabels[4]);

        handOpoLabels[5] = new JLabel("");
        handOpoLabels[5].setBounds(818, 12, 150, 206);
        handOpoLabels[5].setIcon(backIcon);
        frmTituloDeTeste.getContentPane().add(handOpoLabels[5]);

        handOpoLabels[6] = new JLabel("");
        handOpoLabels[6].setBounds(980, 12, 150, 206);
        handOpoLabels[6].setIcon(backIcon);
        frmTituloDeTeste.getContentPane().add(handOpoLabels[6]);
        
        for(int i = 0; i < FIELD_SIZE; i++){
        	handLabels[i].setVisible(false);
        	handOpoLabels[i].setVisible(false);
        }

        JButton btnAlvo1 = new JButton("Alvo");
        btnAlvo1.setEnabled(false);
        btnAlvo1.setBounds(12, 447, 117, 25);
        frmTituloDeTeste.getContentPane().add(btnAlvo1);

        /*
         * Exemplo de uso de addMouseListener para trocar uma imagem de lacaio.
        hand_2.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                textPane.setText(textPane.getText() + "New Lacaio from slot 2\n");
                lacaio1.setIcon(hand_2.getIcon());
                msg1.setForeground(new Color(0, 0, 0)); //red
                msg1.setText("zzz");
            }
        });*/
        
        // Campo
        
        lacaioLabels[0] = new JLabel("");
        lacaioLabels[0].setBounds(9, 536, 150, 206);
        //lacaio1.setIcon(imageIcon3);
        frmTituloDeTeste.getContentPane().add(lacaioLabels[0]);

        lacaioOpoLabels[0] = new JLabel("");
        lacaioOpoLabels[0].setBounds(12, 230, 150, 206);
        frmTituloDeTeste.getContentPane().add(lacaioOpoLabels[0]);

        // Heroes
        JLabel hero_1 = new JLabel("");
        JLabel hero_2 = new JLabel("");
        hero_1.setBounds(1192, 742, 150, 219);
        hero_2.setBounds(1192, 12, 150, 219);
        ImageIcon imageIcon4 = new ImageIcon();
        ImageIcon imageIcon5 = new ImageIcon();

        try {
            //image 1
            BufferedImage img1 = ImageIO.read(new File(BASE_RESOURCE_PATH + "uther.png"));
            //img1.getScaledInstance(150, 219, Image.SCALE_SMOOTH); //lblNewLabel_1.getWidth(), lblNewLabel_1.getHeight()
            BufferedImage dimg2 = new BufferedImage(150, 219, BufferedImage.TYPE_4BYTE_ABGR);
            dimg2.getGraphics().drawImage(img1, 0, 0, 150, 219, null);
            imageIcon4 = new ImageIcon(dimg2);
            hero_1.setIcon(imageIcon4);

            //image 2
            BufferedImage img2 = ImageIO.read(new File(BASE_RESOURCE_PATH + "garrosh.png"));
            //img2.getScaledInstance(150, 219, Image.SCALE_SMOOTH); //lblNewLabel_1.getWidth(), lblNewLabel_1.getHeight()
            dimg2 = new BufferedImage(150, 219, BufferedImage.TYPE_4BYTE_ABGR);
            dimg2.getGraphics().drawImage(img2, 0, 0, 150, 219, null);
            imageIcon4 = new ImageIcon(dimg2);
            hero_2.setIcon(imageIcon4);

        } catch (IOException e) {
            e.printStackTrace();
        }
        frmTituloDeTeste.getContentPane().add(hero_1);
        frmTituloDeTeste.getContentPane().add(hero_2);

        mana_1 = new JLabel("Mana: 0/0");
        mana_1.setForeground(Color.BLUE);
        mana_1.setBounds(1212, 727, 70, 15);
        frmTituloDeTeste.getContentPane().add(mana_1);

        vida_2 = new JLabel("Vida: 30");
        vida_2.setBounds(1212, 243, 70, 15);
        frmTituloDeTeste.getContentPane().add(vida_2);

        vida_1 = new JLabel("Vida: 30");
        vida_1.setBounds(1212, 694, 70, 15);
        frmTituloDeTeste.getContentPane().add(vida_1);

        mana_2 = new JLabel("Mana: 0/0");
        mana_2.setForeground(Color.BLUE);
        mana_2.setBounds(1212, 270, 70, 15);
        frmTituloDeTeste.getContentPane().add(mana_2);

        JButton btnAlvoHero = new JButton("Alvo");
        btnAlvoHero.setEnabled(false);
        btnAlvoHero.setBounds(1192, 297, 117, 25);
        frmTituloDeTeste.getContentPane().add(btnAlvoHero);

        JButton btnPoderHeroico = new JButton("Poder");
        btnPoderHeroico.setEnabled(false);
        btnPoderHeroico.setBounds(1192, 664, 117, 25);
        frmTituloDeTeste.getContentPane().add(btnPoderHeroico);

        JLabel labelDeck2 = new JLabel("");
        labelDeck2.setBounds(1398, 12, 150, 219);
        frmTituloDeTeste.getContentPane().add(labelDeck2);

        JLabel labelDeck1 = new JLabel("");
        labelDeck1.setBounds(1398, 742, 150, 219);
        frmTituloDeTeste.getContentPane().add(labelDeck1);

        // decks
        try {
            //image 2
            img = ImageIO.read(new File(BASE_RESOURCE_PATH + "back.png"));
            img.getScaledInstance(labelDeck2.getWidth(), labelDeck2.getHeight(), Image.SCALE_SMOOTH); //lblNewLabel_1.getWidth(), lblNewLabel_1.getHeight()
            BufferedImage dimg2 = new BufferedImage(labelDeck2.getWidth(), labelDeck2.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
            dimg2.getGraphics().drawImage(img, 0, 0, labelDeck2.getWidth(), labelDeck2.getHeight(), null);
            imageIcon4 = new ImageIcon(dimg2);
            labelDeck2.setIcon(imageIcon4);

            //image 2
            img = ImageIO.read(new File(BASE_RESOURCE_PATH + "back.png"));
            img.getScaledInstance(labelDeck2.getWidth(), labelDeck2.getHeight(), Image.SCALE_SMOOTH); //lblNewLabel_1.getWidth(), lblNewLabel_1.getHeight()
            dimg2 = new BufferedImage(labelDeck2.getWidth(), labelDeck2.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
            dimg2.getGraphics().drawImage(img, 0, 0, labelDeck2.getWidth(), labelDeck2.getHeight(), null);
            imageIcon4 = new ImageIcon(dimg2);
            labelDeck1.setIcon(imageIcon4);

            lblCartas1 = new JLabel("Cartas: 30");
            lblCartas1.setBounds(1426, 714, 83, 15);
            frmTituloDeTeste.getContentPane().add(lblCartas1);

            lblCartas2 = new JLabel("Cartas: 30");
            lblCartas2.setBounds(1426, 243, 83, 15);
            frmTituloDeTeste.getContentPane().add(lblCartas2);

            //JTextPane textPane = new JTextPane();
            btnClearMessages = new JButton("Clear Log");
            btnClearMessages.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                    textPane.setText("");
                }
            });
            btnClearMessages.setBackground(Color.WHITE);
            btnClearMessages.setBounds(1487, 297, 101, 25);
            frmTituloDeTeste.getContentPane().add(btnClearMessages);
            
            btnAtacar2 = new JButton("Atacar");
            btnAtacar2.setEnabled(false);
            btnAtacar2.setBounds(180, 499, 117, 25);
            frmTituloDeTeste.getContentPane().add(btnAtacar2);
            
            btnAlvo2 = new JButton("Alvo");
            btnAlvo2.setEnabled(false);
            btnAlvo2.setBounds(180, 447, 117, 25);
            frmTituloDeTeste.getContentPane().add(btnAlvo2);
            
            lacaioLabels[1] = new JLabel("");
            lacaioLabels[1].setBounds(170, 536, 150, 206);
            frmTituloDeTeste.getContentPane().add(lacaioLabels[1]);
            
            lacaioLabels[2] = new JLabel("");
            lacaioLabels[2].setBounds(332, 536, 150, 206);
            frmTituloDeTeste.getContentPane().add(lacaioLabels[2]);
            
            lacaioLabels[3] = new JLabel("");
            lacaioLabels[3].setBounds(494, 536, 150, 206);
            frmTituloDeTeste.getContentPane().add(lacaioLabels[3]);
            
            lacaioLabels[4] = new JLabel("");
            lacaioLabels[4].setBounds(656, 536, 150, 206);
            frmTituloDeTeste.getContentPane().add(lacaioLabels[4]);
            
            lacaioLabels[5] = new JLabel("");
            lacaioLabels[5].setBounds(818, 536, 150, 206);
            frmTituloDeTeste.getContentPane().add(lacaioLabels[5]);
            
            lacaioLabels[6] = new JLabel("");
            lacaioLabels[6].setBounds(980, 536, 150, 206);
            frmTituloDeTeste.getContentPane().add(lacaioLabels[6]);
            
            lacaioOpoLabels[1] = new JLabel("");
            lacaioOpoLabels[1].setBounds(170, 230, 150, 206);
            frmTituloDeTeste.getContentPane().add(lacaioOpoLabels[1]);
            
            lacaioOpoLabels[2] = new JLabel("");
            lacaioOpoLabels[2].setBounds(332, 230, 150, 206);
            frmTituloDeTeste.getContentPane().add(lacaioOpoLabels[2]);
            
            lacaioOpoLabels[3] = new JLabel("");
            lacaioOpoLabels[3].setBounds(494, 230, 150, 206);
            frmTituloDeTeste.getContentPane().add(lacaioOpoLabels[3]);
            
            lacaioOpoLabels[4] = new JLabel("");
            lacaioOpoLabels[4].setBounds(656, 230, 150, 206);
            frmTituloDeTeste.getContentPane().add(lacaioOpoLabels[4]);
            
            lacaioOpoLabels[5] = new JLabel("");
            lacaioOpoLabels[5].setBounds(818, 230, 150, 206);
            frmTituloDeTeste.getContentPane().add(lacaioOpoLabels[5]);
            
            lacaioOpoLabels[6] = new JLabel("");
            lacaioOpoLabels[6].setBounds(977, 230, 150, 206);
            frmTituloDeTeste.getContentPane().add(lacaioOpoLabels[6]);
            
            btnAlvo3 = new JButton("Alvo");
            btnAlvo3.setEnabled(false);
            btnAlvo3.setBounds(342, 447, 117, 25);
            frmTituloDeTeste.getContentPane().add(btnAlvo3);
            
            btnAtacar3 = new JButton("Atacar");
            btnAtacar3.setEnabled(false);
            btnAtacar3.setBounds(342, 499, 117, 25);
            frmTituloDeTeste.getContentPane().add(btnAtacar3);
            
            btnAlvo4 = new JButton("Alvo");
            btnAlvo4.setEnabled(false);
            btnAlvo4.setBounds(517, 447, 117, 25);
            frmTituloDeTeste.getContentPane().add(btnAlvo4);
            
            btnAtacar4 = new JButton("Atacar");
            btnAtacar4.setEnabled(false);
            btnAtacar4.setBounds(517, 499, 117, 25);
            frmTituloDeTeste.getContentPane().add(btnAtacar4);
            
            btnAlvo5 = new JButton("Alvo");
            btnAlvo5.setEnabled(false);
            btnAlvo5.setBounds(677, 447, 117, 25);
            frmTituloDeTeste.getContentPane().add(btnAlvo5);
            
            btnAtacar5 = new JButton("Atacar");
            btnAtacar5.setEnabled(false);
            btnAtacar5.setBounds(677, 499, 117, 25);
            frmTituloDeTeste.getContentPane().add(btnAtacar5);
            
            btnAlvo6 = new JButton("Alvo");
            btnAlvo6.setEnabled(false);
            btnAlvo6.setBounds(839, 447, 117, 25);
            frmTituloDeTeste.getContentPane().add(btnAlvo6);
            
            btnAtacar6 = new JButton("Atacar");
            btnAtacar6.setEnabled(false);
            btnAtacar6.setBounds(839, 499, 117, 25);
            frmTituloDeTeste.getContentPane().add(btnAtacar6);
            
            btnAlvo7 = new JButton("Alvo");
            btnAlvo7.setEnabled(false);
            btnAlvo7.setBounds(987, 447, 117, 25);
            frmTituloDeTeste.getContentPane().add(btnAlvo7);
            
            btnAtacar7 = new JButton("Atacar");
            btnAtacar7.setEnabled(false);
            btnAtacar7.setBounds(987, 499, 117, 25);
            frmTituloDeTeste.getContentPane().add(btnAtacar7);
            
            msg2 = new JLabel("");
            msg2.setHorizontalAlignment(SwingConstants.CENTER);
            msg2.setBounds(170, 484, 127, 15);
            frmTituloDeTeste.getContentPane().add(msg2);
            
            msg3 = new JLabel("");
            msg3.setHorizontalAlignment(SwingConstants.CENTER);
            msg3.setBounds(332, 484, 127, 15);
            frmTituloDeTeste.getContentPane().add(msg3);
            
            msg4 = new JLabel("");
            msg4.setHorizontalAlignment(SwingConstants.CENTER);
            msg4.setBounds(517, 484, 127, 15);
            frmTituloDeTeste.getContentPane().add(msg4);
            
            msg5 = new JLabel("");
            msg5.setHorizontalAlignment(SwingConstants.CENTER);
            msg5.setBounds(679, 484, 127, 15);
            frmTituloDeTeste.getContentPane().add(msg5);
            
            msg6 = new JLabel("");
            msg6.setHorizontalAlignment(SwingConstants.CENTER);
            msg6.setBounds(839, 484, 127, 15);
            frmTituloDeTeste.getContentPane().add(msg6);
            
            msg7 = new JLabel("");
            msg7.setHorizontalAlignment(SwingConstants.CENTER);
            msg7.setBounds(997, 484, 127, 15);
            frmTituloDeTeste.getContentPane().add(msg7);
            
            btnNext = new JButton("Next");
            btnNext.addActionListener(new ActionListener() {
            	public void actionPerformed(ActionEvent e) {
            		processarNextAcoes(true);
            	}
            });
            btnNext.setEnabled(false);
            btnNext.setBounds(19, 5, 66, 25);
            //frmTituloDeTeste.getContentPane().add(btnNext);
            globalPanel.add(btnNext);
            
            lblTurno = new JLabel("TURNO: 0");
            lblTurno.setFont(new Font("Dialog", Font.BOLD, 14));
            lblTurno.setBounds(1426, 658, 91, 26);
            frmTituloDeTeste.getContentPane().add(lblTurno);
            
            frmTituloDeTeste.getContentPane().add(scrollGrande);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}