package br.ufpa.app.android.amu.v1.classes;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import br.ufpa.app.android.amu.v1.R;
import br.ufpa.app.android.amu.v1.activity.PrincipalActivity;
import br.ufpa.app.android.amu.v1.interfaces.Transacao;

public class TransacaoEnviarNotificacao implements Transacao {

    private final AppCompatActivity atividade;
    private final String idMedicamento;
    private final String titulo;
    private final String corpo;

    public TransacaoEnviarNotificacao(AppCompatActivity atividade, String idMedicamento, String titulo, String corpo) {
        this.atividade = atividade;
        this.idMedicamento = idMedicamento;
        this.titulo = titulo;
        this.corpo = corpo;
    }

    @Override
    public void executar() {
        //Configuraçõe para notificação
        String canal = idMedicamento; //atividade.getString(R.string.default_notification_channel_id);
        Uri uriSom = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Intent intent = new Intent(atividade, PrincipalActivity.class);
        PendingIntent pendingIntent = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            pendingIntent = PendingIntent.getActivity(atividade, 0, intent, PendingIntent.FLAG_MUTABLE);
        }

        //Criar notificação
        NotificationCompat.Builder notificacao = new NotificationCompat.Builder(atividade, canal)
                .setContentTitle(titulo)
                .setContentText(corpo)
                .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                .setSound(uriSom)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        //Recupera notificationManager
        NotificationManager notificationManager = (NotificationManager) atividade.getSystemService(Context.NOTIFICATION_SERVICE);

        //Verifica versão do Android a partir do Oreo para configurar canal de notificação
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(canal, "canal", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        //Envia notificação
        notificationManager.notify(0, notificacao.build());

        System.out.println("Tarefa completa!");
    }
}
