package com.twilio;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import com.twilio.json.BularioEletronicoConteudoJson;
import com.twilio.json.BularioEletronicoJson;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public class NavegadorPaginaBulario {

	public interface BularioEletronicoClient {
		@GET("/api/consulta/bulario?count=10&filter[nomeProduto]=Dorflex&page=1")
		@Headers({ "accept: application/json", "authorization: Guest" })
		CompletableFuture<BularioEletronicoJson> getBulario();
	}

	private static BularioEletronicoJson obterBularioEletronico(Retrofit retrofit)
			throws InterruptedException, ExecutionException {
		BularioEletronicoClient bularioEletronicoClient = retrofit.create(BularioEletronicoClient.class);

		CompletableFuture<BularioEletronicoJson> response = bularioEletronicoClient.getBulario();

		// do other stuff here while the request is in-flight

		BularioEletronicoJson bularioEletronicoJson = response.get();

		return bularioEletronicoJson;
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

	public static void get(String uri) throws Exception {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(uri)).build();

		HttpResponse<Path> response = client.send(request, BodyHandlers.ofFile(Paths.get("body.txt")));

		System.out.println("Response in file:" + response.body());
	}
}
