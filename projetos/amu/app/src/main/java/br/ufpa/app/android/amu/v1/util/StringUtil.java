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
	private static final Charset UTF_16 = Charset.forName("UTF-16BE");

	public static final boolean isVazioOrNull(String valor) {
		if (valor != null && !valor.trim().equals("")) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Retorna true caso o valor1 seja igual ao valor 2, caso contrario retorna
	 * false.
	 * 
	 * @param valor1
	 * @param valor2
	 * @return boolean
	 */
	public static boolean equals(String valor1, String valor2) {
		String aux1 = isVazioOrNull(valor1) ? "" : valor1;
		String aux2 = isVazioOrNull(valor2) ? "" : valor2;
		if (aux1.equals(aux2)) {
			return true;
		}
		return false;
	}

	/**
	 * Retorna uma string formatada a direita, conforme tamanho passado e o valor da
	 * flag
	 * 
	 * @param tamanho
	 * @param entrada
	 * @param flag
	 * @return java.lang.String
	 */
	public static final String padL(int tamanho, Object entrada, String flag) {
		String textoEntrada;
		if (entrada != null) {
			textoEntrada = entrada.toString();
		} else {
			textoEntrada = "";
		}
		StringBuilder result = new StringBuilder();
		//
		int tamanhoRestante = tamanho - textoEntrada.length();
		//
		while (result.length() < tamanhoRestante) {
			result.append(flag);
		}
		result.append(textoEntrada);
		return result.toString();
	}

	/**
	 * Retorna uma string formatada a esquerda, coforme tamanho passado e o valor da
	 * flag
	 * 
	 * @param tamanho
	 * @param entrada
	 * @param flag
	 * @return java.lang.String
	 */
	public static final String padR(int tamanho, Object entrada, String flag) {
		StringBuilder result = new StringBuilder();
		if (entrada != null) {
			result.append(entrada.toString());
		} else {
			result.append("");
		}
		while (result.length() < tamanho) {
			result.append(flag);
		}
		return result.toString();
	}

	/**
	 * <p>
	 * Returns the number of characters in the given String that are the same as the
	 * given character. Matching is case-sensitive; only exact matches are counted.
	 * </p>
	 * 
	 * @param str         String whose characters are to be examined
	 * @param countedChar character to look for
	 * @return number of occurrences of given character in the String
	 * @since 1.0
	 */
	public static final int count(final String str, final char countedChar) {
		if (str == null) {
			throw new NullPointerException();
		}
		int count = 0;
		int index = 0;
		while ((index = str.indexOf(countedChar, index)) >= 0) {
			count++;
			index++; // because we want to continue looking beyond the match
		}
		return count;
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

	public static final String removerAcentos(String str) {
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
