package com.twilio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

import com.fasterxml.jackson.databind.JsonNode;

public class JavaHttpClientBulario {

    public static void main(String[] args) throws Exception {
       // synchronousRequest();
    	postForm("https://consultas.anvisa.gov.br/#/bulario/q/?nomeProduto=Dorflex");
    }

    /*
     * Erros superados
     * PKIX path building failed: sun.security.provider.certpath.SunCertPathBuilderException: unable to find valid certification path to requested target
     */
    
    private static void synchronousRequest() throws IOException, InterruptedException {
        // create a client
        var client = HttpClient.newHttpClient();

        // create a request
        var request = HttpRequest.newBuilder(
            URI.create("https://consultas.anvisa.gov.br/#/bulario/q/?nomeProduto=Dorflex")
        ).build();
        
        // use the client to send the request
        HttpResponse<Supplier<JsonNode>> response = client.send(request, new JsonBodyHandler<>(JsonNode.class));

        /*ObjectMapper objectMapper = new ObjectMapper();
        HealthWorkerService healthWorkerService = new HealthWorkerService();
        HealthWorker healthWorker = healthWorkerService.findHealthWorkerById(1);
        JsonNode healthWorkerJsonNode = objectMapper.valueToTree(healthWorker);
        */
        // the response:
        System.out.println(response.toString());
    }

    private static void synchronousRequestOld() throws IOException, InterruptedException {
        // create a client
        var client = HttpClient.newHttpClient();

        // create a request
        var request = HttpRequest.newBuilder(
            URI.create("https://consultas.anvisa.gov.br/#/bulario/q/?nomeProduto=Dorflex")
        ).build();
        
        // use the client to send the request
        HttpResponse<Supplier<APOD>> response = client.send(request, new JsonBodyHandler<>(APOD.class));

        // the response:
        System.out.println(response.body().get().title);
    }

   
	public static Integer postForm(String url) throws Exception
	{
		int responseCode = 0;
		try
		{
			System.out.println("Comando Post " + url);
			URL obj = new URL(url);
			StringBuilder postData = new StringBuilder();
			byte[] postDataBytes = postData.toString().getBytes("UTF-8");
			HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
			conn.setRequestMethod("GET");
			//conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
			conn.setDoOutput(true);
			conn.getOutputStream().write(postDataBytes);
			responseCode = conn.getResponseCode();
			System.out.println("\nSending 'POST' request to URL : " + url);
			System.out.println("Response Code : " + responseCode + " " + conn.getResponseMessage());
			BufferedReader iny = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String output;
			StringBuffer res = new StringBuffer();
			
			while ((output = iny.readLine()) != null)
			{
				res.append(output);
			}
			
			iny.close();
			System.out.println(res.toString());
			return responseCode;
		}
		catch (Exception e)
		{
			throw new Exception("Erro ao chamar Servico PostForm. Detalhes: " + e.getMessage());
		}
	}


}
