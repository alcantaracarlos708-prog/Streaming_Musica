package br.com.streaming.servico;

import br.com.streaming.modelo.Musica;
import br.com.streaming.modelo.Usuario;
import java.util.ArrayList;
import java.util.List;

public class GeradorRecomendacoes {

    /**
     * Exibe recomendações baseadas no histórico do usuário.
     * Se o usuário ouviu músicas de um gênero, recomenda outras do mesmo gênero.
     */
    public static void exibirRecomendacoes(Usuario usuario, List<Musica> catalogo) {
        System.out.println("\n--- Recomendacoes para " + usuario.getNome() + " ---");

        List<Musica> historico = usuario.getHistorico();
        if (historico.isEmpty()) {
            System.out.println("  Ouca mais musicas para receber recomendacoes personalizadas.");
            return;
        }

        // Coleta gêneros do histórico
        List<String> generosFavoritos = new ArrayList<>();
        for (Musica m : historico) {
            if (!generosFavoritos.contains(m.getGenero())) {
                generosFavoritos.add(m.getGenero());
            }
        }

        // Recomenda músicas do catálogo que não estão no histórico
        int recomendadas = 0;
        for (Musica m : catalogo) {
            boolean jaOuviu = historico.stream()
                    .anyMatch(h -> h.getTitulo().equalsIgnoreCase(m.getTitulo()));
            if (!jaOuviu && generosFavoritos.contains(m.getGenero())) {
                System.out.print("  ★ ");
                m.exibir();
                recomendadas++;
            }
        }

        if (recomendadas == 0) {
            System.out.println("  Sem novas recomendacoes no momento. Explore novos generos!");
        }
    }
}
