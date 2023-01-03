package br.ufpa.app.android.amu.v1.activity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.speech.RecognizerIntent;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import java.util.ArrayList;

import br.ufpa.app.android.amu.v1.integracao.classes.ComandosVoz;
import br.ufpa.app.android.amu.v1.interfaces.GerenteServicosListener;
import br.ufpa.app.android.amu.v1.util.App;
import br.ufpa.app.android.amu.v1.util.Constantes;
import br.ufpa.app.android.amu.v1.util.ThreadUtil;

public class RecursoVozLifeCyCleObserver implements DefaultLifecycleObserver {
    private final ActivityResultRegistry mRegistry;
    private ActivityResultLauncher<Intent> mGetRecursoVoz;
    private GerenteServicosListener gerenteServicosListener;

    RecursoVozLifeCyCleObserver(@NonNull ActivityResultRegistry registry, AppCompatActivity atividade) {
        mRegistry = registry;
        this.gerenteServicosListener = (GerenteServicosListener) atividade;
    }

    public void onCreate(@NonNull LifecycleOwner owner) {
        // ...
        mGetRecursoVoz = mRegistry.register("key", owner,
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            App.escutandoComando = false;

                            App.integracaoUsuario.capturarComandoEncerrado();

                            ThreadUtil.esperar(ThreadUtil.CINCO_SEGUNDOS);

                            // There are no request codes
                            Intent data = result.getData();
                            ArrayList<String> text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                            if (text == null || text.size() == 0) {
                                //txvStatusComando.setText("Texto de Voz inválido");
                                return;
                            }

                            int nrComandoVoz = App.integracaoUsuario.findComando(text.get(0));

                            switch (nrComandoVoz) {
                                case ComandosVoz.COMANDO_NAO_RECONHECIDO: {
                                    App.integracaoUsuario.comandoNaoReconhecido(text.get(0));
                                    break;
                                }

                                case ComandosVoz.DOSE_REALIZADA: {
                                    gerenteServicosListener.executarAcao(Constantes.ACAO_VOZ_DOSE_REALIZADA, text.get(0));
                                    break;
                                }

                                case ComandosVoz.TELA_ANTERIOR: {
                                    gerenteServicosListener.executarAcao(Constantes.ACAO_VOZ_TELA_ANTERIOR, text.get(0));
                                    break;
                                }

                                case ComandosVoz.LISTA_MEDICAMENTOS: {
                                    gerenteServicosListener.executarAcao(Constantes.ACAO_VOZ_LISTA_MEDICAMENTOS, text.get(0));
                                    break;
                                }

                                case ComandosVoz.DESCREVA_MEDICAMENTO: {
                                    gerenteServicosListener.executarAcao(Constantes.ACAO_VOZ_DESCREVA_MEDICAMENTO, text.get(0));
                                    break;
                                }

                                case ComandosVoz.DESCREVA_HORARIO: {
                                    gerenteServicosListener.executarAcao(Constantes.ACAO_VOZ_DESCREVA_HORARIO, text.get(0));
                                    break;
                                }

                                case ComandosVoz.ESTOQUE_ATUAL: {
                                    gerenteServicosListener.executarAcao(Constantes.ACAO_VOZ_ESTOQUE_ATUAL, text.get(0));
                                    break;
                                }

                                case ComandosVoz.ENTRADA_ESTOQUE: {
                                    gerenteServicosListener.executarAcao(Constantes.ACAO_VOZ_ENTRADA_ESTOQUE, text.get(0));
                                    break;
                                }

                                case ComandosVoz.SAIDA_ESTOQUE: {
                                    gerenteServicosListener.executarAcao(Constantes.ACAO_VOZ_SAIDA_ESTOQUE, text.get(0));
                                    break;
                                }

                                case ComandosVoz.ALTERNAR_PERFIL: {
                                    gerenteServicosListener.executarAcao(Constantes.ACAO_VOZ_ALTERNAR_PERFIL, text.get(0));
                                    break;
                                }

                                case ComandosVoz.COMANDOS_DISPONIVEL_TELA_PRINCIPAL: {
                                    gerenteServicosListener.executarAcao(Constantes.ACAO_VOZ_COMANDOS_TELA_PRINCIPAL, text.get(0));
                                    break;
                                }

                                case ComandosVoz.COMANDOS_DISPONIVEL_DETALHE_MEDICAMENTO: {
                                    gerenteServicosListener.executarAcao(Constantes.ACAO_VOZ_COMANDOS_DETALHE_MEDICAMENTO, text.get(0));
                                    break;
                                }

                                case ComandosVoz.SAIR: {
                                    App.integracaoUsuario.avisarSaidaApp();

                                    ThreadUtil.esperar(ThreadUtil.CINCO_SEGUNDOS);

                                    gerenteServicosListener.executarAcao(Constantes.ACAO_VOZ_FECHAR_APP, text.get(0));
                                    break;
                                }

                            }
                        } else if (result.getResultCode() == Activity.RESULT_CANCELED) {
                            App.escutandoComando = false;
                            App.integracaoUsuario.tenteNovamenteComandoVoz();
                        }


                    }
                });
    }

    public void chamarItenteReconechimentoVoz() {
        if (App.escutandoComando) return;

        App.escutandoComando = true;

        App.integracaoUsuario.capturarComandoIniciado();
        ThreadUtil.esperar(ThreadUtil.QUATRO_SEGUNDOS);

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "pt-BR");
        intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, "10000");
        //intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US");
        try {
            mGetRecursoVoz.launch(intent);
            //tvText.setText("");
        } catch (ActivityNotFoundException e) {
            String appPackageName = "com.google.android.googlequicksearchbox";
            try {
                App.context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
            } catch (android.content.ActivityNotFoundException anfe) {
                App.context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
            }

            //Toast.makeText(getApplicationContext(), "Your device doesn't support Speech to Text", Toast.LENGTH_LONG).show();
            //e.printStackTrace();
        }
    }

}

