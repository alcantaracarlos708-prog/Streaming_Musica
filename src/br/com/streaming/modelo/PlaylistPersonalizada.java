package br.com.streaming.modelo;

public class PlaylistPersonalizada extends Playlist {

    public PlaylistPersonalizada(String nome, String criadorNome) {
        super(nome);
        this.criadorNome = criadorNome;
    }

    public PlaylistPersonalizada(String id, String nome, String criadorNome, String genero) {
        super(id, nome, criadorNome, genero);
    }

    @Override
    public void reproduzir() {
        System.out.println("Playlist Personalizada: " + nome
                + " (criada por " + criadorNome + ")");
        if (musicas.isEmpty()) {
            System.out.println("  Nenhuma musica nesta playlist.");
            return;
        }
        for (Musica m : musicas) {
            System.out.println("  > " + m.getTitulo() + " - " + m.getArtista()
                    + " [" + m.getDuracaoFormatada() + "]");
        }
        System.out.println("  Duracao total: " + getDuracaoTotal() + "s");
    }
}
