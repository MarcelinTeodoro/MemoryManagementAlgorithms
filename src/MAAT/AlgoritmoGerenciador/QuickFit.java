package MAAT.AlgoritmoGerenciador;
import  MAAT.Simulacao.Processo;
import  MAAT.Simulacao.Memoria;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Collections;

public class QuickFit implements AlgoritmoGerenciador {

    // Estrutura de dados adicional para acompanhar as listas de buracos por tamanho
    private final Map<Integer, List<Integer>> listasDeBuracos = new HashMap<>();

    @Override
    public void inicializar(Memoria memoria) {
        // No início, a memória inteira é um único grande buraco.
        listasDeBuracos.clear(); // Limpa dados de simulações anteriores
        int tamanhoMemoria = memoria.getTamanhoTotal();
        List<Integer> listaInicial = new ArrayList<>();
        listaInicial.add(0); // Adiciona um buraco do tamanho da memória na posição 0
        listasDeBuracos.put(tamanhoMemoria, listaInicial);
    }

    @Override
    public int encontrarEspaco(Processo processo, Memoria memoria) {
        int tamanhoProcesso = processo.getTamanho();

        // 1. Procura por um buraco de tamanho exato
        if (listasDeBuracos.containsKey(tamanhoProcesso) && !listasDeBuracos.get(tamanhoProcesso).isEmpty()) {
            List<Integer> listaExata = listasDeBuracos.get(tamanhoProcesso);
            int posicao = listaExata.remove(0); // Pega o primeiro da lista
            return posicao;
        }

        // 2. Se não encontrou, procura por um buraco maior (lógica Best Fit como fallback)
        int melhorPosicao = -1;
        int menorTamanhoMaior = Integer.MAX_VALUE;

        // Itera sobre as listas de buracos
        for (Map.Entry<Integer, List<Integer>> entry : listasDeBuracos.entrySet()) {
            int tamanhoBuraco = entry.getKey();
            List<Integer> posicoes = entry.getValue();

            if (tamanhoBuraco > tamanhoProcesso && tamanhoBuraco < menorTamanhoMaior && !posicoes.isEmpty()) {
                menorTamanhoMaior = tamanhoBuraco;
                melhorPosicao = posicoes.get(0);
            }
        }

        // Se encontrou um buraco maior para dividir
        if (melhorPosicao != -1) {
            // Remove o buraco grande da lista antiga
            listasDeBuracos.get(menorTamanhoMaior).remove(Integer.valueOf(melhorPosicao));

            // Calcula o tamanho do novo buraco que sobrará
            int tamanhoRestante = menorTamanhoMaior - tamanhoProcesso;
            if (tamanhoRestante > 0) {
                int posicaoRestante = melhorPosicao + tamanhoProcesso;
                // Adiciona o novo buraco (resto da divisão) à lista apropriada
                processoDesalocado(posicaoRestante, tamanhoRestante);
            }
            return melhorPosicao;
        }

        return -1; // Não encontrou espaço
    }

    @Override
    public void processoDesalocado(int posicao, int tamanho) {
        // Adiciona o espaço liberado na lista correspondente ao seu tamanho
        listasDeBuracos.computeIfAbsent(tamanho, k -> new ArrayList<>()).add(posicao);
    }
}
