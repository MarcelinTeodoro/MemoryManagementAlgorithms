package MAAT.AlgoritmoGerenciador;
import  MAAT.Simulacao.Processo;
import  MAAT.Simulacao.Memoria;
public class FirstFit implements AlgoritmoGerenciador {

    /**
     * Implementação do algoritmo First Fit para encontrar espaço na memória.
     * Este método percorre a lista de espaços livres desde o início e aloca
     * o processo no primeiro buraco que seja grande o suficiente.
     *
     * @param processo O processo a ser alocado.
     * @param memoria O estado atual da memória.
     * @return O índice do bloco de início onde o processo pode ser alocado, ou -1 se não houver espaço.
     */
    @Override
    public int encontrarEspaco(Processo processo, Memoria memoria) {
        int tamanhoProcesso = processo.getTamanho();
        int[] mapaDeBits = memoria.getMapaDeBits();
        int tamanhoMemoria = memoria.getTamanhoTotal();

        // Itera por todo o mapa de bits para encontrar um espaço
        for (int i = 0; i <= tamanhoMemoria - tamanhoProcesso; i++) {
            boolean espacoEncontrado = true;
            // Verifica se o bloco de 'i' até 'i + tamanhoProcesso' está livre
            for (int j = 0; j < tamanhoProcesso; j++) {
                if (mapaDeBits[i + j] == 1) { // 1 significa ocupado
                    espacoEncontrado = false;
                    // Pula para o próximo bloco após a ocupação para otimizar
                    i = i + j;
                    break;
                }
            }

            // Se o espaço foi encontrado, retorna a posição inicial
            if (espacoEncontrado) {
                return i; // Encontrou o primeiro espaço adequado!
            }
        }

        // Se o loop terminar, significa que não há espaço disponível
        return -1;
    }
}
