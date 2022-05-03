import java.util.ArrayList;

/**
* Esta classe é uma interface na qual as classes @Motor e @InterfaceGrafica se comunicam.
* O objeto @Motor recebe em sua construção um objeto @InterfaceGrafica.
* A partir de então, o @Motor tem a obrigação de informar à @InterfaceGrafica informações para que esta última atualize a sua janela para que a pessoa possa ver o jogo.
* Essas informações são passadas através dessa 'interface', que contém um método chamado  processarAcoesGUI (ArrayList<Object> listaAcoes).
* Esse método recebe uma lista de ArrayList<Object> que podem ser @AcaoGUI, @Jogada ou @String.
* O @AcaoGUI é uma informação a ser atualizada na GUI; o @Jogada é uma jogada que foi realizada pelo @Jogador; o @String é uma mensagem de texto para ser exibida na GUI.
* @see java.lang.Object
* @author Rafael Arakaki - MC302
*/
public interface InterfaceGUI {
	
	/**
	  * O método principal pelo qual o Motor informa uma interface gráfica quais informações devem ser atualizadas/exibidas.
	  * 
	  * @param listaAcoes Esse método recebe uma lista de objetos (ArrayList<Object>) que podem ser: @AcaoGUI, @Jogada ou @String.
	  * Esses objetos estão em ordem que devem ser atualizadas e exibidas pela GUI, segundo um protocolo específico.
	  * Esse protocolo dita a ordem em que os objetos podem ser colocados na lista. Por exemplo, sempre inicia-se uma partida com 'INICIO_PARTIDA' e termina com 'FIM_PARTIDA'.
	  * O @AcaoGUI é uma informação a ser atualizada na GUI; o @Jogada é uma jogada que foi realizada pelo @Jogador; o @String é uma mensagem de texto para ser exibida na GUI.
	  * Qualquer objeto que não for de uma das três classes citadas é descartado e uma exceção na interface gráfica é disparada.
	  * Ao retornar, a interface gráfica está liberando o Motor para seguir com a partida.
	  */
	public void processarAcoesGUI (ArrayList<Object> listaAcoes);
}
