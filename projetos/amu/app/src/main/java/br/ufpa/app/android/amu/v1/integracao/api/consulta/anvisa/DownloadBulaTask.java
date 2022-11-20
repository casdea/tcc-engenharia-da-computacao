package br.ufpa.app.android.amu.v1.integracao.api.consulta.anvisa;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import br.ufpa.app.android.amu.v1.activity.DetalheMedicamentoActivity;
import br.ufpa.app.android.amu.v1.integracao.dto.MedicamentoRetDTO;
import br.ufpa.app.android.amu.v1.integracao.dto.RetornoExecucaoDTO;
import br.ufpa.app.android.amu.v1.util.App;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DownloadBulaTask extends AsyncTask<MedicamentoRetDTO, Void, RetornoExecucaoDTO> {

    protected ProgressDialog pDialog;
    private MedicamentoRetDTO medicamentoRetDTO;

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

            if (UtilAvisa.arquivoExiste(UtilAvisa.DIRETORIO_PDF + "/" + this.medicamentoRetDTO.getNomeArquivoPdf()) == false) {
                Retrofit retrofit = RetrofitBuilder.getInstance(App.context);

                BularioEletronicoClient bularioEletronicoClient = retrofit.create(BularioEletronicoClient.class);

                System.out.println("Enfieirando download do arquivo " + this.medicamentoRetDTO.getNomeArquivoPdf());

                Response<ResponseBody> arg = bularioEletronicoClient.getArquivoBula(this.medicamentoRetDTO.getNomeArquivoBulaPaciente()).execute();

                File path = UtilAvisa.criarDiretorio(UtilAvisa.DIRETORIO_PDF);

                UtilAvisa.criarDiretorio(UtilAvisa.DIRETORIO_TXT);

                File file = new File(path, this.medicamentoRetDTO.getNomeArquivoPdf());
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
                if (retornoExecucaoDTO.isOperacaoExecutada() == false) {
                    Toast.makeText(App.context, "Consulta de Bula Falhou " + retornoExecucaoDTO.getMensagemExecucao(), Toast.LENGTH_LONG).show();
                } else {
                    if (UtilAvisa.arquivoExiste(UtilAvisa.DIRETORIO_PDF + "/" + this.medicamentoRetDTO.getNomeArquivoPdf())) {

                        String texto = "";
                        boolean convertidoParaTexto = false;
                        if (UtilAvisa.arquivoExiste(UtilAvisa.DIRETORIO_TXT + "/" + this.medicamentoRetDTO.getNomeArquivoTxt()) == false) {
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

                        Intent intent = new Intent();
                        intent.putExtra("texto", texto);
                        intent.setClass(App.context, DetalheMedicamentoActivity.class);
                        App.context.startActivity(intent);
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
            PdfReaderContentParser parser = new PdfReaderContentParser(reader);
            PrintWriter out = new PrintWriter(new FileOutputStream(txt));
            TextExtractionStrategy strategy;
            for (int i = 1; i <= reader.getNumberOfPages(); i++) {
                // strategy = parser.processContent(i, new SimpleTextExtractionStrategy());
                //out.println(strategy.getResultantText());
                out.println(PdfTextExtractor.getTextFromPage(reader, i).trim()+"\n"); //Extracting the content from the different pages
            }
            out.flush();
            out.close();
            reader.close();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return false;
        }
    }

}