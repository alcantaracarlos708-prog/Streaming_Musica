package br.com.streaming.modelo;

import java.util.ArrayList;

public class Playlist {

    protected String id;
    protected String nome;
    protected String criadorNome;
    protected String genero;
    protected String descricao;
    protected ArrayList<Musica> musicas;

    public Playlist() {
        this.musicas = new ArrayList<>();
    }

    public Playlist(String nome) {
        this.musicas = new ArrayList<>();
        if (nome == null || nome.trim().isEmpty())
            throw new IllegalArgumentException("Nome da playlist nao pode ser nulo ou vazio.");
        this.nome = nome.trim();
    }

    public Playlist(String id, String nome, String criadorNome, String genero) {
        this.musicas     = new ArrayList<>();
        this.id          = id;
        this.criadorNome = criadorNome;
        this.genero      = genero;
        if (nome == null || nome.trim().isEmpty())
            throw new IllegalArgumentException("Nome da playlist nao pode ser nulo ou vazio.");
        this.nome = nome.trim();
    }

    // Getters / Setters
    public String getId()          { return id; }
    public String getNome()        { return nome; }
    public String getTitulo()      { return nome; }   // alias usado no main
    public String getCriadorNome() { return criadorNome; }
    public String getGenero()      { return genero; }
    public String getDescricao()   { return descricao; }

    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty())
            throw new IllegalArgumentException("Nome da playlist nao pode ser nulo ou vazio.");
        this.nome = nome.trim();
    }

    public void setDescricao(String descricao) { this.descricao = descricao; }

    // Operações
    public void adicionarMusica(Musica musica) {
        if (musica == null) {
            System.out.println("Musica invalida (null). Nao foi adicionada.");
            return;
        }
        musicas.add(musica);
        System.out.println("  + '" + musica.getTitulo() + "' adicionada a playlist '" + nome + "'");
    }

    public void removerMusica(int indice) {
        if (indice < 0 || indice >= musicas.size()) {
            System.out.println("Indice invalido.");
            return;
        }
        System.out.println("  - Musica removida: " + musicas.get(indice).getTitulo());
        musicas.remove(indice);
    }

    /** Lista todas as músicas da playlist (alias: listar) */
    public void listarMusicas() {
        System.out.println("--- Playlist: " + nome + " ---");
        if (descricao != null && !descricao.isEmpty())
            System.out.println("Descricao: " + descricao);
        if (musicas.isEmpty()) {
            System.out.println("  Nenhuma musica nesta playlist.");
        } else {
            for (int i = 0; i < musicas.size(); i++) {
                System.out.print("  " + (i + 1) + ". ");
                musicas.get(i).exibir();
            }
        }
    }

    public void listar() { listarMusicas(); }

    /** Polimorfismo: subclasses podem sobrescrever */
    public void reproduzir() {
        System.out.println("Reproduzindo playlist: " + nome);
        if (musicas.isEmpty()) {
            System.out.println("  Nenhuma musica nesta playlist.");
            return;
        }
        for (Musica m : musicas) {
            m.reproduzir();
        }
    }

    public int getDuracaoTotal()      { return musicas.stream().mapToInt(Musica::getDuracaoSegundos).sum(); }
    public int getQuantidadeMusicas() { return musicas.size(); }
    public ArrayList<Musica> getMusicas() { return musicas; }

    @Override
    public String toString() {
        return nome + " (" + musicas.size() + " musica(s))";
    }
}
