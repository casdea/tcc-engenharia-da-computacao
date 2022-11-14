package br.ufpa.app.android.amu.v1.integracao.api.consulta.anvisa;

import android.content.Context;
import android.os.Environment;
import android.webkit.DownloadListener;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import br.ufpa.app.android.amu.v1.integracao.dto.ConsultarMedicamentoRetDTO;
import br.ufpa.app.android.amu.v1.integracao.dto.MedicamentoRetDTO;
import br.ufpa.app.android.amu.v1.integracao.interfaces.IntegracaoBularioEletronico;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class IntegracaoBularioEletronicoAnvisa implements IntegracaoBularioEletronico {

    private static final String DIRETORIO_PDF = "pdfs";
    private static final String DIRETORIO_TXT = "txts";
    private static final String URL_CONSULTAS_API_ANVISA = "https://consultas.anvisa.gov.br";

    @Override
    public ConsultarMedicamentoRetDTO consultarDadosMedicamentos(Context context, String nomeComercial) {

        ConsultarMedicamentoRetDTO consultarMedicamentoRetDTO = new ConsultarMedicamentoRetDTO(true, "");

        try {

            Retrofit retrofit = RetrofitBuilder.getInstance(context);

            BularioEletronicoJson bularioEletronicoJson = obterBularioEletronico(retrofit, nomeComercial);

            if (bularioEletronicoJson != null) {

                System.out.println("Resultado da Consulta do Bulario: " + bularioEletronicoJson.toString());

                System.out.println("Conteudo: ");

                for (BularioEletronicoConteudoJson bularioEletronicoConteudoJson : bularioEletronicoJson.getContent()) {
                    System.out.println("Item: " + bularioEletronicoConteudoJson.toString());
                    consultarMedicamentoRetDTO.getMedicamentos().add(
                            new MedicamentoRetDTO(
                                    bularioEletronicoConteudoJson.getNomeProduto(),
                                    bularioEletronicoConteudoJson.getRazaoSocial(),
                                    bularioEletronicoConteudoJson.getIdBulaPacienteProtegido(),
                                    bularioEletronicoConteudoJson.getIdProduto()+"_"+bularioEletronicoConteudoJson.getData()+".pdf",
                                    bularioEletronicoConteudoJson.getIdProduto()+"_"+bularioEletronicoConteudoJson.getData()+".txt"));
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

        BularioEletronicoJson bularioEletronicoJson = response.get();

        return bularioEletronicoJson;
    }

    public void downloadBulaOld(Context context, String nomeArquivoBulaPaciente) {

        try {
            Retrofit retrofit = RetrofitBuilder.getInstance(context);

            BularioEletronicoClient bularioEletronicoClient = retrofit.create(BularioEletronicoClient.class);

            System.out.println("Enfieirando download do arquivo " + nomeArquivoBulaPaciente);

            bularioEletronicoClient.getArquivoBula(nomeArquivoBulaPaciente).enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> arg0, Response<ResponseBody> arg1) {
                    try {
                        File path = UtilAvisa.criarDiretorio(DIRETORIO_PDF);

                        UtilAvisa.criarDiretorio(DIRETORIO_TXT);

                        File file = new File(path, nomeArquivoBulaPaciente + ".pdf");
                        FileOutputStream fileOutputStream = new FileOutputStream(file);
                        IOUtils.write(arg1.body().bytes(), fileOutputStream);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        System.out.println("Erro ao Obter arquivo de bula " + ex.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> arg0, Throwable arg1) {
                    // TODO Auto-generated method stub
                    System.out.println("falha ao obter arquivo de bula: " + arg1.getMessage());
                }

            });
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void downloadBula(Context context, String nomeArquivoBulaPaciente) {

        try {
            Retrofit retrofit = RetrofitBuilder.getInstance(context);

            BularioEletronicoClient bularioEletronicoClient = retrofit.create(BularioEletronicoClient.class);

            System.out.println("Enfieirando download do arquivo " + nomeArquivoBulaPaciente);

            Response<ResponseBody> arg = bularioEletronicoClient.getArquivoBula(nomeArquivoBulaPaciente).execute();

            File path = UtilAvisa.criarDiretorio(DIRETORIO_PDF);

            UtilAvisa.criarDiretorio(DIRETORIO_TXT);

            File file = new File(path, nomeArquivoBulaPaciente + ".pdf");
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            IOUtils.write(arg.body().bytes(), fileOutputStream);
        } catch (
                Exception e) {
            e.printStackTrace();
            System.out.println("falha ao obter arquivo de bula: " + e.getMessage());
        }

    }

    @Override
    public String obterTextoBula(MedicamentoRetDTO medicamentoRetDTO) {
        try {
            //downloadBula(context, nomeArquivoBulaPaciente);
            new DownloadBulaTask().execute(medicamentoRetDTO);

            return "";
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return "Não foi possivel obter texto da bula. Detalhes: " + e.getMessage();
        }
    }
}