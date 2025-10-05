package MAAT.AlgoritmoGerenciador;
import  MAAT.Simulacao.Processo;
import  MAAT.Simulacao.Memoria;

public class BestFit implements AlgoritmoGerenciador {

    /**
     * Implementação do algoritmo Best Fit.
     * Procura por toda a memória e aloca o processo no menor buraco que seja
     * grande o suficiente, minimizando o espaço desperdiçado.
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

        int melhorIndice = -1;
        int menorTamanhoDoBuraco = Integer.MAX_VALUE;

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
                    // E se é o "melhor" (menor) buraco encontrado até agora
                    if (tamanhoDoBuracoAtual < menorTamanhoDoBuraco) {
                        menorTamanhoDoBuraco = tamanhoDoBuracoAtual;
                        melhorIndice = inicioDoBuraco;
                    }
                }
            } else {
                i++; // Pula para o próximo bloco se este estiver ocupado
            }
        }

        return melhorIndice; // Retorna o melhor índice encontrado ou -1
    }
}
