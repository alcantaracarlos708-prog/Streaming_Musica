package br.com.streaming.util;

public class FormatadorTempo {

    public static String formatar(int totalSegundos) {
        int minutos = totalSegundos / 60;
        int segundos = totalSegundos % 60;
        return String.format("%d:%02d", minutos, segundos);
    }

    public static String formatarLongo(int totalSegundos) {
        int horas   = totalSegundos / 3600;
        int minutos = (totalSegundos % 3600) / 60;
        int segundos = totalSegundos % 60;
        if (horas > 0) {
            return String.format("%dh %02dmin %02ds", horas, minutos, segundos);
        }
        return String.format("%dmin %02ds", minutos, segundos);
    }
}
