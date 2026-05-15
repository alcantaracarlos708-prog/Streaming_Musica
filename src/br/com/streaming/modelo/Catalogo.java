package br.com.streaming.modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Gerencia o acervo de músicas e oferece busca e filtros.
 */
public class Catalogo {

    private List<Musica> musicas;

    public Catalogo() {
        this.musicas = new ArrayList<>();
    }

    // --- Gerenciamento ---

    public void adicionarMusica(Musica musica) {
        if (musica == null) {
            System.out.println("Musica invalida. Nao adicionada.");
            return;
        }
        musicas.add(musica);
    }

    public void removerMusica(String id) {
        musicas.removeIf(m -> m.getId().equalsIgnoreCase(id));
    }

    public List<Musica> getTodas() {
        return new ArrayList<>(musicas);
    }

    public int getTamanho() {
        return musicas.size();
    }

    // --- Buscas ---

    /** Busca músicas cujo título contém o termo (ignora maiúsculas). */
    public List<Musica> buscarPorTitulo(String termo) {
        List<Musica> resultado = new ArrayList<>();
        for (Musica m : musicas) {
            if (m.contemTitulo(termo)) resultado.add(m);
        }
        return resultado;
    }

    /** Busca músicas cujo artista contém o termo (ignora maiúsculas). */
    public List<Musica> buscarPorArtista(String termo) {
        List<Musica> resultado = new ArrayList<>();
        for (Musica m : musicas) {
            if (m.contemArtista(termo)) resultado.add(m);
        }
        return resultado;
    }

    // --- Filtros ---

    /** Retorna todas as músicas de um gênero específico. */
    public List<Musica> filtrarPorGenero(String genero) {
        List<Musica> resultado = new ArrayList<>();
        for (Musica m : musicas) {
            if (m.getGenero().equalsIgnoreCase(genero)) resultado.add(m);
        }
        return resultado;
    }

    /** Retorna músicas com duração entre min e max segundos. */
    public List<Musica> filtrarPorDuracao(int minSegundos, int maxSegundos) {
        List<Musica> resultado = new ArrayList<>();
        for (Musica m : musicas) {
            if (m.getDuracaoSegundos() >= minSegundos && m.getDuracaoSegundos() <= maxSegundos)
                resultado.add(m);
        }
        return resultado;
    }

    // --- Exibição ---

    public void exibirResultados(String titulo, List<Musica> lista) {
        System.out.println("\n--- " + titulo + " (" + lista.size() + " resultado(s)) ---");
        if (lista.isEmpty()) {
            System.out.println("  Nenhuma musica encontrada.");
            return;
        }
        for (Musica m : lista) {
            System.out.print("  ");
            m.exibir();
        }
    }
}
