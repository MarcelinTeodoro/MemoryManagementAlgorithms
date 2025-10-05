package MAAT.Simulacao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Memoria {

    private int[] mapaDeBits;
    private int tamanhoTotal;

    /**
     * Construtor da classe Memoria.
     * @param tamanho O número total de blocos da memória. [cite: 71]
     */
    public Memoria(int tamanho) {
        this.tamanhoTotal = tamanho;
        this.mapaDeBits = new int[tamanho];
        // Inicializa a memória com todos os blocos livres (0)
        Arrays.fill(this.mapaDeBits, 0);
    }




    /**
     * Aloca um espaço na memória, marcando os blocos como ocupados (1).
     * @param posicao O índice inicial da alocação.
     * @param tamanho O número de blocos a serem alocados.
     */
    public void alocar(int posicao, int tamanho) {
        for (int i = 0; i < tamanho; i++) {
            if (posicao + i < tamanhoTotal) {
                mapaDeBits[posicao + i] = 1; // 1 representa um espaço ocupado
            }
        }
    }

    /**
     * Desaloca um espaço na memória, marcando os blocos como livres (0). [cite: 66, 133]
     * @param posicao O índice inicial da desalocação.
     * @param tamanho O número de blocos a serem liberados.
     */
    public void desalocar(int posicao, int tamanho) {
        for (int i = 0; i < tamanho; i++) {
            if (posicao + i < tamanhoTotal) {
                mapaDeBits[posicao + i] = 0; // 0 representa um espaço livre
            }
        }
    }

    /**
     * Calcula e exibe a fragmentação externa.
     * A fragmentação externa é a soma de todos os blocos livres que formam buracos
     * pequenos demais para alocar qualquer um dos processos pendentes.
     * @param processosPendentes A lista de processos que aguardam para serem alocados.
     */
    public void calcularEExibirFragmentacaoExterna(List<Processo> processosPendentes) {
        if (processosPendentes.isEmpty()) {
            System.out.println("Não há processos pendentes para calcular a fragmentação.");
            return;
        }

        int menorProcessoPendente = Integer.MAX_VALUE;
        for (Processo p : processosPendentes) {
            if (p.getTamanho() < menorProcessoPendente) {
                menorProcessoPendente = p.getTamanho();
            }
        }

        int fragmentacaoExterna = 0;
        int buracoAtual = 0;
        for (int i = 0; i < tamanhoTotal; i++) {
            if (mapaDeBits[i] == 0) {
                buracoAtual++;
            } else {
                if (buracoAtual > 0 && buracoAtual < menorProcessoPendente) {
                    fragmentacaoExterna += buracoAtual;
                }
                buracoAtual = 0;
            }
        }
        // Verifica o último buraco, caso a memória termine com espaço livre
        if (buracoAtual > 0 && buracoAtual < menorProcessoPendente) {
            fragmentacaoExterna += buracoAtual;
        }

        System.out.println("Estatística de Fragmentação Externa: " + fragmentacaoExterna + " blocos inutilizáveis.");
    }

    public void exibirMapa() {

        System.out.println(Arrays.toString(mapaDeBits));
    }



    public int[] getMapaDeBits() {
        return mapaDeBits;
    }

    public int getTamanhoTotal() {
        return tamanhoTotal;
    }
}





