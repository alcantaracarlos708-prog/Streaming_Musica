package br.com.streaming.modelo;

import br.com.streaming.servico.Reproduzivel;

public class Musica implements Reproduzivel {

    private String id;
    private String titulo;
    private String artista;
    private String album;
    private int    ano;
    private int    duracaoSegundos;
    private String genero;
    private double tamanhoMb;

    private boolean reproduzindo = false;

    private static final String[] GENEROS_VALIDOS =
            {"Pop", "Rock", "Jazz", "Eletrônica", "Hip-Hop", "Clássica"};

    public Musica() {}

    public Musica(String id, String titulo, String artista, String album,
                  int ano, int duracaoSegundos, String genero, double tamanhoMb) {
        this.id    = id;
        this.album = album;
        this.ano   = ano;
        this.tamanhoMb = tamanhoMb;
        // validações inline para evitar this-escape no construtor
        if (titulo == null || titulo.trim().isEmpty())
            throw new IllegalArgumentException("Titulo nao pode ser nulo ou vazio.");
        this.titulo = titulo.trim();
        if (artista == null || artista.trim().isEmpty())
            throw new IllegalArgumentException("Artista nao pode ser nulo ou vazio.");
        this.artista = artista.trim();
        if (duracaoSegundos <= 0 || duracaoSegundos > 7200)
            throw new IllegalArgumentException("Duracao deve ser entre 1 e 7200 segundos.");
        this.duracaoSegundos = duracaoSegundos;
        boolean generoEncontrado = false;
        for (String g : GENEROS_VALIDOS) {
            if (g.equalsIgnoreCase(genero)) { this.genero = g; generoEncontrado = true; break; }
        }
        if (!generoEncontrado)
            throw new IllegalArgumentException("Genero invalido. Use: Pop, Rock, Jazz, Eletrônica, Hip-Hop, Clássica.");
    }

    // Getters
    public String getId()              { return id; }
    public String getTitulo()          { return titulo; }
    public String getArtista()         { return artista; }
    public String getAlbum()           { return album; }
    public int    getAno()             { return ano; }
    public int    getDuracaoSegundos() { return duracaoSegundos; }
    public String getGenero()          { return genero; }
    public double getTamanhoMb()       { return tamanhoMb; }

    // Setters com validação
    public void setTitulo(String titulo) {
        if (titulo == null || titulo.trim().isEmpty())
            throw new IllegalArgumentException("Titulo nao pode ser nulo ou vazio.");
        this.titulo = titulo.trim();
    }

    public void setArtista(String artista) {
        if (artista == null || artista.trim().isEmpty())
            throw new IllegalArgumentException("Artista nao pode ser nulo ou vazio.");
        this.artista = artista.trim();
    }

    public void setDuracaoSegundos(int duracaoSegundos) {
        if (duracaoSegundos <= 0 || duracaoSegundos > 7200)
            throw new IllegalArgumentException("Duracao deve ser entre 1 e 7200 segundos.");
        this.duracaoSegundos = duracaoSegundos;
    }

    public void setGenero(String genero) {
        for (String g : GENEROS_VALIDOS) {
            if (g.equalsIgnoreCase(genero)) {
                this.genero = g;
                return;
            }
        }
        throw new IllegalArgumentException(
                "Genero invalido. Use: Pop, Rock, Jazz, Eletrônica, Hip-Hop, Clássica.");
    }

    // Implementação de Reproduzivel
    @Override
    public void reproduzir() {
        reproduzindo = true;
        System.out.println("  ▶ " + titulo + " - " + artista
                + " [" + getDuracaoFormatada() + "]");
    }

    @Override
    public void pausar() {
        reproduzindo = false;
        System.out.println("  ⏸ Pausado: " + titulo);
    }

    @Override
    public void parar() {
        reproduzindo = false;
        System.out.println("  ⏹ Parado: " + titulo);
    }

    @Override
    public boolean isReproduzindo() {
        return reproduzindo;
    }

    // Utilitários
    public String getDuracaoFormatada() {
        int minutos  = duracaoSegundos / 60;
        int segundos = duracaoSegundos % 60;
        return String.format("%d:%02d", minutos, segundos);
    }

    public void exibir() {
        System.out.println("Titulo: " + titulo + " | Artista: " + artista
                + " | Album: " + album + " (" + ano + ")"
                + " | Duracao: " + getDuracaoFormatada()
                + " | Genero: " + genero
                + " | Tamanho: " + tamanhoMb + " MB");
    }

    public boolean contemTitulo(String busca) {
        return titulo.toLowerCase().contains(busca.toLowerCase());
    }

    public boolean contemArtista(String busca) {
        return artista.toLowerCase().contains(busca.toLowerCase());
    }

    @Override
    public String toString() {
        return titulo + " - " + artista;
    }
}
