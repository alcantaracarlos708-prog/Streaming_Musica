public class Musica {
    private String titulo;
    private String artista;
    private int duracaoSegundos;
    private String genero;

    private static final String[] GENEROS_VALIDOS = {"Pop", "Rock", "Jazz", "Eletrônica", "Hip-Hop", "Clássica"};

    
    public Musica() {}

   
    public Musica(String titulo, String artista, int duracaoSegundos, String genero) {
        setTitulo(titulo);
        setArtista(artista);
        setDuracaoSegundos(duracaoSegundos);
        setGenero(genero);
    }

   
    public String getTitulo() { return titulo; }
    public String getArtista() { return artista; }
    public int getDuracaoSegundos() { return duracaoSegundos; }
    public String getGenero() { return genero; }

    
    public void setTitulo(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("Titulo nao pode ser nulo ou vazio.");
        }
        this.titulo = titulo.trim();
    }

    public void setArtista(String artista) {
        if (artista == null || artista.trim().isEmpty()) {
            throw new IllegalArgumentException("Artista nao pode ser nulo ou vazio.");
        }
        this.artista = artista.trim();
    }

    public void setDuracaoSegundos(int duracaoSegundos) {
        if (duracaoSegundos <= 0 || duracaoSegundos >= 3600) {
            throw new IllegalArgumentException("Duracao deve ser entre 1 e 3599 segundos.");
        }
        this.duracaoSegundos = duracaoSegundos;
    }

    public void setGenero(String genero) {
        for (String g : GENEROS_VALIDOS) {
            if (g.equalsIgnoreCase(genero)) {
                this.genero = g;
                return;
            }
        }
        throw new IllegalArgumentException("Genero invalido. Use: Pop, Rock, Jazz, Eletrônica, Hip-Hop, Clássica.");
    }

  
    public void exibir() {
        System.out.println("Titulo: " + titulo + " | Artista: " + artista +
                " | Duracao: " + getDuracaoFormatada() + " | Genero: " + genero);
    }

    public String getDuracaoFormatada() {
        int minutos = duracaoSegundos / 60;
        int segundos = duracaoSegundos % 60;
        return String.format("%d:%02d", minutos, segundos);
    }

    public boolean contemTitulo(String busca) {
        return titulo.toLowerCase().contains(busca.toLowerCase());
    }

    public boolean contemArtista(String busca) {
        return artista.toLowerCase().contains(busca.toLowerCase());
    }
}