package br.ufpa.app.android.amu.v1.integracao.api.consulta.anvisa;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import br.ufpa.app.android.amu.v1.integracao.dto.ConsultarMedicamentoRetDTO;
import br.ufpa.app.android.amu.v1.integracao.dto.MedicamentoRetDTO;
import br.ufpa.app.android.amu.v1.integracao.interfaces.IntegracaoBularioEletronico;
import retrofit2.Retrofit;

public class IntegracaoBularioEletronicoAnvisa implements IntegracaoBularioEletronico {

    @Override
    public ConsultarMedicamentoRetDTO consultarDadosMedicamentos(Context context, String nomeComercial) {

        ConsultarMedicamentoRetDTO consultarMedicamentoRetDTO = new ConsultarMedicamentoRetDTO(true, "");

        try {

            Retrofit retrofit = RetrofitBuilder.getInstance(context);

            BularioEletronicoJson bularioEletronicoJson = obterBularioEletronico(retrofit, nomeComercial);

            if (bularioEletronicoJson != null) {

                System.out.println("Resultado da Consulta do Bulario: " + bularioEletronicoJson);

                System.out.println("Conteudo: ");

                for (BularioEletronicoConteudoJson bularioEletronicoConteudoJson : bularioEletronicoJson.getContent()) {
                    System.out.println("Item: " + bularioEletronicoConteudoJson.toString());
                    consultarMedicamentoRetDTO.getMedicamentos().add(
                            new MedicamentoRetDTO(
                                    bularioEletronicoConteudoJson.getNomeProduto(),
                                    bularioEletronicoConteudoJson.getRazaoSocial(),
                                    bularioEletronicoConteudoJson.getIdBulaPacienteProtegido(),
                                    String.valueOf(bularioEletronicoConteudoJson.getIdProduto()),
                                    bularioEletronicoConteudoJson.getData()));
                }
            } else {
                System.out.println("CONSULTA NAO FOI EXECUTADA");
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            consultarMedicamentoRetDTO.setOperacaoExecutada(false);
            consultarMedicamentoRetDTO.setMensagemExecucao(e.getMessage());
        }

        return consultarMedicamentoRetDTO;
    }

    private BularioEletronicoJson obterBularioEletronico(Retrofit retrofit, String nomeComercial)
            throws InterruptedException, ExecutionException {
        BularioEletronicoClient bularioEletronicoClient = retrofit.create(BularioEletronicoClient.class);
        CompletableFuture<BularioEletronicoJson> response = bularioEletronicoClient.getBulario(10, nomeComercial, 1);

        // do other stuff here while the request is in-flight

        return response.get();
    }

    @Override
    public void obterTextoBula(AppCompatActivity atividade, MedicamentoRetDTO medicamentoRetDTO) {
        new DownloadBulaTask(atividade).execute(medicamentoRetDTO);
    }

}