package com.twilio.estudo;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import com.twilio.json.BularioEletronicoJson;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public class RetrofitDemo {

    public interface APODClient {
        @GET("/gerenciador/oi")
        @Headers("accept: application/json")
        CompletableFuture<BularioEletronicoJson> getApod();
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://localhost:8080")
            .addConverterFactory(JacksonConverterFactory.create())
            .build();

        APODClient apodClient = retrofit.create(APODClient.class);

        CompletableFuture<BularioEletronicoJson> response = apodClient.getApod();

        // do other stuff here while the request is in-flight

        BularioEletronicoJson apod = response.get();

        //System.out.println(apod.body);


    }

}
