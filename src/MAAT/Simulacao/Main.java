package MAAT.Simulacao;
import MAAT.AlgoritmoGerenciador.AlgoritmoGerenciador;
import MAAT.AlgoritmoGerenciador.FirstFit;import MAAT.AlgoritmoGerenciador.NextFit;import MAAT.AlgoritmoGerenciador.BestFit;
import MAAT.AlgoritmoGerenciador.QuickFit;import MAAT.AlgoritmoGerenciador.WorstFit;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;


public class Main {

    public static void main(String[] args) {
        // --- 1. Configuração do Ambiente da Simulação ---

        // Define o tamanho da memória conforme a atividade
        final int TAMANHO_MEMORIA = 32; //

        // Cria a lista de todos os processos disponíveis
        List<Processo> processosDisponiveis = criarListaDeProcessos();

        // Cria um mapa para armazenar os algoritmos a serem testados.
        // Usamos LinkedHashMap para manter a ordem de inserção.
        Map<String, AlgoritmoGerenciador> algoritmos = new LinkedHashMap<>();


        algoritmos.put("First Fit", new FirstFit());
        algoritmos.put("Next Fit", new NextFit());
        algoritmos.put("Best Fit", new BestFit());
        algoritmos.put("public class Quick Fit", new QuickFit());
        algoritmos.put("Worst Fit", new WorstFit());


        System.out.println("### INÍCIO DA SIMULAÇÃO DE GERENCIAMENTO DE MEMÓRIA ###");
        System.out.println("Tamanho da Memória: " + TAMANHO_MEMORIA + " blocos.");
        System.out.println("Total de Processos na lista: " + processosDisponiveis.size());

        // --- 2. Execução da Simulação para Cada Algoritmo ---

        for (Map.Entry<String, AlgoritmoGerenciador> entry : algoritmos.entrySet()) {
            String nomeAlgoritmo = entry.getKey();
            AlgoritmoGerenciador algoritmo = entry.getValue();

            // É importante resetar o estado dos processos antes de cada simulação
            resetarEstadoDosProcessos(processosDisponiveis);

            // Cria uma nova instância do Simulador para cada algoritmo
            Simulador simulador = new Simulador(algoritmo, nomeAlgoritmo, TAMANHO_MEMORIA, processosDisponiveis);


            simulador.executar();
        }

        System.out.println("### FIM DE TODAS AS SIMULAÇÕES ###");
    }

    /**

     * @return Uma lista de objetos Processo.
     */
    private static List<Processo> criarListaDeProcessos() {
        List<Processo> processos = new ArrayList<>();
        processos.add(new Processo("P1", 5));   //
        processos.add(new Processo("P2", 4));   //
        processos.add(new Processo("P3", 2));   //
        processos.add(new Processo("P4", 5));   //
        processos.add(new Processo("P5", 8));   //
        processos.add(new Processo("P6", 3));   //
        processos.add(new Processo("P7", 5));   //
        processos.add(new Processo("P8", 8));   //
        processos.add(new Processo("P9", 2));   //
        processos.add(new Processo("P10", 6));  //
        return processos;
    }

    /**
     * Garante que todos os processos comecem como "não alocados" antes de uma nova simulação.
     * @param processos A lista de processos a ser resetada.
     */
    private static void resetarEstadoDosProcessos(List<Processo> processos) {
        for (Processo p : processos) {
            p.setPosicaoNaMemoria(-1);
        }
    }
}