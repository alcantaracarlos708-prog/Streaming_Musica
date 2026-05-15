package br.com.streaming.principal;

import br.com.streaming.modelo.*;
import br.com.streaming.servico.GeradorRecomendacoes;
import br.com.streaming.util.RelatorioUsuario;

import java.util.List;
import java.util.Scanner;

public class StreamingMusica {

    private static final Scanner sc = new Scanner(System.in);
    private static Catalogo catalogo = new Catalogo();
    private static Usuario usuarioLogado = null;

    // =========================================================
    // MAIN
    // =========================================================
    public static void main(String[] args) {
        cabecalho("SISTEMA DE STREAMING DE MUSICA");
        boolean rodando = true;
        while (rodando) {
            exibirMenuPrincipal();
            int op = lerInt("Opcao");
            switch (op) {
                case 1 -> menuCadastrarUsuario();
                case 2 -> menuLogarUsuario();
                case 3 -> menuCatalogo();
                case 4 -> { exigirLogin(); if (usuarioLogado != null) menuUsuario(); }
                case 0 -> { System.out.println("\nAte logo!"); rodando = false; }
                default -> System.out.println("Opcao invalida.");
            }
        }
        sc.close();
    }

    // =========================================================
    // MENUS PRINCIPAIS
    // =========================================================
    private static void exibirMenuPrincipal() {
        String logado = usuarioLogado != null
            ? " | Logado: " + usuarioLogado.getNome() + " (" + usuarioLogado.getTipoPlano() + ")"
            : " | Nenhum usuario logado";
        separador("MENU PRINCIPAL" + logado);
        System.out.println("  1. Cadastrar usuario");
        System.out.println("  2. Entrar com usuario");
        System.out.println("  3. Gerenciar catalogo de musicas");
        System.out.println("  4. Minha conta");
        System.out.println("  0. Sair");
    }

