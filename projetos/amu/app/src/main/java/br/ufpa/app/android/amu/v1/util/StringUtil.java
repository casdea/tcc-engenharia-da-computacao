package br.ufpa.app.android.amu.v1.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.ufpa.app.android.amu.v1.dao.exception.ErrorCode;
import br.ufpa.app.android.amu.v1.dao.exception.InfraStructureException;

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
	 * Retorna true caso o valor da String seja zero ou vazio ou null, caso
	 * contrario retorna false
	 * 
	 * @param valor
	 * @return boolean
	 */
	public static final boolean isZeroOrVazioOrNull(String valor) {
		if (NumberUtil.existeSomenteNumero(valor) && !isVazioOrNull(valor)) {
			for (int i = 0; i < valor.length(); i++) {
				if (!valor.substring(i, (i + 1)).equals("0")) {
					return false;
				}
			}
			return true;
		}
		return true;
	}

	/**
	 * Retorna null caso o valor da String seja vazio ou null
	 * 
	 * @param valor
	 * @return java.lang.String
	 */
	public static final String getValorOrNull(String valor) {
		if (valor != null && !valor.trim().equals("")) {
			return valor;
		} else {
			return null;
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

	public static final boolean existeAcentos(String str) {
		if (str == null) {
			return false;
		}
		for (int i = 0; i < str.length(); i++) {
			if (str.substring(i, i + 1).matches("[��������]")) {
				return true;
			} else if (str.substring(i, i + 1).matches("[������]")) {
				return true;
			} else if (str.substring(i, i + 1).matches("[������]")) {
				return true;
			} else if (str.substring(i, i + 1).matches("[����������]")) {
				return true;
			} else if (str.substring(i, i + 1).matches("[������]")) {
				return true;
			} else if (str.substring(i, i + 1).matches("[����]")) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Retorna uma string sem acentos
	 * 
	 * @param str
	 * @return java.lang.String
	 */
	public static final String removerAcentos(String str) {
		if (str == null) {
			return "";
		}
		str = str.replaceAll("[����]", "a");
		str = str.replaceAll("[����]", "A");
		str = str.replaceAll("[���]", "e");
		str = str.replaceAll("[���]", "E");
		str = str.replaceAll("[���]", "i");
		str = str.replaceAll("[���]", "I");
		str = str.replaceAll("[������]", "o");
		str = str.replaceAll("[����]", "O");
		str = str.replaceAll("[���]", "u");
		str = str.replaceAll("[���]", "U");
		str = str.replaceAll("[��]", "c");
		str = str.replaceAll("[��]", "C");
		return str;
	}

	public static final String quebraLinha() {
		return "\r\n";
	}

	/**
	 * Retorna uma sting com os caracteres maiusculos
	 * 
	 * @param valor
	 * @return java.lang.String
	 */
	public static final String toUpperCase(String valor) {
		if (valor == null) {
			return null;
		} else if (valor.trim().equals("")) {
			return valor;
		} else {
			return valor.toUpperCase();
		}
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

	public static final int posCharOccurrence(String entrada, char caracter, int numero) {
		if (numero > count(entrada, caracter)) {
			return -1;
		}
		if (entrada.indexOf(caracter) < 0) {
			return -1;
		}
		int posicaoRelativa = 0;
		int posicaoAbsoluta = 0;
		String temp = entrada;
		for (int i = 0; i < numero; i++) {
			posicaoRelativa = temp.indexOf(caracter);
			posicaoAbsoluta += (i == 0 ? posicaoRelativa : posicaoRelativa + 1);
			temp = temp.substring(posicaoRelativa + 1, temp.length());
		}
		return posicaoAbsoluta;
	}

	public static String reduzirString(String valor, int tamanho) {
		String novoValor = new String("");
		if (valor == null) {
			return novoValor;
		}
		if (valor.length() > tamanho) {
			novoValor = valor.substring(0, tamanho);
		} else {
			novoValor = valor;
		}
		return novoValor;
	}

	public static String substituirCaracter(String texto, String antigo, String novo) {
		StringBuilder novoTexto = new StringBuilder();
		for (int i = 0; i < texto.length(); i++) {
			if (texto.charAt(i) == antigo.charAt(0))
				novoTexto.append(novo);
			else
				novoTexto.append(texto.charAt(i));
		}
		return novoTexto.toString();
	}

	public static String letraRemessa(int qtde) {
		if (qtde < 10)
			return String.valueOf(qtde);
		else {
			if (qtde == 10)
				return "A";
			else if (qtde == 11)
				return "B";
			else if (qtde == 12)
				return "C";
			else if (qtde == 13)
				return "D";
			else if (qtde == 14)
				return "E";
			else if (qtde == 15)
				return "F";
			else if (qtde == 16)
				return "G";
			else if (qtde == 17)
				return "H";
			else if (qtde == 18)
				return "I";
			else if (qtde == 19)
				return "J";
			else if (qtde == 20)
				return "L";
			else if (qtde == 21)
				return "M";
			else if (qtde == 22)
				return "N";
			else if (qtde == 23)
				return "O";
			else if (qtde == 24)
				return "P";
			else if (qtde == 25)
				return "Q";
			else if (qtde == 26)
				return "R";
			else if (qtde == 27)
				return "S";
			else if (qtde == 28)
				return "T";
			else if (qtde == 11)
				return "U";
			else if (qtde == 12)
				return "V";
			else if (qtde == 11)
				return "X";
			else if (qtde == 12)
				return "Z";
			else if (qtde == 11)
				return "W";
			else if (qtde == 12)
				return "Y";
			else if (qtde == 11)
				return "K";
			else
				return "";
		}
	}

	public static String codificaUTF(String texto) throws UnsupportedEncodingException {
		if (texto != null)
			return URLEncoder.encode(texto, "UTF-8");
		else
			return "";
	}

	public static String codificaUTF16(String texto) throws UnsupportedEncodingException {
		if (texto != null)
			return URLEncoder.encode(texto, "UTF-16");
		else
			return "";
	}

	public static String decodificaUTF(String texto) throws UnsupportedEncodingException {
		if (texto != null)
			return URLDecoder.decode(texto, "UTF-8");
		else
			return "";
	}

	public static String toText(String valor) {
		if (valor == null)
			return "";
		else
			return valor;
	}

	public static String formatarDocumento(String tipoPessoa, String documento) {
		String documentoFormatado = documento;
		if (documento != null) {
			// fisica
			if (tipoPessoa.equals("F") || tipoPessoa.equals("1")) {
				if (documento.length() == 11) {
					documentoFormatado = documento.substring(0, 3) + "." + documento.substring(3, 6) + "."
							+ documento.substring(6, 9) + "-" + documento.substring(9, 11);
				}
			} else {
				if (documento.length() == 14) {
					documentoFormatado = documento.substring(0, 2) + "." + documento.substring(2, 5) + "."
							+ documento.substring(5, 8) + "/" + documento.substring(8, 12) + "-"
							+ documento.substring(12, 14);
				}
			}
		}
		return documentoFormatado;
	}

	public static String formataNomeSobreNomePessoa(String nome) {
		String[] vNome = nome.split(" ");
		String primeiroNome = vNome[0];
		String ultimoNome = vNome[vNome.length - 1];
		return primeiroNome.substring(0, 1) + primeiroNome.substring(1).toLowerCase() + " " + ultimoNome.substring(0, 1)
				+ ultimoNome.substring(1).toLowerCase();
	}

	public static String calculaModulo11(String numero) throws InfraStructureException {
		if (numero == null) {
			throw new InfraStructureException(ErrorCode.SYSTEM_FAILURE, "N�mero de entrada �nvalido.");
		}
		if (numero.length() != 13) {
			throw new InfraStructureException(ErrorCode.SYSTEM_FAILURE, "N�mero de entrada �nvalido.");
		}
		String numeroBase = "2765432765432";
		int total = 0;
		for (int i = 0; i < numero.length(); i++) {
			String t1 = numero.substring(i, i + 1);
			String t2 = numeroBase.substring(i, i + 1);
			int a = Integer.parseInt(t1);
			int b = Integer.parseInt(t2);
			total = total + (a * b);
		}
		int resto = total % 11;
		String digito = "";
		if (resto == 1)
			digito = "P";
		else if (resto == 0)
			digito = "0";
		else
			digito = String.valueOf(11 - resto);
		return digito;
	}

	public static String textToHex(String text) {
		byte[] palavraBytes = text.getBytes();
		StringBuilder sbHex = new StringBuilder();
		for (int i = 0; i < palavraBytes.length; i++) {
			if (sbHex.length() == 0)
				sbHex.append(String.format("%02X", palavraBytes[i]));
			else
				sbHex.append(" " + String.format("%02X", palavraBytes[i]));
		}
		return sbHex.toString();
	}

	public static byte[] Encode(byte[] ByteArray, int offset, String str) {
		byte[] bytes = str.getBytes(UTF_16);
		System.arraycopy(bytes, 0, ByteArray, offset, bytes.length);
		return ByteArray;
	}

	public static String Decode(byte[] ByteArray, int len) {
		return new String(ByteArray, 0, 2 * len, UTF_16);
	}

	public static String textToUTF16(String text) {
		StringBuilder sbHex = new StringBuilder();
		byte[] bytes = new byte[2 * text.length()];
		Encode(bytes, 0, text);
		for (byte b : bytes) {
			if (sbHex.length() == 0)
				sbHex.append(String.format("%02x ", b));
			else
				sbHex.append(String.format(" " + "%02x ", b));
		}
		return sbHex.toString();
	}

	public static boolean validarEmail(String email) {
		boolean isEmailIdValid = false;
		if (email != null && email.length() > 0) {
			isEmailIdValid = true;
			String[] emails = email.split(",");
			for (int i = 0; i < emails.length; i++) {
				String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
				Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
				Matcher matcher = pattern.matcher(emails[i].trim());
				if (matcher.matches() == false) {
					isEmailIdValid = false;
					break;
				}
			}
		}
		return isEmailIdValid;
	}

	public static String removerCaracterEspecial(String texto) {
		texto = texto.replaceAll("-", "");
		texto = texto.replaceAll(".", "");
		texto = texto.replaceAll("/", "");
		return texto;
	}

	public static String removeSeparadorDecimal(String texto) {
		texto = texto.replaceAll(",", "");
		texto = texto.replaceAll(".", "");
		return texto;
	}

	public static String codificaHTML(String texto) throws UnsupportedEncodingException {
		texto = texto.replace("�", "&ccedil;");
		texto = texto.replace("�", "&aacute;");
		texto = texto.replace("�", "&atilde;");
		texto = texto.replace("�", "&ocirc;");

		texto = texto.replace("�", "&Aacute;");
		texto = texto.replace("�", "&Egrave;");
		texto = texto.replace("�", "&Eacute;");
		texto = texto.replace("�", "&eacute;");
		texto = texto.replace("�", "&eacute;");

		texto = texto.replace("�", "&Atilde;");
		texto = texto.replace("�", "&Uacute;");
		texto = texto.replace("�", "&uacute;");
		texto = texto.replace("�", "&Ccedil;");
		texto = texto.replace("\n", "&#10;");
		texto = texto.replace("�", "&Agrave;");
		texto = texto.replace("�", "&agrave;");
		texto = texto.replace("�", "&Oacute;");
		texto = texto.replace("�", "&ecirc;");
		texto = texto.replace("�", "&otilde;");
		texto = texto.replace("�", "&oacute;");

		texto = texto.replace("�", "&ordm;");
		texto = texto.replace("�", "&ordf;");
		texto = texto.replace("�", "&iacute;");
		
		//

		/*
		 * texto = texto.replace("�", "&ccedil;"); texto = texto.replace("�",
		 * "&aacute;"); texto = texto.replace("�", "&atilde;"); texto =
		 * texto.replace("�", "&ocirc;");
		 * 
		 * texto = texto.replace("�", "&Aacute;"); texto = texto.replace("�",
		 * "&Egrave;"); texto = texto.replace("�", "&Eacute;");
		 * 
		 * texto = texto.replace("�", "&Atilde;"); texto = texto.replace("�",
		 * "&Uacute;"); texto = texto.replace("�", "&uacute;"); texto =
		 * texto.replace("�", "&Ccedil;");
		 * 
		 * texto = texto.replace("�","&Aacute;"); texto = texto.replace("�","&ocirc;");
		 * texto = texto.replace("�","&aacute;"); texto = texto.replace("�","&egrave;");
		 * texto = texto.replace("�","&Ograve;"); texto = texto.replace("�","&ccedil;");
		 * texto = texto.replace("�","&Acirc;"); texto = texto.replace("�","&Euml;");
		 * texto = texto.replace("�","&ograve;"); texto = texto.replace("�","&acirc;");
		 * texto = texto.replace("�","&euml;"); texto = texto.replace("�","&Oslash;");
		 * texto = texto.replace("�","&Ntilde;"); texto = texto.replace("�","&ETH;");
		 * texto = texto.replace("�","&oslash;"); texto = texto.replace("�","&ntilde;");
		 * texto = texto.replace("�","&eth;"); texto = texto.replace("�","&Otilde;");
		 * texto = texto.replace("�","&Aring;"); texto = texto.replace("�","&Yacute;");
		 * texto = texto.replace("�","&aring;"); texto = texto.replace("�","&Iacute;");
		 * texto = texto.replace("�","&Ouml;"); texto = texto.replace("�","&yacute;");
		 * texto = texto.replace("�","&Atilde;"); texto = texto.replace("�","&iacute;");
		 * texto = texto.replace("�","&ouml;"); texto = texto.replace("�","&atilde;");
		 * texto = texto.replace("�","&Icirc;"); //texto =
		 * texto.replace("/"/","&quot;"); texto = texto.replace("�","&Auml;"); texto =
		 * texto.replace("�","&icirc;"); texto = texto.replace("�","&Uacute;"); texto =
		 * texto.replace("<","&lt;"); texto = texto.replace("�","&auml;"); texto =
		 * texto.replace("�","&Igrave;"); texto = texto.replace("�","&uacute;"); texto =
		 * texto.replace(">","&gt;"); texto = texto.replace("�","&AElig;"); texto =
		 * texto.replace("�","&igrave;"); texto = texto.replace("�","&Ucirc;"); texto =
		 * texto.replace("�","&aelig;"); texto = texto.replace("�","&Iuml;"); texto =
		 * texto.replace("�","&ucirc;"); texto = texto.replace("�","&iuml;"); texto =
		 * texto.replace("�","&Ugrave;"); texto = texto.replace("�","&reg;"); texto =
		 * texto.replace("�","&Eacute;"); texto = texto.replace("�","&ugrave;"); texto =
		 * texto.replace("�","&copy;"); texto = texto.replace("�","&eacute;"); texto =
		 * texto.replace("�","&Uuml;"); texto = texto.replace("�","&THORN;"); texto =
		 * texto.replace("�","&Ecirc;"); texto = texto.replace("�","&uuml;"); texto =
		 * texto.replace("�","&thorn;"); texto = texto.replace("�","&Ocirc;"); texto =
		 * texto.replace("�","&szlig;"); texto = texto.replace("&","&amp;");
		 */
		return texto;
	}

	public static String removerZerosFrente(String texto) {
		String retorno = "";
		for (int i = 0; i < texto.length(); i++) {
			if (texto.charAt(i) != '0') {
				retorno = texto.substring(i);
				break;
			}
		}
		return retorno;
	}

	public static final String removerCaracteresInvalidosXML(String str) {
		if (str == null) {
			return "";
		}
		str = str.replaceAll("&", "E");
		return str;
	}

	public static String[] split(String texto, int divisor) {

		if (texto == null)
			texto = "";

		int tamanho = texto.length() / divisor;

		if (tamanho <= 0)
			tamanho = 1;

		if ((tamanho * divisor) < texto.length())
			tamanho = tamanho + 1;

		String[] linhas = new String[tamanho];
		int i = 0;
		while (texto.equals("") == false) {
			if (texto.length() >= divisor) {
				linhas[i] = texto.substring(0, divisor);
				texto = texto.substring(divisor, texto.length());
			} else {
				linhas[i] = texto;
				texto = "";
			}

			i = i + 1;
		}

		return linhas;

	}

	public static String toFirstWordUppercase(String texto) {
		String[] palavras = texto.split(" ");
		String retorno = null;
		for (int i = 0; i < palavras.length; i++) {
			String palavra = palavras[i].trim();
			if (palavra.equals("") == false) {
				if (palavra.length() >= 1) {
					String firstPalavra;
					String restoPalavra = "";
					if (palavra.length() >= 1) {
						if (palavra.length() == 1) {
							firstPalavra = palavra.substring(0, 1).toLowerCase();
						} else {
							firstPalavra = palavra.substring(0, 1).toUpperCase();
							restoPalavra = palavra.substring(1, palavra.length()).toLowerCase();
						}
						if (retorno == null)
							retorno = firstPalavra + restoPalavra;
						else
							retorno = retorno + " " + firstPalavra + restoPalavra;
					}
				}
			}
		}
		return retorno;
	}

	public static String juntarNome(String tipoPessoa, String nomeCompleto) {
		String nomes[] = nomeCompleto.split(" ");
		String nomeCliente = null;
		try {
			if (tipoPessoa.equals("F")) {
				nomeCliente = nomes[0] + " " + nomes[nomes.length - 1];
			} else {
				nomeCliente = nomes[0] + " " + nomes[1];
			}
		} catch (Exception e) {
			nomeCliente = "Prezado Cliente";
		}
		return StringUtil.removerAcentos(nomeCliente);
	}

	public static String getValorString(String valor) {
		if (valor == null)
			return valor + ", \n";
		else
			return "'" + valor + "', \n";
	}

	public static String getValorStringSemVirgula(String valor) {
		if (valor == null)
			return valor + " \n";
		else
			return "'" + valor + "' \n";
	}

	public static String[] divideAnoMes(int anoMes) {
		String[] dividido = new String[2];
		dividido[0] = String.valueOf(anoMes).substring(0, 4);
		dividido[1] = String.valueOf(anoMes).substring(4, 6);

		return dividido;
	}

	public static String[] mySplit(String linha, String separador) {
		List<String> lista = new ArrayList<String>();
		while (linha.contains(separador)) {
			String campo = linha.substring(0, linha.indexOf(separador));
			lista.add(campo);
			linha = linha.substring(linha.indexOf(separador) + 1, linha.length());
		}

		String[] campos = new String[lista.size()];
		int i = 0;
		for (String campo : lista) {
			campos[i] = campo;
			i = i + 1;
		}
		return campos;
	}

	public static boolean isPreenchido(String texto) {
		return texto != null && texto.equals("")==false;	
	}
	
	public static String calculaModulo11Santander(String numero) throws InfraStructureException {
		if (numero == null) {
			throw new InfraStructureException(ErrorCode.SYSTEM_FAILURE, "N�mero de entrada �nvalido.");
		}
		if (numero.length() != 13) {
			throw new InfraStructureException(ErrorCode.SYSTEM_FAILURE, "N�mero de entrada �nvalido.");
		}
		String numeroBase = "2765432765432";
		int total = 0;
		for (int i = 0; i < numero.length(); i++) {
			String t1 = numero.substring(i, i + 1);
			String t2 = numeroBase.substring(i, i + 1);
			int a = Integer.parseInt(t1);
			int b = Integer.parseInt(t2);
			total = total + (a * b);
		}
		int resto = total % 11;
		String digito = "";
		if (resto == 10)
			digito = "1";
		else if (resto == 1)
			digito = "0";
		else if (resto == 0)
			digito = "0";
		else
			digito = String.valueOf(11 - resto);
		return digito;
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
}
