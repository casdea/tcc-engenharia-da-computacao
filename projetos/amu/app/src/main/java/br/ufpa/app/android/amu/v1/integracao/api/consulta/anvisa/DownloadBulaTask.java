package br.ufpa.app.android.amu.v1.integracao.api.consulta.anvisa;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import br.ufpa.app.android.amu.v1.integracao.dto.MedicamentoRetDTO;
import br.ufpa.app.android.amu.v1.integracao.dto.RetornoExecucaoDTO;
import br.ufpa.app.android.amu.v1.interfaces.GerenteServicosListener;
import br.ufpa.app.android.amu.v1.util.App;
import br.ufpa.app.android.amu.v1.util.Constantes;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DownloadBulaTask extends AsyncTask<MedicamentoRetDTO, Void, RetornoExecucaoDTO> {

    private final GerenteServicosListener gerenteServicosListener;
    protected ProgressDialog pDialog;
    private MedicamentoRetDTO medicamentoRetDTO;

    public DownloadBulaTask(AppCompatActivity atividade) {
        this.gerenteServicosListener = (GerenteServicosListener) atividade;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = ProgressDialog.show(App.context, "Consultando", "Contactando a ANVISA, por favor, aguarde alguns instantes.", true, false);
    }

    @Override
    protected RetornoExecucaoDTO doInBackground(MedicamentoRetDTO... medicamentos) {
        RetornoExecucaoDTO retornoExecucaoDTO = new RetornoExecucaoDTO();
        try {
            App.mensagemExecucao = "";

            this.medicamentoRetDTO = medicamentos[0];

            if (!UtilAvisa.arquivoExiste(UtilAvisa.DIRETORIO_PDF + "/" + this.medicamentoRetDTO.getNomeArquivoPdf())) {
                Retrofit retrofit = RetrofitBuilder.getInstance(App.context);

                BularioEletronicoClient bularioEletronicoClient = retrofit.create(BularioEletronicoClient.class);

                System.out.println("Enfieirando download do arquivo " + this.medicamentoRetDTO.getNomeArquivoPdf());

                Response<ResponseBody> arg = bularioEletronicoClient.getArquivoBula(this.medicamentoRetDTO.getNomeArquivoBulaPaciente()).execute();

                File path = UtilAvisa.criarDiretorio(UtilAvisa.DIRETORIO_PDF);

                UtilAvisa.criarDiretorio(UtilAvisa.DIRETORIO_TXT);

                File file = new File(path, this.medicamentoRetDTO.getNomeArquivoPdf());
                Log.i("Arquivo pdf ",file.getName());
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                IOUtils.write(arg.body().bytes(), fileOutputStream);
            }

            retornoExecucaoDTO.setMensagemExecucao("");
            retornoExecucaoDTO.setOperacaoExecutada(true);
        } catch (Exception e) {
            e.printStackTrace();
            retornoExecucaoDTO.setMensagemExecucao("falha ao obter consultar arquivo de bula: " + e.getMessage());
            retornoExecucaoDTO.setOperacaoExecutada(true);
            System.out.println(retornoExecucaoDTO.getMensagemExecucao());
        }

        return retornoExecucaoDTO;
    }

    @Override
    protected void onPostExecute(RetornoExecucaoDTO retornoExecucaoDTO) {
        super.onPostExecute(retornoExecucaoDTO);
        try {
            if (retornoExecucaoDTO != null) {
                if (!retornoExecucaoDTO.isOperacaoExecutada()) {
                    Toast.makeText(App.context, "Consulta de Bula Falhou " + retornoExecucaoDTO.getMensagemExecucao(), Toast.LENGTH_LONG).show();
                } else {
                    if (UtilAvisa.arquivoExiste(UtilAvisa.DIRETORIO_PDF + "/" + this.medicamentoRetDTO.getNomeArquivoPdf())) {

                        String texto = "";
                        boolean convertidoParaTexto = false;
                        if (!UtilAvisa.arquivoExiste(UtilAvisa.DIRETORIO_TXT + "/" + this.medicamentoRetDTO.getNomeArquivoTxt())) {
                            if (parsePdf(UtilAvisa.obterDiretorioPdfs() + this.medicamentoRetDTO.getNomeArquivoPdf(),
                                    UtilAvisa.obterDiretorioTxts() + this.medicamentoRetDTO.getNomeArquivoTxt())) {
                                convertidoParaTexto = true;
                            } else {
                                App.mensagemExecucao = "Não foi possivel converter a bula para texto";
                                texto = App.mensagemExecucao;
                            }
                        }
                        else
                            convertidoParaTexto = true;

                        if (convertidoParaTexto)
                            texto = UtilAvisa.lerArquivoTexto(UtilAvisa.obterDiretorioTxts() + this.medicamentoRetDTO.getNomeArquivoTxt());

                        App.medicamentoDTO = UtilAvisa.textoToMedicamento(this.medicamentoRetDTO, texto);

                        gerenteServicosListener.executarAcao(Constantes.ACAO_RECEBER_TEXTO_BULA, null);

                    } else
                        App.mensagemExecucao = "Não foi possivel consultar a bula";
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        pDialog.dismiss();
    }

    private boolean parsePdf(String pdf, String txt) throws Exception {
        try {
            PdfReader reader = new PdfReader(pdf);
            PrintWriter out = new PrintWriter(new FileOutputStream(txt));
            for (int i = 1; i <= reader.getNumberOfPages(); i++) {
                out.println(PdfTextExtractor.getTextFromPage(reader, i).trim()+"\n"); //Extracting the content from the different pages
            }
            out.flush();
            out.close();
            reader.close();

            Log.i("Arquivo convertido txt ",txt);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return false;
        }
    }

}