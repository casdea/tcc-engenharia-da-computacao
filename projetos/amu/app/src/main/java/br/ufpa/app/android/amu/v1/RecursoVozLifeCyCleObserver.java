package br.ufpa.app.android.amu.v1;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.speech.RecognizerIntent;
import android.view.View;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import java.util.ArrayList;

import br.ufpa.app.android.amu.v1.util.App;

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
                            // There are no request codes
                            Intent data = result.getData();
                            ArrayList<String> text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                            if (text == null || text.size()==0) {
                                //txvStatusComando.setText("Texto de Voz inválido");
                                return;
                            }

                            int idView = App.integracaoUsuario.findComando(text.get(0));

                            if (idView == -1) {
                                //txvStatusComando.setText("Comando não foi reconhecido");
                                return;
                            }

                            if (idView == R.id.btnConsultaMedicamento)
                            {
                                Intent intent = new Intent();
                                intent.setClass(App.context, ConsultaMedicamentoActivity.class);
                                App.context.startActivity(intent);
                            }

                            //onClick(view);
                        }

                    }
                });
    }

    public void chamarItenteReconechimentoVoz()
    {
        App.integracaoUsuario.pararMensagem();

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "pt-BR");
        //intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US");
        try {
            //startActivityForResult(intent, RESULT_SPEECH);
            //Intent intent = new Intent(this, SomeActivity.class);
            mGetRecursoVoz.launch(intent);
            //tvText.setText("");
        } catch (ActivityNotFoundException e) {
            String appPackageName = "com.google.android.googlequicksearchbox";
            try {
                App.context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
            } catch (android.content.ActivityNotFoundException anfe) {
                App.context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
            }

            //Toast.makeText(getApplicationContext(), "Your device doesn't support Speech to Text", Toast.LENGTH_SHORT).show();
            //e.printStackTrace();
        }
    }

}

