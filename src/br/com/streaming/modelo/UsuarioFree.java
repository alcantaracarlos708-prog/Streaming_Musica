package br.com.streaming.modelo;

public class UsuarioFree extends Usuario {

    private static final int MAX_PLAYLISTS        = 3;
    private static final int LIMITE_REPRODUCOES   = 30;

    private int contadorReproducoes = 0;
    private int anunciosExibidos    = 0;

    public UsuarioFree(String id, String nome, String email) {
        super(id, nome, email);
    }

    public int getContadorReproducoes() { return contadorReproducoes; }
    public int getAnunciosExibidos()    { return anunciosExibidos; }

    @Override
    public String  getTipoPlano()          { return "Free"; }
    @Override
    public int     getLimiteDownloads()    { return 0; }
    @Override
    public boolean podeOuvirSemAnuncios()  { return false; }

    @Override
    public void reproduzirMusica(Musica musica) {
        if (musica == null) { System.out.println("Musica invalida."); return; }
        if (contadorReproducoes >= LIMITE_REPRODUCOES) {
            System.out.println("Limite diario de reproducoes atingido! Assine Premium.");
            return;
        }
        contadorReproducoes++;
        if (contadorReproducoes % 3 == 0) exibirAnuncio();
        musica.reproduzir();
        System.out.println("  [Qualidade: Padrao]");
        registrarHistorico(musica);
    }

    // Sobrecarga: reproduzir com detalhes
    public void reproduzirMusica(Musica musica, boolean exibirDetalhes) {
        reproduzirMusica(musica);
        if (exibirDetalhes && musica != null) {
            System.out.println("  Duracao: " + musica.getDuracaoFormatada()
                    + " | Genero: " + musica.getGenero());
        }
    }

    @Override
    public void criarPlaylist(Playlist playlist) {
        if (playlists.size() >= MAX_PLAYLISTS) {
            System.out.println("Limite de " + MAX_PLAYLISTS
                    + " playlists atingido! Assine Premium para playlists ilimitadas.");
            return;
        }
        super.criarPlaylist(playlist);
    }

    public void exibirAnuncio() {
        anunciosExibidos++;
        System.out.println("\n" + "=".repeat(50));
        System.out.println("ANUNCIO #" + anunciosExibidos
                + ": Assine Premium e ouca sem interrupcoes!");
        System.out.println("=".repeat(50) + "\n");
    }
}
