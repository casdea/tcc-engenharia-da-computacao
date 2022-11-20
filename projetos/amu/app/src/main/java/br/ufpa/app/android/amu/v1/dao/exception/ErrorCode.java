package br.ufpa.app.android.amu.v1.dao.exception;

public interface ErrorCode {

	public final int SYSTEM_FAILURE = 1;

	public final int NULL_KEY = 2;

	public final int CHAVE_ESTRANGEIRA = 3;

	public final int CAMPO_OBRIGATORIO = 4;

	public final int TIME_OUT = 5;
	
	public final int DEPOSITO_CLIENTE_NAO_LOCALIZADO = 6;
}
