/**
* Essa classe é responsável por inicializar a Interface Gráfica (GUI) em uma Thread separada para o jogo LaMa (Lacaios & Magias).
* @see java.lang.Object
* @author Rafael Arakaki - MC302
*/
public class InterfaceRunner implements Runnable {
	public InterfaceGrafica window;
	public boolean ready;
	public boolean inverterCampoGUI;
	
	// Construtor padrão de InterfaceRunner.
	// inverterCampoGUI = inverte os jogadores de lugar na interface gráfica.
	public InterfaceRunner(boolean inverterCampoGUI) {
		this.inverterCampoGUI = inverterCampoGUI;
		this.ready = false;
	}

	// Instancia e inicializa a GUI em uma Thread separada
	public void run() {
        try {
    		window = new InterfaceGrafica(this.inverterCampoGUI);
            window.frmTituloDeTeste.setVisible(true);
    		this.ready = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
