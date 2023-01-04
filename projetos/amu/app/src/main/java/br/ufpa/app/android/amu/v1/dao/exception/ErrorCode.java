package br.ufpa.app.android.amu.v1.dao.exception;

public interface ErrorCode {

	int SYSTEM_FAILURE = 1;

	int NULL_KEY = 2;

	int CHAVE_ESTRANGEIRA = 3;

	int CAMPO_OBRIGATORIO = 4;

	int TIME_OUT = 5;
	
	int DEPOSITO_CLIENTE_NAO_LOCALIZADO = 6;
}
