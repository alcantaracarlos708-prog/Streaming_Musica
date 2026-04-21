import java.util.ArrayList;

public class Usuario {
    protected String nome;
    protected String email;
    protected ArrayList<Playlist> playlists;
    protected ArrayList<Musica> historicoReproducao;

    // Construtor padrão
    public Usuario() {
        this.playlists = new ArrayList<>();
        this.historicoReproducao = new ArrayList<>();
    }

    // Construtor com nome e email
    public Usuario(String nome, String email) {
        this.playlists = new ArrayList<>();
        this.historicoReproducao = new ArrayList<>();
        setNome(nome);
        setEmail(email);
    }

    // Getters e Setters
    public String getNome() { return nome; }
    public String getEmail() { return email; }

    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do usuario nao pode ser nulo ou vazio.");
        }
        this.nome = nome.trim();
    }

    public void setEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email nao pode ser nulo ou vazio.");
        }
        this.email = email.trim();
    }

    // Reproduz uma música e adiciona ao histórico
    public void reproduzirMusica(Musica musica) {
        if (musica == null) {
            System.out.println("Musica invalida.");
            return;
        }
        System.out.println("Reproduzindo: " + musica.getTitulo() + " - " + musica.getArtista());
        historicoReproducao.add(musica);
    }

    // Exibe o histórico de reproduções
    public void exibirHistorico() {
        System.out.println("\n--- HISTORICO DE REPRODUCAO de " + nome + " ---");
        if (historicoReproducao.isEmpty()) {
            System.out.println("Nenhuma musica reproduzida ainda.");
            return;
        }
        for (int i = 0; i < historicoReproducao.size(); i++) {
            System.out.print((i + 1) + ". ");
            historicoReproducao.get(i).exibir();
        }
    }

    // Cria uma playlist (sem limite — comportamento padrão)
    public void criarPlaylist(String nome) {
        Playlist nova = new Playlist(nome);
        playlists.add(nova);
        System.out.println("Playlist '" + nome + "' criada com sucesso!");
    }

    public Playlist getPlaylist(int indice) {
        if (indice >= 0 && indice < playlists.size()) {
            return playlists.get(indice);
        }
        return null;
    }

    public void listarPlaylists() {
        if (playlists.isEmpty()) {
            System.out.println("Nenhuma playlist encontrada.");
        } else {
            for (int i = 0; i < playlists.size(); i++) {
                System.out.println((i + 1) + ". " + playlists.get(i).getNome());
            }
        }
    }

    public int getQuantidadePlaylists() {
        return playlists.size();
    }
}