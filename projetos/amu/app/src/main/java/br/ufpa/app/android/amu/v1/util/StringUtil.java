package br.ufpa.app.android.amu.v1.util;

import java.nio.charset.Charset;
import java.util.UUID;

public abstract class StringUtil {
	/**
	 * Retorna true caso o valor da String seja vazio ou null, caso contrario
	 * retorna false
	 * 
	 * @param valor
	 * @return java.lang.String
	 */
	public static boolean isVazioOrNull(String valor) {
		return valor == null || valor.trim().equals("");
	}

	public static String createId() {
		return UUID.randomUUID().toString();
	}

	public static int findByValor(String[] lista, String valor)
	{
		for (int i = 0; i < lista.length; i++) {
			if (lista[i].equals(valor)) {
				return i;
			}
		}

		return -1;
	}

	public static String removerAcentos(String str) {
		if (str == null) {
			return "";
		}
		str = str.replaceAll("[áàâãª]", "a");
		str = str.replaceAll("[ÁÀÂÃ]", "A");
		str = str.replaceAll("[éèê]", "e");
		str = str.replaceAll("[ÉÈÊ]", "E");
		str = str.replaceAll("[íìî]", "i");
		str = str.replaceAll("[ÍÌÎ]", "I");
		str = str.replaceAll("[óòôõº°]", "o");
		str = str.replaceAll("[ÓÒÔÕ]", "O");
		str = str.replaceAll("[úùû]", "u");
		str = str.replaceAll("[ÚÙÛ]", "U");
		str = str.replaceAll("[çý]", "c");
		str = str.replaceAll("[ÇÝ]", "C");
		return str;
	}
}
