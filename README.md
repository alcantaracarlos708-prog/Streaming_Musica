# 🎵 Sistema de Streaming de Música

Projeto acadêmico em Java que simula uma plataforma de streaming de música com suporte a múltiplos tipos de usuário, playlists, downloads, busca no catálogo e geração de relatórios.

---

## 📋 Funcionalidades

- **Cadastro e gerenciamento de músicas** — cada música possui id, título, artista, álbum, ano, duração, gênero e tamanho em MB, com validações nos setters
- **Catálogo com buscas e filtros** — busca por título ou artista, filtro por gênero e filtro por faixa de duração
- **Sistema de playlists** — criação de playlists personalizadas (pelo usuário) e automáticas (por critério: top, recomendadas, recentes)
- **Múltiplos tipos de usuário** — plano Free (limite de reproduções, anúncios, sem download) e plano Premium (qualidade alta, downloads, modo offline)
- **Sistema de reprodução completo** — métodos `reproduzir()`, `pausar()`, `parar()` e `isReproduzindo()` com controle de estado
- **Sistema de downloads** — exclusivo para usuários Premium; impede duplicatas e suporta reprodução offline
- **Histórico de reprodução** — todas as músicas ouvidas são registradas por usuário
- **Recomendações** — sugestões baseadas nos gêneros do histórico de cada usuário
- **Estatísticas e relatórios** — tempo total ouvido, gênero favorito, música mais ouvida e quantidade de playlists

---

## 🏗️ Arquitetura

```
src/
└── br/com/streaming/
    ├── modelo/          # Entidades do domínio
    ├── servico/         # Interfaces de contrato
    ├── util/            # Utilitários e relatórios
    └── principal/       # Ponto de entrada
```

### Pacotes e classes

| Pacote | Classe / Interface | Responsabilidade |
|---|---|---|
| `modelo` | `Musica` | Entidade da música; implementa `Reproduzivel` |
| `modelo` | `Catalogo` | Acervo de músicas; buscas e filtros |
| `modelo` | `Playlist` | Playlist base com adição, remoção e listagem |
| `modelo` | `PlaylistPersonalizada` | Playlist criada pelo usuário (herda `Playlist`) |
| `modelo` | `PlaylistAutomatica` | Playlist gerada por critério do sistema (herda `Playlist`) |
| `modelo` | `Usuario` | Classe abstrata base de todos os usuários |
| `modelo` | `UsuarioFree` | Usuário com limite de reproduções e anúncios |
| `modelo` | `UsuarioPremium` | Usuário com alta qualidade, downloads e modo offline; implementa `Baixavel` |
| `modelo` | `ItemReproducao` | Representa uma música na fila com posição e estado |
| `servico` | `Reproduzivel` | Interface com `reproduzir()`, `pausar()`, `parar()`, `isReproduzindo()` |
| `servico` | `Baixavel` | Interface com `baixarMusica()`, `listarMusicasBaixadas()` |
| `servico` | `GeradorRecomendacoes` | Gera recomendações com base no histórico do usuário |
| `util` | `Validador` | Validações de e-mail, nome e duração |
| `util` | `FormatadorTempo` | Formata segundos em `mm:ss` ou `Xh Ymin Zs` |
| `util` | `RelatorioUsuario` | Calcula e exibe estatísticas de um usuário |
| `principal` | `StreamingMusica` | Método `main` — menu interativo no terminal; todas as ações são feitas pelo usuário em tempo real |

### Conceitos de POO aplicados

- **Encapsulamento** — todos os atributos são `private`/`protected`; acesso via getters e setters com validação
- **Herança** — `UsuarioFree` e `UsuarioPremium` estendem `Usuario`; `PlaylistPersonalizada` e `PlaylistAutomatica` estendem `Playlist`
- **Polimorfismo** — `reproduzirMusica()` e `reproduzir()` têm comportamento diferente em cada subclasse; sobrecarga com parâmetro `boolean` em Free e Premium
- **Classes abstratas** — `Usuario` é abstrata com os métodos `reproduzirMusica()`, `getTipoPlano()`, `getLimiteDownloads()` e `podeOuvirSemAnuncios()`
- **Interfaces** — `Reproduzivel` implementada por `Musica`; `Baixavel` implementada por `UsuarioPremium`
- **`@Override`** — anotação presente em todos os métodos sobrescritos

---

## 🚀 Como Executar

### Pré-requisitos

- Java JDK 11 ou superior instalado
- Terminal (Linux/macOS) ou Prompt de Comando (Windows)

### Compilar

```bash
# Na raiz do projeto (onde está a pasta src/)
javac -d out $(find src -name "*.java")
```

> No Windows (PowerShell):
> ```powershell
> Get-ChildItem -Recurse -Filter *.java src | ForEach-Object { $_.FullName } | Out-File arquivos.txt
> javac -d out @arquivos.txt
> ```

### Executar

```bash
java -cp out br.com.streaming.principal.StreamingMusica
```

### Uso

O programa abre um menu interativo no terminal. Tudo é cadastrado pelo próprio usuário durante a execução — nenhum dado vem pré-carregado.

```
===========================================
  MENU PRINCIPAL | Nenhum usuario logado
===========================================
  1. Cadastrar usuario
  2. Entrar com usuario
  3. Gerenciar catalogo de musicas
  4. Minha conta
  0. Sair
```

**Fluxo recomendado:**
1. Opção `1` — cadastre um usuário (Free ou Premium)
2. Opção `3` → `1` — adicione músicas ao catálogo
3. Opção `4` → `2` — reproduza músicas
4. Opção `4` → `3` — crie playlists e adicione músicas
5. Opção `4` → `6` — veja seu relatório de estatísticas
6. *(Premium)* Opção `4` → `7` — baixe músicas e reproduza offline

---

## 👤 Autor

- **Nome:** Carlos
- **RA:** 45711402

---

## 📅 Histórico de Checkpoints

| # | Checkpoint | Descrição |
|---|---|---|
| 1 | Estrutura inicial | Criação dos pacotes e classes base (`Musica`, `Playlist`, `Usuario`) |
| 2 | Herança e abstração | Implementação de `Usuario` abstrata, `UsuarioFree` e `UsuarioPremium` |
| 3 | Interfaces | Criação e implementação de `Reproduzivel` e `Baixavel` |
| 4 | Polimorfismo | Sobrescrita de `reproduzirMusica()` e `reproduzir()` nas subclasses; sobrecarga com `boolean` |
| 5 | Funcionalidades completas | Catálogo com buscas/filtros, `GeradorRecomendacoes`, `PlaylistAutomatica` |
| 6 | Qualidade e relatórios | Correção de warnings, `RelatorioUsuario` com estatísticas, 0 erros e 0 warnings |
