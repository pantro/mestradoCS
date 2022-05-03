/**
* Esta classe é uma enumeração de tipos de ações(informações) possíveis para atualizar a interface gráfica.
* @see java.lang.Object
* @author Rafael Arakaki - MC302
*/
public enum TipoAcao {
	INICIO_PARTIDA,
	INICIO_TURNO,
	ADICIONAR_MAO, RETIRAR_MAO,
	ADICIONAR_MESA, RETIRAR_MESA,
	SET_HP_HEROI, SET_MANA_HEROI, SET_MAXMANA_HEROI, SET_NUM_BARALHO,
	SET_LACAIO_HP,
	SET_LACAIO_BUFFATTACK,
	FIM_TURNO,
	FIM_PARTIDA
}
