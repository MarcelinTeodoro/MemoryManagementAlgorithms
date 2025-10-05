package MAAT.Simulacao;
import MAAT.AlgoritmoGerenciador.AlgoritmoGerenciador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Simulador {

    private Memoria memoria;
    private AlgoritmoGerenciador algoritmo;
    private String nomeAlgoritmo;
    private List<Processo> todosOsProcessos;
    private Map<String, Processo> processosAlocados;
    private Random random;

    /**
     * Construtor do Simulador.
     * @param algoritmo O algoritmo de gerenciamento a ser usado.
     * @param nomeAlgoritmo O nome do algoritmo para exibição.
     * @param tamanhoMemoria O tamanho total da memória.
     * @param processos A lista de processos disponíveis para a simulação.
     */
    public Simulador(AlgoritmoGerenciador algoritmo, String nomeAlgoritmo, int tamanhoMemoria, List<Processo> processos) {
        this.algoritmo = algoritmo;
        this.nomeAlgoritmo = nomeAlgoritmo;
        this.memoria = new Memoria(tamanhoMemoria);
        this.todosOsProcessos = processos;
        this.processosAlocados = new HashMap<>();
        this.random = new Random();
    }

    /**
     * Executa o ciclo de simulação conforme as regras da atividade.
     */


    public void executar() {
        System.out.println("\n=======================================================");
        System.out.println("INICIANDO SIMULAÇÃO COM O ALGORITMO: " + nomeAlgoritmo);
        System.out.println("=======================================================");

        // Chamada para o novo método de inicialização
        algoritmo.inicializar(memoria);

        System.out.print("Estado inicial do mapa: ");
        memoria.exibirMapa();

        for (int i = 1; i <= 30; i++) {
            System.out.println("\n--- Operação " + i + " ---");

            Processo processoSorteado = todosOsProcessos.get(random.nextInt(todosOsProcessos.size()));

            if (processosAlocados.containsKey(processoSorteado.getId())) {
                desalocarProcesso(processoSorteado);
            } else {
                alocarProcesso(processoSorteado);
            }
        }

        System.out.println("\n--- FIM DA SIMULAÇÃO: " + nomeAlgoritmo + " ---");
        // ... (cálculo de fragmentação permanece o mesmo) ...
        List<Processo> processosPendentes = new ArrayList<>();
        for(Processo p : todosOsProcessos) {
            if(!processosAlocados.containsKey(p.getId())) {
                processosPendentes.add(p);
            }
        }
        memoria.calcularEExibirFragmentacaoExterna(processosPendentes);
        System.out.println("=======================================================\n");
    }

    private void alocarProcesso(Processo processo) {
        int posicao = algoritmo.encontrarEspaco(processo, memoria);

        if (posicao != -1) {
            // Mensagem de sucesso na alocação
            System.out.println("Processo " + processo.getId() + " (tamanho " + processo.getTamanho() + ") alocado no bloco " + posicao + ".");
            memoria.alocar(posicao, processo.getTamanho());
            processo.setPosicaoNaMemoria(posicao);
            processosAlocados.put(processo.getId(), processo);
        } else {
            // Mensagem de erro na alocação
            System.out.println("ERRO: Espaço insuficiente para alocar o Processo " + processo.getId() + " (tamanho " + processo.getTamanho() + ").");
        }
        System.out.print("Mapa de bits atualizado: "); // Saída do mapa após a operação
        memoria.exibirMapa();
    }
    private void desalocarProcesso(Processo processo) {
        int posicao = processo.getPosicaoNaMemoria();
        int tamanho = processo.getTamanho(); // Guarda o tamanho antes de desalocar

        System.out.println("Processo " + processo.getId() + " encontrado na memória. Desalocando do bloco " + posicao + ".");

        memoria.desalocar(posicao, tamanho);
        processo.setPosicaoNaMemoria(-1);
        processosAlocados.remove(processo.getId());

        // Notifica o algoritmo sobre a desalocação
        algoritmo.processoDesalocado(posicao, tamanho);

        System.out.print("Mapa de bits atualizado: ");
        memoria.exibirMapa();
    }

}
