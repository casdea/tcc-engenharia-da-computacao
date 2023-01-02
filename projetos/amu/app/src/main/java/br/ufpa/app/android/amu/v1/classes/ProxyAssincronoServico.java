package br.ufpa.app.android.amu.v1.classes;

import br.ufpa.app.android.amu.v1.interfaces.Transacao;

public class ProxyAssincronoServico implements Transacao {
    private Transacao transacao;

    public ProxyAssincronoServico(Transacao transacao) {
        this.transacao = transacao;
    }

    @Override
    public void executar() {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                try {
                    transacao.executar();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread t = new Thread(r);
        t.start();
    }
}
