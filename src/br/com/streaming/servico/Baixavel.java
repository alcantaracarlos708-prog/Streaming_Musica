package br.com.streaming.servico;

import br.com.streaming.modelo.Musica;

public interface Baixavel {
    void baixarMusica(Musica musica);
    void listarMusicasBaixadas();
    int getQuantidadeMusicasBaixadas();
}