    // =========================================================
    // CADASTRO / LOGIN
    // =========================================================
    private static void menuCadastrarUsuario() {
        cabecalho("CADASTRAR USUARIO");
        String nome  = lerTexto("Nome");
        String email = lerTexto("Email");
        System.out.println("  Tipo de plano:");
        System.out.println("  1. Free");
        System.out.println("  2. Premium");
        int tipo = lerInt("Tipo");
        try {
            if (tipo == 1) {
                String id = gerarId("U");
                usuarioLogado = new UsuarioFree(id, nome, email);
                System.out.println("\nUsuario Free criado e logado: " + nome);
            } else if (tipo == 2) {
                double valor = lerDouble("Valor do plano (ex: 24.90)");
                String id = gerarId("U");
                usuarioLogado = new UsuarioPremium(id, nome, email, valor);
                System.out.println("\nUsuario Premium criado e logado: " + nome);
            } else {
                System.out.println("Tipo invalido.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void menuLogarUsuario() {
        cabecalho("ENTRAR");
        System.out.println("Digite seus dados para criar/acessar sua conta.");
        menuCadastrarUsuario();
    }

    // =========================================================
    // CATALOGO
    // =========================================================
    private static void menuCatalogo() {
        boolean voltar = false;
        while (!voltar) {
            cabecalho("CATALOGO DE MUSICAS (" + catalogo.getTamanho() + " musica(s))");
            System.out.println("  1. Adicionar musica ao catalogo");
            System.out.println("  2. Listar todas as musicas");
            System.out.println("  3. Buscar por titulo");
            System.out.println("  4. Buscar por artista");
            System.out.println("  5. Filtrar por genero");
            System.out.println("  6. Filtrar por duracao");
            System.out.println("  0. Voltar");
            int op = lerInt("Opcao");
            switch (op) {
                case 1 -> adicionarMusicaCatalogo();
                case 2 -> listarCatalogo();
                case 3 -> {
                    String t = lerTexto("Titulo (ou trecho)");
                    catalogo.exibirResultados("Busca: \"" + t + "\"", catalogo.buscarPorTitulo(t));
                }
                case 4 -> {
                    String a = lerTexto("Artista (ou trecho)");
                    catalogo.exibirResultados("Busca: \"" + a + "\"", catalogo.buscarPorArtista(a));
                }
                case 5 -> {
                    System.out.println("Generos: Pop | Rock | Jazz | Eletronica | Hip-Hop | Classica");
                    String g = lerTexto("Genero");
                    catalogo.exibirResultados("Genero: " + g, catalogo.filtrarPorGenero(g));
                }
                case 6 -> {
                    int min = lerInt("Duracao minima (segundos)");
                    int max = lerInt("Duracao maxima (segundos)");
                    catalogo.exibirResultados("Duracao " + min + "s - " + max + "s",
                            catalogo.filtrarPorDuracao(min, max));
                }
                case 0 -> voltar = true;
                default -> System.out.println("Opcao invalida.");
            }
        }
    }

    private static void adicionarMusicaCatalogo() {
        cabecalho("ADICIONAR MUSICA");
        try {
            String id     = gerarId("M");
            String titulo = lerTexto("Titulo");
            String artista= lerTexto("Artista");
            String album  = lerTexto("Album");
            int    ano    = lerInt("Ano");
            int    durSeg = lerInt("Duracao em segundos (ex: 210)");
            System.out.println("Generos: Pop | Rock | Jazz | Eletronica | Hip-Hop | Classica");
            String genero = lerTexto("Genero");
            double tamanho= lerDouble("Tamanho em MB (ex: 4.5)");
            Musica m = new Musica(id, titulo, artista, album, ano, durSeg, genero, tamanho);
            catalogo.adicionarMusica(m);
            System.out.println("\nMusica adicionada com sucesso! ID: " + id);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao cadastrar: " + e.getMessage());
        }
    }

    private static void listarCatalogo() {
        List<Musica> todas = catalogo.getTodas();
        System.out.println("\n--- CATALOGO (" + todas.size() + " musica(s)) ---");
        if (todas.isEmpty()) { System.out.println("  Catalogo vazio."); return; }
        for (int i = 0; i < todas.size(); i++) {
            System.out.print("  " + (i + 1) + ". ");
            todas.get(i).exibir();
        }
    }

    // =========================================================
    // MENU DO USUARIO LOGADO
    // =========================================================
    private static void menuUsuario() {
        boolean voltar = false;
        while (!voltar) {
            cabecalho("MINHA CONTA — " + usuarioLogado.getNome());
            System.out.println("  1. Ver meu perfil");
            System.out.println("  2. Reproduzir musica");
            System.out.println("  3. Gerenciar playlists");
            System.out.println("  4. Ver historico");
            System.out.println("  5. Ver recomendacoes");
            System.out.println("  6. Ver relatorio");
            if (usuarioLogado instanceof UsuarioPremium)
                System.out.println("  7. Downloads (Premium)");
            System.out.println("  0. Voltar");
            int op = lerInt("Opcao");
            switch (op) {
                case 1 -> usuarioLogado.exibirPerfil();
                case 2 -> menuReproduzir();
                case 3 -> menuPlaylists();
                case 4 -> exibirHistorico();
                case 5 -> GeradorRecomendacoes.exibirRecomendacoes(usuarioLogado, catalogo.getTodas());
                case 6 -> new RelatorioUsuario(usuarioLogado).exibir();
                case 7 -> { if (usuarioLogado instanceof UsuarioPremium p) menuDownloads(p); }
                case 0 -> voltar = true;
                default -> System.out.println("Opcao invalida.");
            }
        }
    }

    // =========================================================
    // REPRODUCAO
    // =========================================================
    private static void menuReproduzir() {
        if (catalogo.getTamanho() == 0) { System.out.println("Catalogo vazio. Adicione musicas primeiro."); return; }
        listarCatalogo();
        int idx = lerInt("Numero da musica (0 para cancelar)");
        if (idx == 0) return;
        List<Musica> todas = catalogo.getTodas();
        if (idx < 1 || idx > todas.size()) { System.out.println("Numero invalido."); return; }
        Musica m = todas.get(idx - 1);
        usuarioLogado.reproduzirMusica(m);

        System.out.println("  [p] Pausar  [s] Parar  [Enter] Continuar");
        String cmd = sc.nextLine().trim().toLowerCase();
        if (cmd.equals("p")) { m.pausar(); System.out.println("  Pressione Enter para retomar..."); sc.nextLine(); m.reproduzir(); }
        else if (cmd.equals("s")) m.parar();
    }

    // =========================================================
    // PLAYLISTS
    // =========================================================
    private static void menuPlaylists() {
        boolean voltar = false;
        while (!voltar) {
            cabecalho("PLAYLISTS");
            List<Playlist> pls = usuarioLogado.getPlaylists();
            if (pls.isEmpty()) System.out.println("  Nenhuma playlist criada ainda.");
            else for (int i = 0; i < pls.size(); i++) System.out.println("  " + (i+1) + ". " + pls.get(i));
            System.out.println("\n  1. Criar nova playlist");
            System.out.println("  2. Adicionar musica a playlist");
            System.out.println("  3. Listar musicas de uma playlist");
            System.out.println("  4. Reproduzir playlist");
            System.out.println("  5. Remover musica de playlist");
            System.out.println("  0. Voltar");
            int op = lerInt("Opcao");
            switch (op) {
                case 1 -> criarPlaylist();
                case 2 -> adicionarMusicaPlaylist();
                case 3 -> listarPlaylist();
                case 4 -> reproduzirPlaylist();
                case 5 -> removerMusicaPlaylist();
                case 0 -> voltar = true;
                default -> System.out.println("Opcao invalida.");
            }
        }
    }

    private static void criarPlaylist() {
        String nome = lerTexto("Nome da playlist");
        String gen  = lerTexto("Genero (ou 'Variado')");
        String id   = gerarId("PL");
        Playlist pl = new PlaylistPersonalizada(id, nome, usuarioLogado.getNome(), gen);
        usuarioLogado.criarPlaylist(pl);
    }

    private static Playlist selecionarPlaylist() {
        List<Playlist> pls = usuarioLogado.getPlaylists();
        if (pls.isEmpty()) { System.out.println("Nenhuma playlist. Crie uma primeiro."); return null; }
        for (int i = 0; i < pls.size(); i++) System.out.println("  " + (i+1) + ". " + pls.get(i));
        int idx = lerInt("Numero da playlist");
        if (idx < 1 || idx > pls.size()) { System.out.println("Numero invalido."); return null; }
        return pls.get(idx - 1);
    }

    private static void adicionarMusicaPlaylist() {
        Playlist pl = selecionarPlaylist();
        if (pl == null) return;
        listarCatalogo();
        int idx = lerInt("Numero da musica");
        List<Musica> todas = catalogo.getTodas();
        if (idx < 1 || idx > todas.size()) { System.out.println("Numero invalido."); return; }
        pl.adicionarMusica(todas.get(idx - 1));
    }

    private static void listarPlaylist() {
        Playlist pl = selecionarPlaylist();
        if (pl != null) pl.listar();
    }

    private static void reproduzirPlaylist() {
        Playlist pl = selecionarPlaylist();
        if (pl != null) pl.reproduzir();
    }

    private static void removerMusicaPlaylist() {
        Playlist pl = selecionarPlaylist();
        if (pl == null) return;
        pl.listar();
        int idx = lerInt("Numero da musica para remover");
        pl.removerMusica(idx - 1);
    }

    // =========================================================
    // HISTORICO
    // =========================================================
    private static void exibirHistorico() {
        List<Musica> hist = usuarioLogado.getHistorico();
        System.out.println("\n--- HISTORICO (" + hist.size() + " musica(s)) ---");
        if (hist.isEmpty()) { System.out.println("  Nenhuma musica ouvida ainda."); return; }
        for (int i = 0; i < hist.size(); i++)
            System.out.println("  " + (i+1) + ". " + hist.get(i));
    }

    // =========================================================
    // DOWNLOADS (PREMIUM)
    // =========================================================
    private static void menuDownloads(UsuarioPremium premium) {
        boolean voltar = false;
        while (!voltar) {
            cabecalho("DOWNLOADS");
            System.out.println("  1. Baixar musica");
            System.out.println("  2. Ver musicas baixadas");
            System.out.println("  3. Reproduzir offline");
            System.out.println("  0. Voltar");
            int op = lerInt("Opcao");
            switch (op) {
                case 1 -> { listarCatalogo(); int idx = lerInt("Numero da musica"); List<Musica> t = catalogo.getTodas(); if (idx >= 1 && idx <= t.size()) premium.baixarMusica(t.get(idx-1)); else System.out.println("Numero invalido."); }
                case 2 -> premium.listarMusicasBaixadas();
                case 3 -> { listarCatalogo(); int idx = lerInt("Numero da musica"); List<Musica> t = catalogo.getTodas(); if (idx >= 1 && idx <= t.size()) premium.reproduzirMusica(t.get(idx-1), true); else System.out.println("Numero invalido."); }
                case 0 -> voltar = false; // sai do loop
                default -> System.out.println("Opcao invalida.");
            }
            if (op == 0) voltar = true;
        }
    }

    // =========================================================
    // UTILITÁRIOS DE ENTRADA
    // =========================================================
    private static String lerTexto(String campo) {
        System.out.print("  " + campo + ": ");
        return sc.nextLine().trim();
    }

    private static int lerInt(String campo) {
        while (true) {
            System.out.print("  " + campo + ": ");
            String linha = sc.nextLine().trim();
            try { return Integer.parseInt(linha); }
            catch (NumberFormatException e) { System.out.println("  Digite um numero inteiro valido."); }
        }
    }

    private static double lerDouble(String campo) {
        while (true) {
            System.out.print("  " + campo + ": ");
            String linha = sc.nextLine().trim().replace(",", ".");
            try { return Double.parseDouble(linha); }
            catch (NumberFormatException e) { System.out.println("  Digite um numero valido (ex: 4.5)."); }
        }
    }

    private static int contadorId = 1;
    private static String gerarId(String prefixo) {
        return prefixo + String.format("%03d", contadorId++);
    }

    private static void exigirLogin() {
        if (usuarioLogado == null)
            System.out.println("\nVoce precisa cadastrar ou entrar com um usuario primeiro (opcao 1 ou 2).");
    }

    // =========================================================
    // UTILITÁRIOS VISUAIS
    // =========================================================
    private static void cabecalho(String titulo) {
        System.out.println("\n===========================================");
        System.out.println("  " + titulo);
        System.out.println("===========================================");
    }

    private static void separador(String titulo) {
        System.out.println("\n--- " + titulo + " ---");
    }
}
