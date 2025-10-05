package MAAT.AlgoritmoGerenciador;
import  MAAT.Simulacao.Processo;
import  MAAT.Simulacao.Memoria;

public class WorstFit implements AlgoritmoGerenciador {

    /**
     * Implementação do algoritmo Worst Fit.
     * Procura por toda a memória e aloca o processo no MAIOR buraco que seja
     * grande o suficiente.
     *
     * @param processo O processo a ser alocado.
     * @param memoria O estado atual da memória.
     * @return O índice do bloco de início para a alocação, ou -1 se não houver espaço.
     */
    @Override
    public int encontrarEspaco(Processo processo, Memoria memoria) {
        int tamanhoProcesso = processo.getTamanho();
        int[] mapaDeBits = memoria.getMapaDeBits();
        int tamanhoMemoria = memoria.getTamanhoTotal();

        int piorIndice = -1;
        int maiorTamanhoDoBuraco = -1; // Começa com -1 para garantir que o primeiro buraco encontrado seja aceito

        int i = 0;
        while (i < tamanhoMemoria) {
            // Se encontrarmos o início de um buraco (espaço livre)
            if (mapaDeBits[i] == 0) {
                int inicioDoBuraco = i;
                int tamanhoDoBuracoAtual = 0;

                // Mede o tamanho do buraco atual
                while (i < tamanhoMemoria && mapaDeBits[i] == 0) {
                    tamanhoDoBuracoAtual++;
                    i++;
                }

                // Verifica se o buraco é grande o suficiente para o processo
                if (tamanhoDoBuracoAtual >= tamanhoProcesso) {
                    // E se é o "pior" (maior) buraco encontrado até agora
                    if (tamanhoDoBuracoAtual > maiorTamanhoDoBuraco) {
                        maiorTamanhoDoBuraco = tamanhoDoBuracoAtual;
                        piorIndice = inicioDoBuraco;
                    }
                }
            } else {
                i++; // Pula para o próximo bloco se este estiver ocupado
            }
        }

        return piorIndice; // Retorna o pior (maior) índice encontrado ou -1
    }
}
