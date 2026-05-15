package br.com.streaming.modelo;

import br.com.streaming.util.Validador;
import java.util.ArrayList;
import java.util.List;

public abstract class Usuario {

    private String id;
    private String nome;
    private String email;
    protected List<Playlist> playlists;
    protected List<Musica>   historico;

    public Usuario(String id, String nome, String email) {
        if (!Validador.emailValido(email))
            throw new IllegalArgumentException("E-mail invalido: " + email);
        if (!Validador.nomeValido(nome))
            throw new IllegalArgumentException("Nome invalido: " + nome);
        this.id        = id;
        this.nome      = nome;
        this.email     = email;
        this.playlists = new ArrayList<>();
        this.historico = new ArrayList<>();
    }

    public String getId()                  { return id; }
    public String getNome()                { return nome; }
    public String getEmail()               { return email; }
    public List<Playlist> getPlaylists()   { return playlists; }
    public List<Musica>   getHistorico()   { return historico; }

    public void criarPlaylist(Playlist playlist) {
        playlists.add(playlist);
        System.out.println("Playlist \"" + playlist.getTitulo() + "\" criada por " + nome);
    }

    // Sobrecarga: criar playlist pelo nome (retorna a playlist criada)
    public Playlist criarPlaylist(String nomePlaylist) {
        Playlist p = new Playlist(nomePlaylist);
        playlists.add(p);
        System.out.println("Playlist \"" + nomePlaylist + "\" criada por " + nome);
        return p;
    }

    public void registrarHistorico(Musica musica) {
        historico.add(musica);
    }

    public abstract void reproduzirMusica(Musica musica);
    public abstract String getTipoPlano();
    public abstract int    getLimiteDownloads();
    public abstract boolean podeOuvirSemAnuncios();

    public void exibirPerfil() {
        System.out.println("\nUsuario : " + nome + " (" + getTipoPlano() + ")");
        System.out.println("  Email    : " + email);
        System.out.println("  Playlists: " + playlists.size());
        System.out.println("  Ouvidas  : " + historico.size());
    }

    @Override
    public String toString() {
        return nome + " [" + getTipoPlano() + "] - " + email;
    }
}
