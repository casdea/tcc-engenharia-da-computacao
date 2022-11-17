package br.ufpa.app.android.amu.v1.integracao.api.usuario.pcd.visual;

import android.media.MediaPlayer;

public abstract class Comando {

    protected MediaPlayer mediaPlayer;

    public Comando(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    public abstract void before();
    public abstract  void run();
    public  abstract void after();
}
