package br.com.streaming.modelo;

import br.com.streaming.servico.Baixavel;
import java.util.ArrayList;

public class UsuarioPremium extends Usuario implements Baixavel {

    private double valorPlano;
    private ArrayList<Musica> musicasBaixadas;

    public UsuarioPremium(String id, String nome, String email, double valorPlano) {
        super(id, nome, email);
        this.valorPlano     = valorPlano;
        this.musicasBaixadas = new ArrayList<>();
    }

    public double getValorPlano() { return valorPlano; }

    @Override
    public String  getTipoPlano()         { return "Premium"; }
    @Override
    public int     getLimiteDownloads()   { return Integer.MAX_VALUE; }
    @Override
    public boolean podeOuvirSemAnuncios() { return true; }

    @Override
    public void reproduzirMusica(Musica musica) {
        if (musica == null) { System.out.println("Musica invalida."); return; }
        System.out.println("Reproduzindo em ALTA QUALIDADE: "
                + musica.getTitulo() + " - " + musica.getArtista());
        musica.reproduzir();
        System.out.println("  [Qualidade: Alta | Plano: Premium]");
        registrarHistorico(musica);
    }

    // Sobrecarga: reprodução offline
    public void reproduzirMusica(Musica musica, boolean modoOffline) {
        if (modoOffline) {
            boolean encontrada = musicasBaixadas.stream()
                    .anyMatch(m -> m.getTitulo().equalsIgnoreCase(musica.getTitulo()));
            if (!encontrada) {
                System.out.println("Musica nao esta baixada para reproducao offline.");
                return;
            }
            System.out.println("[OFFLINE] Reproduzindo: " + musica.getTitulo());
            registrarHistorico(musica);
        } else {
            reproduzirMusica(musica);
        }
    }

    // Implementação de Baixavel
    @Override
    public void baixarMusica(Musica musica) {
        if (musica == null) { System.out.println("Musica invalida."); return; }
        boolean jaExiste = musicasBaixadas.stream()
                .anyMatch(m -> m.getTitulo().equalsIgnoreCase(musica.getTitulo())
                            && m.getArtista().equalsIgnoreCase(musica.getArtista()));
        if (jaExiste) {
            System.out.println("Musica '" + musica.getTitulo() + "' ja esta baixada!");
            return;
        }
        musicasBaixadas.add(musica);
        System.out.println("Download concluido: " + musica.getTitulo()
                + " - " + musica.getArtista());
    }

    @Override
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
        System.out.println("Total: " + musicasBaixadas.size() + " musica(s).");
    }

    @Override
    public int getQuantidadeMusicasBaixadas() {
        return musicasBaixadas.size();
    }
}
