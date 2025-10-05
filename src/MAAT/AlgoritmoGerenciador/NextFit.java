package MAAT.AlgoritmoGerenciador;
import  MAAT.Simulacao.Processo;
import  MAAT.Simulacao.Memoria;

public class NextFit implements AlgoritmoGerenciador {

    // Guarda o índice de onde a próxima busca deve começar.
    private int ultimoIndiceVerificado = 0;

    /**
     * Implementação do algoritmo Next Fit.
     * Continua a busca por espaço livre a partir do ponto onde a última
     * alocação terminou, dando a volta na memória se necessário.
     *
     * @param processo O processo a ser alocado.
     * @param memoria  O estado atual da memória.
     * @return O índice do bloco de início para alocação, ou -1 se não houver espaço.
     */
    @Override
    public int encontrarEspaco(Processo processo, Memoria memoria) {
        int tamanhoProcesso = processo.getTamanho();
        int[] mapaDeBits = memoria.getMapaDeBits();
        int tamanhoMemoria = memoria.getTamanhoTotal();

        // Faz uma volta completa na memória, começando do último ponto.
        for (int i = 0; i < tamanhoMemoria; i++) {

            // Lógica de "wrap-around": calcula o índice atual baseado no último ponto.
            int indiceAtual = (ultimoIndiceVerificado + i) % tamanhoMemoria;

            // Otimização: se não há chance de caber, pula.
            if (indiceAtual + tamanhoProcesso > tamanhoMemoria) {
                continue;
            }

            boolean espacoEncontrado = true;
            // Verifica se o bloco de 'indiceAtual' até 'indiceAtual + tamanhoProcesso' está livre.
            for (int j = 0; j < tamanhoProcesso; j++) {
                if (mapaDeBits[indiceAtual + j] == 1) { // Bloco ocupado
                    espacoEncontrado = false;
                    // Pula a verificação para depois do bloco ocupado
                    i += j;
                    break;
                }
            }

            if (espacoEncontrado) {
                // Guarda a posição final deste processo para a próxima busca.
                ultimoIndiceVerificado = (indiceAtual + tamanhoProcesso) % tamanhoMemoria;
                return indiceAtual; // Encontrou um espaço!
            }
        }

        return -1; // Não há espaço disponível após uma volta completa.
    }
}
