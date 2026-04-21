import java.util.ArrayList;
import java.util.Scanner;

public class StreamingMusica {

    static Scanner scanner = new Scanner(System.in);
    static ArrayList<Musica> bancoMusicas = new ArrayList<>();

    // Referências para os dois tipos de usuário
    static UsuarioFree usuarioFree = null;
    static UsuarioPremium usuarioPremium = null;
    static boolean isPremium = false;

    public static void main(String[] args) {
        cadastrarUsuario();
        popularBancoDeMusicas(); // Músicas iniciais para demonstração

        int opcao;
        do {
            if (isPremium) {
                exibirMenuPremium();
            } else {
                exibirMenuFree();
            }

            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                opcao = -1;
            }

            if (isPremium) {
                processarOpcaoPremium(opcao);
            } else {
                processarOpcaoFree(opcao);
            }

        } while (true);
    }

    // ─────────────────────────────────────────────
    // CADASTRO INICIAL
    // ─────────────────────────────────────────────

    static void cadastrarUsuario() {
        System.out.println("\n=== BEM-VINDO AO STREAMING DE MUSICA ===");

        String nome = "";
        while (nome.isEmpty()) {
            System.out.print("Digite seu nome: ");
            nome = scanner.nextLine().trim();
            if (nome.isEmpty()) System.out.println("Nome nao pode ser vazio.");
        }

        String email = "";
        while (email.isEmpty()) {
            System.out.print("Digite seu email: ");
            email = scanner.nextLine().trim();
            if (email.isEmpty()) System.out.println("Email nao pode ser vazio.");
        }

        System.out.println("\nEscolha o tipo de conta:");
        System.out.println("1. Free (Gratuito)");
        System.out.println("2. Premium (Pago)");
        System.out.print("Escolha: ");

        int tipo = -1;
        try { tipo = Integer.parseInt(scanner.nextLine()); } catch (Exception e) {}

        if (tipo == 2) {
            isPremium = true;
            System.out.println("\nEscolha o plano Premium:");
            System.out.println("1. Mensal  (R$ 19,90)");
            System.out.println("2. Anual   (R$ 199,00)");
            System.out.println("3. Familiar (R$ 29,90)");
            System.out.print("Escolha: ");

            int planoOp = -1;
            try { planoOp = Integer.parseInt(scanner.nextLine()); } catch (Exception e) {}

            String plano;
            switch (planoOp) {
                case 1: plano = "Mensal"; break;
                case 2: plano = "Anual"; break;
                case 3: plano = "Familiar"; break;
                default: plano = "Mensal"; System.out.println("Opcao invalida. Plano Mensal selecionado.");
            }

            usuarioPremium = new UsuarioPremium(nome, email, plano);
            System.out.println("\nConta Premium (" + plano + ") criada para " + nome + "!");

        } else {
            isPremium = false;
            usuarioFree = new UsuarioFree(nome, email);
            System.out.println("\nConta Free criada para " + nome + "!");
        }
    }

    // ─────────────────────────────────────────────
    // MENUS
    // ─────────────────────────────────────────────

    static void exibirMenuFree() {
        System.out.println("\n=== MENU FREE - " + usuarioFree.getNome() + " ===");
        System.out.println("Reproducoes: " + usuarioFree.getContadorReproducoes()
                + "/" + usuarioFree.getLimiteReproducoes());
        System.out.println("Playlists: " + usuarioFree.getQuantidadePlaylists() + "/3");
        System.out.println("---");
        System.out.println("1. Cadastrar musica");
        System.out.println("2. Listar todas as musicas");
        System.out.println("3. Reproduzir musica");
        System.out.println("4. Ver historico");
        System.out.println("5. Criar playlist (max. 3)");
        System.out.println("6. Gerenciar playlists");
        System.out.println("7. Buscar musica");
        System.out.println("8. Fazer upgrade para Premium");
        System.out.println("0. Sair");
        System.out.print("Escolha: ");
    }

    static void exibirMenuPremium() {
        System.out.println("\n=== MENU PREMIUM - " + usuarioPremium.getNome()
                + " [" + usuarioPremium.getTipoPlano() + "] ===");
        System.out.println("Playlists: " + usuarioPremium.getQuantidadePlaylists()
                + " | Downloads: " + usuarioPremium.getQuantidadeMusicasBaixadas());
        System.out.println("---");
        System.out.println("1. Cadastrar musica");
        System.out.println("2. Listar todas as musicas");
        System.out.println("3. Reproduzir musica (Alta Qualidade)");
        System.out.println("4. Ver historico");
        System.out.println("5. Criar playlist (ilimitado)");
        System.out.println("6. Gerenciar playlists");
        System.out.println("7. Buscar musica");
        System.out.println("8. Baixar musica");
        System.out.println("9. Ver musicas baixadas");
        System.out.println("0. Sair");
        System.out.print("Escolha: ");
    }

    // ─────────────────────────────────────────────
    // PROCESSAMENTO DE OPÇÕES
    // ─────────────────────────────────────────────

    static void processarOpcaoFree(int op) {
        switch (op) {
            case 1: cadastrarMusica(); break;
            case 2: listarMusicas(); break;
            case 3: reproduzirMusicaFree(); break;
            case 4: usuarioFree.exibirHistorico(); break;
            case 5: criarPlaylist(false); break;
            case 6: gerenciarPlaylists(false); break;
            case 7: buscarMusica(); break;
            case 8: fazerUpgradePremium(); break;
            case 0: System.out.println("Ate logo, " + usuarioFree.getNome() + "!"); System.exit(0);
            default: System.out.println("Opcao invalida!");
        }
    }

    static void processarOpcaoPremium(int op) {
        switch (op) {
            case 1: cadastrarMusica(); break;
            case 2: listarMusicas(); break;
            case 3: reproduzirMusicaPremium(); break;
            case 4: usuarioPremium.exibirHistorico(); break;
            case 5: criarPlaylist(true); break;
            case 6: gerenciarPlaylists(true); break;
            case 7: buscarMusica(); break;
            case 8: baixarMusicaPremium(); break;
            case 9: usuarioPremium.listarMusicasBaixadas(); break;
            case 0: System.out.println("Ate logo, " + usuarioPremium.getNome() + "!"); System.exit(0);
            default: System.out.println("Opcao invalida!");
        }
    }

    // ─────────────────────────────────────────────
    // AÇÕES COMPARTILHADAS
    // ─────────────────────────────────────────────

    static void fazerUpgradePremium() {
        System.out.println("\n=== UPGRADE PARA PREMIUM ===");
        System.out.println("Escolha o plano Premium:");
        System.out.println("1. Mensal  (R$ 19,90)");
        System.out.println("2. Anual   (R$ 199,00)");
        System.out.println("3. Familiar (R$ 29,90)");
        System.out.print("Escolha: ");

        int planoOp = -1;
        try { planoOp = Integer.parseInt(scanner.nextLine()); } catch (Exception e) {}

        String plano;
        switch (planoOp) {
            case 1: plano = "Mensal"; break;
            case 2: plano = "Anual"; break;
            case 3: plano = "Familiar"; break;
            default: plano = "Mensal"; System.out.println("Opcao invalida. Plano Mensal selecionado.");
        }

        // Cria conta Premium mantendo nome e email do usuário Free
        usuarioPremium = new UsuarioPremium(usuarioFree.getNome(), usuarioFree.getEmail(), plano);
        isPremium = true;
        System.out.println("\nUpgrade realizado com sucesso! Bem-vindo ao Premium (" + plano + ")!");
    }

    static void cadastrarMusica() {
        try {
            System.out.print("Titulo: ");
            String titulo = scanner.nextLine();
            System.out.print("Artista: ");
            String artista = scanner.nextLine();
            System.out.print("Duracao (segundos): ");
            int duracao = Integer.parseInt(scanner.nextLine());
            System.out.print("Genero (Pop, Rock, Jazz, Eletronica, Hip-Hop, Classica): ");
            String genero = scanner.nextLine();

            Musica m = new Musica(titulo, artista, duracao, genero);
            bancoMusicas.add(m);
            System.out.println("Musica cadastrada com sucesso!");

        } catch (NumberFormatException e) {
            System.out.println("Duracao invalida. Informe um numero inteiro.");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    static void listarMusicas() {
        if (bancoMusicas.isEmpty()) {
            System.out.println("Nenhuma musica cadastrada.");
            return;
        }
        System.out.println("\n--- BANCO DE MUSICAS ---");
        for (int i = 0; i < bancoMusicas.size(); i++) {
            System.out.print((i + 1) + ". ");
            bancoMusicas.get(i).exibir();
        }
    }

    static void buscarMusica() {
        System.out.println("Buscar por: 1. Titulo | 2. Artista");
        System.out.print("Escolha: ");
        int tipo = -1;
        try { tipo = Integer.parseInt(scanner.nextLine()); } catch (Exception e) {}
        System.out.print("Busca: ");
        String busca = scanner.nextLine();

        boolean achou = false;
        for (Musica m : bancoMusicas) {
            if ((tipo == 1 && m.contemTitulo(busca)) || (tipo == 2 && m.contemArtista(busca))) {
                m.exibir();
                achou = true;
            }
        }
        if (!achou) System.out.println("Nenhuma musica encontrada para '" + busca + "'.");
    }

    static void reproduzirMusicaFree() {
        if (bancoMusicas.isEmpty()) { System.out.println("Nenhuma musica cadastrada."); return; }
        listarMusicas();
        System.out.print("Escolha o numero da musica: ");
        try {
            int idx = Integer.parseInt(scanner.nextLine()) - 1;
            if (idx >= 0 && idx < bancoMusicas.size()) {
                usuarioFree.reproduzirMusica(bancoMusicas.get(idx));
            } else {
                System.out.println("Musica nao encontrada.");
            }
        } catch (Exception e) {
            System.out.println("Entrada invalida.");
        }
    }

    static void reproduzirMusicaPremium() {
        if (bancoMusicas.isEmpty()) { System.out.println("Nenhuma musica cadastrada."); return; }
        listarMusicas();
        System.out.print("Escolha o numero da musica: ");
        try {
            int idx = Integer.parseInt(scanner.nextLine()) - 1;
            if (idx >= 0 && idx < bancoMusicas.size()) {
                usuarioPremium.reproduzirMusica(bancoMusicas.get(idx));
            } else {
                System.out.println("Musica nao encontrada.");
            }
        } catch (Exception e) {
            System.out.println("Entrada invalida.");
        }
    }

    static void baixarMusicaPremium() {
        if (bancoMusicas.isEmpty()) { System.out.println("Nenhuma musica cadastrada."); return; }
        listarMusicas();
        System.out.print("Escolha o numero da musica para baixar: ");
        try {
            int idx = Integer.parseInt(scanner.nextLine()) - 1;
            if (idx >= 0 && idx < bancoMusicas.size()) {
                usuarioPremium.baixarMusica(bancoMusicas.get(idx));
            } else {
                System.out.println("Musica nao encontrada.");
            }
        } catch (Exception e) {
            System.out.println("Entrada invalida.");
        }
    }

    static void criarPlaylist(boolean premium) {
        System.out.print("Nome da playlist: ");
        String nome = scanner.nextLine();
        try {
            if (premium) {
                usuarioPremium.criarPlaylist(nome);
            } else {
                usuarioFree.criarPlaylist(nome);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    static void gerenciarPlaylists(boolean premium) {
        System.out.println("\n=== GERENCIAR PLAYLISTS ===");
        System.out.println("1. Listar minhas playlists");
        System.out.println("2. Adicionar musica a uma playlist");
        System.out.println("3. Remover musica de uma playlist");
        System.out.println("4. Exibir detalhes de uma playlist");
        System.out.println("0. Voltar");
        System.out.print("Opcao: ");

        int op = -1;
        try { op = Integer.parseInt(scanner.nextLine()); } catch (Exception e) {}
        if (op == 0) return;

        Usuario u = premium ? usuarioPremium : usuarioFree;

        if (op == 1) {
            u.listarPlaylists();
            return;
        }

        u.listarPlaylists();
        if (u.getQuantidadePlaylists() == 0) return;

        System.out.print("Escolha o numero da playlist: ");
        int idxP = -1;
        try { idxP = Integer.parseInt(scanner.nextLine()) - 1; } catch (Exception e) {}
        Playlist p = u.getPlaylist(idxP);

        if (p == null) { System.out.println("Playlist nao encontrada."); return; }

        if (op == 2) {
            listarMusicas();
            System.out.print("Escolha o numero da musica para adicionar: ");
            try {
                int idxM = Integer.parseInt(scanner.nextLine()) - 1;
                if (idxM >= 0 && idxM < bancoMusicas.size()) {
                    p.adicionarMusica(bancoMusicas.get(idxM));
                } else {
                    System.out.println("Musica nao encontrada.");
                }
            } catch (Exception e) { System.out.println("Entrada invalida."); }

        } else if (op == 3) {
            p.listarMusicas();
            System.out.print("Escolha o numero da musica para remover: ");
            try {
                int idxR = Integer.parseInt(scanner.nextLine()) - 1;
                p.removerMusica(idxR);
            } catch (Exception e) { System.out.println("Entrada invalida."); }

        } else if (op == 4) {
            p.listarMusicas();
            System.out.println("Total de musicas: " + p.getQuantidadeMusicas());
            System.out.println("Duracao total: " + p.getDuracaoTotal() + " segundos.");
        }
    }

    // ─────────────────────────────────────────────
    // BANCO DE MÚSICAS INICIAL
    // ─────────────────────────────────────────────

    static void popularBancoDeMusicas() {
        try {
            bancoMusicas.add(new Musica("Bohemian Rhapsody", "Queen", 354, "Rock"));
            bancoMusicas.add(new Musica("Shape of You", "Ed Sheeran", 234, "Pop"));
            bancoMusicas.add(new Musica("Blinding Lights", "The Weeknd", 200, "Pop"));
            bancoMusicas.add(new Musica("God's Plan", "Drake", 198, "Hip-Hop"));
            bancoMusicas.add(new Musica("So What", "Miles Davis", 561, "Jazz"));
        } catch (Exception e) {
            System.out.println("Erro ao popular banco: " + e.getMessage());
        }
    }
}