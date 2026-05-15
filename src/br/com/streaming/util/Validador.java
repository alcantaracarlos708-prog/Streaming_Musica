package br.com.streaming.util;

public class Validador {

    public static boolean emailValido(String email) {
        if (email == null || email.isBlank()) return false;
        return email.matches("^[\\w.+-]+@[\\w-]+\\.[a-zA-Z]{2,}$");
    }

    public static boolean nomeValido(String nome) {
        if (nome == null || nome.isBlank()) return false;
        return nome.trim().length() >= 2;
    }

    public static boolean duracaoValida(int segundos) {
        return segundos > 0 && segundos <= 7200;
    }

    public static boolean tamanhoValido(double mb) {
        return mb > 0 && mb <= 50;
    }
}
