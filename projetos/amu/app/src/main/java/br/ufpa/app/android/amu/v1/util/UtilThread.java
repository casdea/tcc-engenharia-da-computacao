package br.ufpa.app.android.amu.v1.util;

public class UtilThread {

    public static final long QUATRO_SEGUNDOS = 4000;
    public static final long CINCO_SEGUNDOS = 5000;

    public static void esperar(long tempo)
    {
        try {
            Thread.sleep(tempo);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
