package com.twilio;

import java.io.File;
import java.io.FileOutputStream;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.apache.commons.io.IOUtils;

import com.twilio.json.BularioEletronicoConteudoJson;
import com.twilio.json.BularioEletronicoJson;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class NavegadorPaginaBulario {

	private static BularioEletronicoJson obterBularioEletronico(Retrofit retrofit)
			throws InterruptedException, ExecutionException {
		BularioEletronicoClient bularioEletronicoClient = retrofit.create(BularioEletronicoClient.class);

		CompletableFuture<BularioEletronicoJson> response = bularioEletronicoClient.getBulario(10,"Dorflex",1);

		// do other stuff here while the request is in-flight

		BularioEletronicoJson bularioEletronicoJson = response.get();

		return bularioEletronicoJson;
	}

	private static void obterArquivoBula(Retrofit retrofit, String nomeArquivo, String chave) throws InterruptedException, ExecutionException {
		BularioEletronicoClient bularioEletronicoClient = retrofit.create(BularioEletronicoClient.class);
		
		bularioEletronicoClient.getArquivoBula(nomeArquivo).enqueue(new Callback<ResponseBody>() {
			
			@Override
			public void onResponse(Call<ResponseBody> arg0, Response<ResponseBody> arg1) {
				 try {
                     File path = new File("D:\\Temp\\anvisa\\");
                     File file = new File(path, "bula"+chave+".pdf");
                     FileOutputStream fileOutputStream = new FileOutputStream(file);
                     IOUtils.write(arg1.body().bytes(), fileOutputStream);
                 }
                 catch (Exception ex){
                	 ex.printStackTrace();
                	 System.out.println("Erro no responde de obter arquivo de bula "+ex.getMessage());
                 }
				
			}
			
			@Override
			public void onFailure(Call<ResponseBody> arg0, Throwable arg1) {
				// TODO Auto-generated method stub
				System.out.println("falha ao obter arquivo da bula: "+arg1.getMessage());
			}
			
		});
		
	}

	public static void main(String[] args) throws Exception {

		try {

			Retrofit retrofit = new Retrofit.Builder().baseUrl("https://consultas.anvisa.gov.br")
					.addConverterFactory(JacksonConverterFactory.create()).build();

			BularioEletronicoJson bularioEletronicoJson = obterBularioEletronico(retrofit);

			if (bularioEletronicoJson != null) {

				System.out.println("Resultado da Consulta do Bulario: " + bularioEletronicoJson.toString());

				System.out.println("Conteudo: ");
				
				for (BularioEletronicoConteudoJson bularioEletronicoConteudoJson : bularioEletronicoJson.getContent()) {
					System.out.println("Item: "+bularioEletronicoConteudoJson.toString());
					
					obterArquivoBula(retrofit, bularioEletronicoConteudoJson.getIdBulaPacienteProtegido(), bularioEletronicoConteudoJson.getIdBulaPacienteProtegido());
				}
				


			}
			else {
				System.out.println("CONSULTA NAO FOI EXECUTADA");
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

	}
}
