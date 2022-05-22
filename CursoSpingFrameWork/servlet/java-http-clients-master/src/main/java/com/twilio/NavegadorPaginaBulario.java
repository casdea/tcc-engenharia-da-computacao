package com.twilio;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.apache.commons.io.IOUtils;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;
import com.twilio.json.BularioEletronicoConteudoJson;
import com.twilio.json.BularioEletronicoJson;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class NavegadorPaginaBulario {
	
	private static final String URL_CONSULTAS_API_ANVISA = "https://consultas.anvisa.gov.br";
	private static final String DIRETORIO_DESTINO_PDF_ANVISA = "D:\\Temp\\anvisa\\";
	private static int arquivosBaixados;
	
	private static BularioEletronicoJson obterBularioEletronico(Retrofit retrofit)
			throws InterruptedException, ExecutionException {
		BularioEletronicoClient bularioEletronicoClient = retrofit.create(BularioEletronicoClient.class);

		CompletableFuture<BularioEletronicoJson> response = bularioEletronicoClient.getBulario(10, "Dorflex", 1);

		// do other stuff here while the request is in-flight

		BularioEletronicoJson bularioEletronicoJson = response.get();

		return bularioEletronicoJson;
	}

	private static void obterArquivoBula(Retrofit retrofit, String nomeArquivo, String chave)
			throws InterruptedException, ExecutionException {
		BularioEletronicoClient bularioEletronicoClient = retrofit.create(BularioEletronicoClient.class);

		System.out.println("Enfieirando download do arquivo "+nomeArquivo);
		
		bularioEletronicoClient.getArquivoBula(nomeArquivo).enqueue(new Callback<ResponseBody>() {

			@Override
			public void onResponse(Call<ResponseBody> arg0, Response<ResponseBody> arg1) {
				try {
					File path = new File(DIRETORIO_DESTINO_PDF_ANVISA);
					File file = new File(path, chave + ".pdf");
					FileOutputStream fileOutputStream = new FileOutputStream(file);
					IOUtils.write(arg1.body().bytes(), fileOutputStream);
					arquivosBaixados++;
				} catch (Exception ex) {
					ex.printStackTrace();
					System.out.println("Erro no responde de obter arquivo de bula " + ex.getMessage());
				}
			}

			@Override
			public void onFailure(Call<ResponseBody> arg0, Throwable arg1) {
				// TODO Auto-generated method stub
				System.out.println("falha ao obter arquivo da bula: " + arg1.getMessage());
			}

		});

	}
	
	public static void parsePdf(String pdf, String txt) throws IOException {
	    PdfReader reader = new PdfReader(pdf);
	    PdfReaderContentParser parser = new PdfReaderContentParser(reader);
	    PrintWriter out = new PrintWriter(new FileOutputStream(txt));
	    TextExtractionStrategy strategy;
	    for (int i = 1; i <= reader.getNumberOfPages(); i++) {
	        strategy = parser.processContent(i, new SimpleTextExtractionStrategy());
	        out.println(strategy.getResultantText());
	    }
	    out.flush();
	    out.close();
	    reader.close();
	}

	public static void main(String[] args) throws Exception {

		try {

			Retrofit retrofit = new Retrofit.Builder().baseUrl(URL_CONSULTAS_API_ANVISA)
					.addConverterFactory(JacksonConverterFactory.create()).build();

			BularioEletronicoJson bularioEletronicoJson = obterBularioEletronico(retrofit);

			arquivosBaixados = 0;
			
			if (bularioEletronicoJson != null) {

				System.out.println("Resultado da Consulta do Bulario: " + bularioEletronicoJson.toString());

				System.out.println("Conteudo: ");

				for (BularioEletronicoConteudoJson bularioEletronicoConteudoJson : bularioEletronicoJson.getContent()) {
					System.out.println("Item: " + bularioEletronicoConteudoJson.toString());

					obterArquivoBula(retrofit, bularioEletronicoConteudoJson.getIdBulaPacienteProtegido(),
							bularioEletronicoConteudoJson.getIdBulaPacienteProtegido());

					obterArquivoBula(retrofit, bularioEletronicoConteudoJson.getIdBulaProfissionalProtegido(),
							bularioEletronicoConteudoJson.getIdBulaProfissionalProtegido());
				}
				
				System.out.println("Aguardando os arquivos serem baixados...");
				
				int contador = 0;
				
				while (contador <= 5) 
				{
					if (arquivosBaixados == 4) break;
					
					Thread.sleep(5000);
					contador++;
				}
				
				System.out.println("Transformando em texto...");
				
				for (BularioEletronicoConteudoJson bularioEletronicoConteudoJson : bularioEletronicoJson.getContent()) {
					parsePdf(DIRETORIO_DESTINO_PDF_ANVISA+bularioEletronicoConteudoJson.getIdBulaPacienteProtegido()+".pdf", DIRETORIO_DESTINO_PDF_ANVISA+bularioEletronicoConteudoJson.getIdBulaPacienteProtegido()+".txt");
					parsePdf(DIRETORIO_DESTINO_PDF_ANVISA+bularioEletronicoConteudoJson.getIdBulaProfissionalProtegido()+".pdf", DIRETORIO_DESTINO_PDF_ANVISA+bularioEletronicoConteudoJson.getIdBulaProfissionalProtegido()+".txt");

					System.out.println("Arquivo: " + bularioEletronicoConteudoJson.toString());
				}
				
			} else {
				System.out.println("CONSULTA NAO FOI EXECUTADA");
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

	}
}
