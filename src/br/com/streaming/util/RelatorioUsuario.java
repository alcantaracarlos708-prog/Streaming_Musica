package br.com.streaming.util;

import br.com.streaming.modelo.Musica;
import br.com.streaming.modelo.Usuario;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Gera estatísticas e relatórios de um usuário.
 */
public class RelatorioUsuario {

    private Usuario usuario;

    public RelatorioUsuario(Usuario usuario) {
        if (usuario == null) throw new IllegalArgumentException("Usuario nao pode ser nulo.");
        this.usuario = usuario;
    }

    /** Total de segundos ouvidos no histórico. */
    public int getTotalSegundosOuvidos() {
        int total = 0;
        for (Musica m : usuario.getHistorico()) total += m.getDuracaoSegundos();
        return total;
    }

    /** Gênero mais ouvido com base no histórico. */
    public String getGeneroFavorito() {
        List<Musica> historico = usuario.getHistorico();
        if (historico.isEmpty()) return "Sem dados";

        Map<String, Integer> contagem = new HashMap<>();
        for (Musica m : historico) {
            contagem.put(m.getGenero(), contagem.getOrDefault(m.getGenero(), 0) + 1);
        }

        String favorito = "";
        int max = 0;
        for (Map.Entry<String, Integer> entry : contagem.entrySet()) {
            if (entry.getValue() > max) {
                max = entry.getValue();
                favorito = entry.getKey();
            }
        }
        return favorito;
    }

    /** Música mais ouvida (primeira ocorrência com maior frequência). */
    public Musica getMusicaMaisOuvida() {
        List<Musica> historico = usuario.getHistorico();
        if (historico.isEmpty()) return null;

        Map<String, Integer> contagem = new HashMap<>();
        for (Musica m : historico)
            contagem.put(m.getTitulo(), contagem.getOrDefault(m.getTitulo(), 0) + 1);

        int max = 0;
        String tituloMaisOuvido = "";
        for (Map.Entry<String, Integer> e : contagem.entrySet()) {
            if (e.getValue() > max) { max = e.getValue(); tituloMaisOuvido = e.getKey(); }
        }

        for (Musica m : historico) {
            if (m.getTitulo().equals(tituloMaisOuvido)) return m;
        }
        return null;
    }

    /** Imprime o relatório completo no console. */
    public void exibir() {
        int totalSeg  = getTotalSegundosOuvidos();
        String genero = getGeneroFavorito();
        Musica top    = getMusicaMaisOuvida();

        System.out.println("\n========================================");
        System.out.println("  RELATORIO — " + usuario.getNome().toUpperCase());
        System.out.println("========================================");
        System.out.println("  Plano          : " + usuario.getTipoPlano());
        System.out.println("  Musicas ouvidas: " + usuario.getHistorico().size());
        System.out.println("  Tempo total    : " + FormatadorTempo.formatarLongo(totalSeg));
        System.out.println("  Genero favorito: " + genero);
        if (top != null) {
            System.out.println("  Musica mais ouvida: " + top.getTitulo() + " - " + top.getArtista());
        }
        System.out.println("  Playlists criadas: " + usuario.getPlaylists().size());
        System.out.println("========================================");
    }
}
