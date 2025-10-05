package MAAT.AlgoritmoGerenciador;

import MAAT.Simulacao.Processo;import MAAT.Simulacao.Memoria;


public interface AlgoritmoGerenciador {

    // Método que já tínhamos
    int encontrarEspaco(Processo processo, Memoria memoria);

    /**
     * Notifica o algoritmo que um processo foi desalocado.
     * Essencial para algoritmos como o QuickFit que gerenciam listas de buracos.
     * @param posicao A posição onde o bloco foi liberado.
     * @param tamanho O tamanho do bloco liberado.
     */
    default void processoDesalocado(int posicao, int tamanho) {
        // Implementação padrão vazia. Apenas QuickFit precisará disso.
    }

    /**
     * Chamado uma vez no início da simulação para inicializar o algoritmo.
     * @param memoria O estado inicial da memória.
     */
    default void inicializar(Memoria memoria) {
        // Implementação padrão vazia.
    }
}
