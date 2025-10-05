package MAAT.Simulacao;

public class Processo {

    private String id; // ID do processo (ex: "P1")
    private int tamanho; // Tamanho em blocos
    private int posicaoNaMemoria; // Posição inicial no mapa de bits onde foi alocado

    /**
     * Construtor para criar um novo processo.
     * @param id O identificador do processo.
     * @param tamanho O número de blocos de memória que o processo requer.
     */
    public Processo(String id, int tamanho) {
        this.id = id;
        this.tamanho = tamanho;
        this.posicaoNaMemoria = -1; // Inicia com -1 para indicar que não está alocado na memória.
    }

    // --- Getters e Setters ---

    public String getId() {
        return id;
    }

    public int getTamanho() {
        return tamanho;
    }

    public int getPosicaoNaMemoria() {
        return posicaoNaMemoria;
    }

    public void setPosicaoNaMemoria(int posicaoNaMemoria) {
        this.posicaoNaMemoria = posicaoNaMemoria;
    }

    /**
     * Verifica se o processo está atualmente alocado na memória.
     * @return true se o processo estiver alocado, false caso contrário.
     */
    public boolean isAlocado() {
        return this.posicaoNaMemoria != -1;
    }

    /**
     * Representação em String do objeto Processo, útil para logs e depuração.
     */
    @Override
    public String toString() {
        return "Processo{" +
                "id='" + id + '\'' +
                ", tamanho=" + tamanho +
                ", alocadoEm=" + (isAlocado() ? posicaoNaMemoria : "N/A") +
                '}';
    }
}
