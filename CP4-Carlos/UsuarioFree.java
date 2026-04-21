public class UsuarioFree extends Usuario {

    private static final int MAX_PLAYLISTS = 3;
    private int contadorReproducoes;
    private int limiteReproducoes;

    public UsuarioFree(String nome, String email) {
        super(nome, email); // Chama o construtor da superclasse
        this.contadorReproducoes = 0;
        this.limiteReproducoes = 30; // Limite diário de reproduções
    }

    // Getters
    public int getContadorReproducoes() { return contadorReproducoes; }
    public int getLimiteReproducoes() { return limiteReproducoes; }

    // Sobrescrita: reproduz com contador e anúncios a cada 3 músicas
    @Override
    public void reproduzirMusica(Musica musica) {
        if (musica == null) {
            System.out.println("Musica invalida.");
            return;
        }

        if (contadorReproducoes >= limiteReproducoes) {
            System.out.println("Limite diario de reproducoes atingido!");
            System.out.println("Assine Premium para reproducoes ilimitadas!");
            return;
        }

        contadorReproducoes++;

        // Exibe anúncio a cada 3 músicas
        if (contadorReproducoes % 3 == 0) {
            exibirAnuncio();
        }

        // Chama o comportamento original da superclasse
        super.reproduzirMusica(musica);
        System.out.println("[Qualidade: Padrao]");
    }

    // Sobrescrita: criar playlist com limite de 3
    @Override
    public void criarPlaylist(String nome) {
        if (playlists.size() >= MAX_PLAYLISTS) {
            System.out.println("Limite de " + MAX_PLAYLISTS + " playlists atingido!");
            System.out.println("Assine Premium para playlists ilimitadas!");
            return;
        }
        super.criarPlaylist(nome);
    }

    // Exibe um anúncio
    public void exibirAnuncio() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("ANUNCIO: Assine Premium e ouca sem interrupcoes!");
        System.out.println("=".repeat(50) + "\n");
    }

    // Sobrecarga: reproduzir com mensagem extra (overloading)
    public void reproduzirMusica(Musica musica, boolean exibirDetalhes) {
        reproduzirMusica(musica);
        if (exibirDetalhes && musica != null) {
            System.out.println("   Duracao: " + musica.getDuracaoFormatada()
                    + " | Genero: " + musica.getGenero());
        }
    }
}