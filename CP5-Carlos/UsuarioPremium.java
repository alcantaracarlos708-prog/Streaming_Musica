import java.util.ArrayList;

public class UsuarioPremium extends Usuario {

    private String tipoPlano; // Mensal, Anual, Familiar
    private ArrayList<Musica> musicasBaixadas;

    public UsuarioPremium(String nome, String email, String tipoPlano) {
        super(nome, email);
        setTipoPlano(tipoPlano);
        this.musicasBaixadas = new ArrayList<>();
    }

    public String getTipoPlano() { return tipoPlano; }

    public void setTipoPlano(String tipoPlano) {
        if (tipoPlano == null || tipoPlano.trim().isEmpty())
            throw new IllegalArgumentException("Tipo de plano nao pode ser vazio.");
        this.tipoPlano = tipoPlano.trim();
    }

    @Override
    public void reproduzirMusica(Musica musica) {
        if (musica == null) {
            System.out.println("Musica invalida.");
            return;
        }
        System.out.println("Reproduzindo em ALTA QUALIDADE: "
                + musica.getTitulo() + " - " + musica.getArtista());
        System.out.println("[Qualidade: Alta | Plano: " + tipoPlano + "]");
        historicoReproducao.add(musica);
    }

    public void baixarMusica(Musica musica) {
        if (musica == null) {
            System.out.println("Musica invalida.");
            return;
        }
        for (Musica m : musicasBaixadas) {
            if (m.getTitulo().equalsIgnoreCase(musica.getTitulo())
                    && m.getArtista().equalsIgnoreCase(musica.getArtista())) {
                System.out.println("Musica '" + musica.getTitulo() + "' ja esta baixada!");
                return;
            }
        }
        musicasBaixadas.add(musica);
        System.out.println("Musica baixada: " + musica.getTitulo()
                + " - " + musica.getArtista());
    }

    public void listarMusicasBaixadas() {
        System.out.println("\n--- MUSICAS BAIXADAS ---");
        if (musicasBaixadas.isEmpty()) {
            System.out.println("Nenhuma musica baixada ainda.");
            return;
        }
        for (int i = 0; i < musicasBaixadas.size(); i++) {
            System.out.print((i + 1) + ". ");
            musicasBaixadas.get(i).exibir();
        }
        System.out.println("Total: " + musicasBaixadas.size() + " musica(s) baixada(s).");
    }

    // Sobrecarga (overloading): reprodução offline
    public void reproduzirMusica(Musica musica, boolean modoOffline) {
        if (modoOffline) {
            boolean encontrada = false;
            for (Musica m : musicasBaixadas) {
                if (m.getTitulo().equalsIgnoreCase(musica.getTitulo())) {
                    encontrada = true;
                    break;
                }
            }
            if (!encontrada) {
                System.out.println("Musica nao esta baixada para reproducao offline.");
                return;
            }
            System.out.println("[OFFLINE] Reproduzindo: " + musica.getTitulo());
            historicoReproducao.add(musica);
        } else {
            reproduzirMusica(musica);
        }
    }

    public int getQuantidadeMusicasBaixadas() {
        return musicasBaixadas.size();
    }
}
