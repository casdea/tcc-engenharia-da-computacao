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
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import java.util.ArrayList;

import br.ufpa.app.android.amu.v1.R;
import br.ufpa.app.android.amu.v1.integracao.classes.TipoFuncao;
import br.ufpa.app.android.amu.v1.util.App;
import br.ufpa.app.android.amu.v1.util.ThreadUtil;

public class RecursoVozLifeCyCleObserver implements DefaultLifecycleObserver {
    private final ActivityResultRegistry mRegistry;
    private ActivityResultLauncher<Intent> mGetRecursoVoz;

    RecursoVozLifeCyCleObserver(@NonNull ActivityResultRegistry registry) {
        mRegistry = registry;
    }

    public void onCreate(@NonNull LifecycleOwner owner) {
        // ...
        mGetRecursoVoz = mRegistry.register("key", owner,
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            App.integracaoUsuario.capturarComandoEncerrado();

                            ThreadUtil.esperar(ThreadUtil.CINCO_SEGUNDOS);

                            // There are no request codes
                            Intent data = result.getData();
                            ArrayList<String> text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                            if (text == null || text.size()==0) {
                                //txvStatusComando.setText("Texto de Voz inválido");
                                return;
                            }

                            if (App.comandoAtualVoz.equals(TipoFuncao.CHAMADA_TELA)) {
                                int idView = App.integracaoUsuario.findComando(text.get(0));

                                if (idView == -1) {
                                    //txvStatusComando.setText("Comando não foi reconhecido");
                                    return;
                                }

                                if (idView == R.id.btnConsultaMedicamento) {
                                    Intent intent = new Intent();
                                    intent.setClass(App.context, ConsultaMedicamentoActivity.class);
                                    App.context.startActivity(intent);
                                }
                            }
                            else
                            if (App.comandoAtualVoz.equals(TipoFuncao.PESQUISA_MEDICAMENTOS)) {
                            }

                            //onClick(view);
                        }

                    }
                });
    }

    public void chamarItenteReconechimentoVoz()
    {
        App.integracaoUsuario.capturarComandoIniciado();
        ThreadUtil.esperar(ThreadUtil.QUATRO_SEGUNDOS);

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "pt-BR");
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

