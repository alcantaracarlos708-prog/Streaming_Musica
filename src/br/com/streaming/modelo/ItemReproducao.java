package br.com.streaming.modelo;

public class ItemReproducao {

    private Musica musica;
    private int    posicaoSegundos;
    private boolean pulada;

    public ItemReproducao(Musica musica) {
        if (musica == null) throw new IllegalArgumentException("Musica nao pode ser nula.");
        this.musica          = musica;
        this.posicaoSegundos = 0;
        this.pulada          = false;
    }

    public Musica getMusica()          { return musica; }
    public int    getPosicaoSegundos() { return posicaoSegundos; }
    public boolean isPulada()          { return pulada; }

    public void avancar(int segundos) {
        posicaoSegundos = Math.min(posicaoSegundos + segundos, musica.getDuracaoSegundos());
    }

    public void pular() {
        pulada = true;
        posicaoSegundos = musica.getDuracaoSegundos();
    }

    public void resetar() {
        posicaoSegundos = 0;
        pulada          = false;
    }

    @Override
    public String toString() {
        return musica.getTitulo() + " [" + posicaoSegundos + "s / "
                + musica.getDuracaoSegundos() + "s]" + (pulada ? " (pulada)" : "");
    }
}
