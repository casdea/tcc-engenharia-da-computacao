package br.ufpa.app.android.amu.v1.integracao.api.bularioEletronico;

import java.util.concurrent.CompletableFuture;

import br.ufpa.app.android.amu.v1.integracao.api.bularioEletronico.json.BularioEletronicoJson;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BularioEletronicoClient {

	@Headers({ "accept: application/json", "authorization: Guest" })
	@GET("/api/consulta/bulario?")
	CompletableFuture<BularioEletronicoJson> getBulario(@Query("count") int count,
														@Query("filter[nomeProduto]") String nomeProduto, @Query("page") int page);

	@Headers({ "accept: application/json", "authorization: Guest" })
	@GET("/api/consulta/medicamentos/arquivo/bula/parecer/{nomeArquivo}/?Authorization=")
	Call<ResponseBody> getArquivoBula(@Path("nomeArquivo") String nomeArquivo);

}