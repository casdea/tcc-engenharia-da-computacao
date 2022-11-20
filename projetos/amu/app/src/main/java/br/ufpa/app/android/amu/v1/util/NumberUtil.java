package br.ufpa.app.android.amu.v1.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import br.ufpa.app.android.amu.v1.dao.exception.ErrorCode;
import br.ufpa.app.android.amu.v1.dao.exception.InfraStructureException;

public abstract class NumberUtil
{
	/**
	 * Retorna null caso o valor do Integer seja zero ou null
	 * 
	 * @param valor
	 * @return java.lang.Integer
	 */
	public static final Integer getValorOrNull(Integer valor)
	{
		if (valor != null && !valor.equals(new Integer(0)))
		{
			return valor;
		}
		else
		{
			return null;
		}
	}

	/**
	 * Retorna true caso o valor do Integer seja zero ou null, caso contrario retorna false
	 * 
	 * @param valor
	 * @return boolean
	 */
	public static final boolean isZeroOrNull(Integer valor)
	{
		if (valor != null && !valor.equals(new Integer(0)))
		{
			return false;
		}
		else
		{
			return true;
		}
	}

	public static final String toString(Integer valor)
	{
		if (valor != null)
		{
			return valor.toString();
		}
		else
		{
			return "";
		}
	}

	/**
	 * Retorna true caso o valor1 seja igual ao valor 2, caso contrario retorna false.
	 * 
	 * @param valor1
	 * @param valor2
	 * @return boolean
	 */
	public static boolean equals(Integer valor1, Integer valor2)
	{
		Integer aux1 = isZeroOrNull(valor1) ? new Integer(0) : valor1;
		Integer aux2 = isZeroOrNull(valor2) ? new Integer(0) : valor2;
		if (aux1.equals(aux2))
		{
			return true;
		}
		return false;
	}

	/**
	 * Retorna null caso o valor do Long seja zero ou null
	 * 
	 * @param valor
	 * @return java.lang.Integer
	 */
	public static final Long getValorOrNull(Long valor)
	{
		if (valor != null && !valor.equals(new Long(0)))
		{
			return valor;
		}
		else
		{
			return null;
		}
	}

	/**
	 * Retorna true caso o valor do Long seja zero ou null, caso contrario retorna false
	 * 
	 * @param valor
	 * @return java.lang.Integer
	 */
	public static final boolean isZeroOrNull(Long valor)
	{
		if (valor != null && !valor.equals(new Long(0)))
		{
			return false;
		}
		else
		{
			return true;
		}
	}

	public static final String toString(Long valor)
	{
		if (valor != null)
		{
			return valor.toString();
		}
		else
		{
			return "";
		}
	}

	/**
	 * Retorna true caso o valor1 seja igual ao valor 2, caso contrario retorna false.
	 * 
	 * @param valor1
	 * @param valor2
	 * @return boolean
	 */
	public static boolean equals(Long valor1, Long valor2)
	{
		Long aux1 = isZeroOrNull(valor1) ? new Long(0) : valor1;
		Long aux2 = isZeroOrNull(valor2) ? new Long(0) : valor2;
		if (aux1.equals(aux2))
		{
			return true;
		}
		return false;
	}

	/**
	 * Retorna null caso o valor do Double seja zero ou null
	 * 
	 * @param valor
	 * @return java.lang.Double
	 */
	public static final Double getValorOrNull(Double valor)
	{
		if (valor != null && !valor.equals(new Double(0.0)))
		{
			return valor;
		}
		else
		{
			return null;
		}
	}

	/**
	 * Retorna true caso o valor do Double seja zero ou null, caso contrario retorna false
	 * 
	 * @param valor
	 * @return java.lang.Double
	 */
	public static final boolean isZeroOrNull(Double valor)
	{
		if (valor != null && !valor.equals(new Double(0.0)))
		{
			return false;
		}
		else
		{
			return true;
		}
	}

	public static final String toString(Double valor)
	{
		if (valor != null)
		{
			return valor.toString();
		}
		else
		{
			return "";
		}
	}

	/**
	 * Retorna true caso o valor1 seja igual ao valor 2, caso contrario retorna false.
	 * 
	 * @param valor1
	 * @param valor2
	 * @return boolean
	 */
	public static final boolean equals(Double valor1, Double valor2)
	{
		Double aux1 = isZeroOrNull(valor1) ? new Double(0.0) : valor1;
		Double aux2 = isZeroOrNull(valor2) ? new Double(0.0) : valor2;
		if (aux1.equals(aux2))
		{
			return true;
		}
		return false;
	}

	public static final boolean existeSomenteNumero(String range)
	{
		for (int i = 0; i < range.length(); i++)
		{
			if (!range.substring(i, i + 1).matches("[0-9]"))
			{
				return false;
			}
		}
		return true;
	}

	/**
	 * Remove todos os caracteres que n�o forem numericos
	 * 
	 * @param valor
	 * @return java.lang.String
	 */
	public static final String toRemoveAllIsNotNumber(String valor)
	{
		StringBuffer newValor = new StringBuffer();
		String[] range = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		for (int i = 1; i <= valor.length(); i++)
		{
			if (Arrays.binarySearch(range, valor.substring(i - 1, i)) >= 0)
			{
				newValor.append(valor.substring(i - 1, i));
			}
		}
		return newValor.toString().trim();
	}

	/**
	 * Retorna um double arredondado com apenas 2 casas decimais
	 * 
	 * @param value
	 * @return double
	 */
	public static final double round(double value)
	{
		int decimalPlace = 2;
		double powerOfTen = 1;
		while (decimalPlace-- > 0)
		{
			powerOfTen *= 10.0;
		}
		return Math.round(value * powerOfTen) / powerOfTen;
	}

	public static final int convertStrToInt(String valor)
	{
		return existeSomenteNumero(valor) ? Integer.parseInt(valor) : 0;
	}

	/**
	 * Converte um Double em uma string formatada sem pontos ou virgulas.
	 * 
	 * @param value
	 * @return casasDecimais
	 */
	public static final String convertDoubleToString(Double valor, int casasDecimais)
	{
		if (valor == null || valor.equals(new Double(0)))
		{
			return "0";
		}
		String auxValor = String.valueOf(valor);
		String s1 = auxValor.substring(0, auxValor.lastIndexOf('.'));
		String s2 = StringUtil.padR(casasDecimais, auxValor.substring(auxValor.lastIndexOf('.') + 1, auxValor.length()), "0");
		return s1 + s2;
	}

	public static final String getNumber(Object valor)
	{
		Locale locale = new Locale("pt", "BR");
		NumberFormat numberFormat = NumberFormat.getNumberInstance(locale);
		numberFormat.setMinimumFractionDigits(2);
		numberFormat.setMaximumFractionDigits(2);
		return numberFormat.format(valor);
	}

	public static final double getNumber(String valor)
	{
		Locale locale = new Locale("pt", "BR");
		NumberFormat numberFormat = NumberFormat.getNumberInstance(locale);
		numberFormat.setMinimumFractionDigits(2);
		numberFormat.setMaximumFractionDigits(2);
		try
		{
			return new Double(numberFormat.parse(valor).doubleValue());
		}
		catch (ParseException e)
		{
			return new Double(0.0);
		}
	}

	public static final Long getParteNumericaNoFormularioRenach(String noFormularioRenach) throws InfraStructureException
	{
		if (StringUtil.isVazioOrNull(noFormularioRenach) || noFormularioRenach.length() != 11 || !noFormularioRenach.substring(0, 2).equals("PA") || !existeSomenteNumero(noFormularioRenach.substring(2)))
		{
			throw new InfraStructureException(ErrorCode.SYSTEM_FAILURE, "N� Formulario Renach inv�lido.");
		}
		return new Long(noFormularioRenach.substring(2));
	}

	public static final boolean isNoFormularioRenachValido(String noFormularioRenach)
	{
		if (StringUtil.isVazioOrNull(noFormularioRenach) || noFormularioRenach.length() != 11 || !noFormularioRenach.substring(0, 2).equals("PA") || !existeSomenteNumero(noFormularioRenach.substring(2)))
		{
			return false;
		}
		return true;
	}

	public static final Double getValorOrZero(Double valor)
	{
		if (valor == null)
		{
			return Double.valueOf(0);
		}
		else
		{
			return valor;
		}
	}

	public static String getRandomizedNumberAsString()
	{
		try
		{
			// Create a secure random number generator
			SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
			// Get 1024 random bits
			byte[] bytes = new byte[1024 / 8];
			sr.nextBytes(bytes);
			// Create two secure number generators with the same seed
			int seedByteCount = 10;
			byte[] seed = sr.generateSeed(seedByteCount);
			sr = SecureRandom.getInstance("SHA1PRNG");
			sr.setSeed(seed);
			SecureRandom sr2 = SecureRandom.getInstance("SHA1PRNG");
			sr2.setSeed(seed);
			return (Long.toOctalString(sr.nextLong()));
		}
		catch (NoSuchAlgorithmException e)
		{
			return "0010101100";
		}
	}

	public static String formatadorDouble(Double valor, int decimal)
	{
		if (valor == null)
		{
			valor = new Double(0);
		}
		DecimalFormat formatador = null;
		if (decimal > 0)
			formatador = new DecimalFormat("####0." + StringUtil.padL(decimal, "", "0"));
		else
			formatador = new DecimalFormat("####0");
		String s = formatador.format(valor);
		s = s.replace(',', '.');
		return s;
	}

	public static String formataMilhares(Double valor) throws Exception
	{
		if (valor == null || valor.toString().trim().equals(""))
		{
			return "0,00";
		}
		else
		{
			DecimalFormat df = new DecimalFormat("###,##0.00");
			df.setDecimalFormatSymbols(new DecimalFormatSymbols(new Locale("PT", "BR")));
			return df.format(valor);
		}
	}

	public static String toText(Double valor)
	{
		if (valor == null)
			return "0";
		else
			return String.valueOf(valor);
	}

	public static String toText(Integer valor)
	{
		if (valor == null)
			return "0";
		else
			return String.valueOf(valor);
	}

	public static String toText(Short valor)
	{
		if (valor == null)
			return "0";
		else
			return String.valueOf(valor);
	}

	public static String toText(Long valor)
	{
		if (valor == null)
			return "0";
		else
			return String.valueOf(valor);
	}

	public static String formatDoubleToFebraban(Double valor, int casasDecimais)
	{
		if (valor == null || valor.equals(new Double(0)))
		{
			return "0";
		}
		String auxValor = String.valueOf(valor);
		String s1 = auxValor.substring(0, auxValor.lastIndexOf('.'));
		String s2 = StringUtil.padR(casasDecimais, auxValor.substring(auxValor.lastIndexOf('.') + 1, auxValor.length()), "0");
		return s1 + s2;
	}

	public static Double formatFebrabanToDouble(String valor)
	{
		if (valor == null || valor.equals("") || valor.length() <= 1)
			return 0D;
		return getNumber(valor.substring(0, valor.length() - 2) + "," + valor.substring(valor.length() - 2, valor.length()));
	}

	public static final boolean isPreenchido(Double valor)
	{
		if (valor != null && !valor.equals(Double.valueOf(0)))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public static final boolean isPreenchido(Long valor)
	{
		if (valor != null && !valor.equals(Long.valueOf(0)))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public static final boolean isPreenchido(Integer valor)
	{
		if (valor != null && !valor.equals(Integer.valueOf(0)))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public static final Double getNumberOrZero(Double valor)
	{
		try
		{
			if (valor == null)
				return Double.valueOf(0);
			else
				return valor;
		}
		catch (Exception e)
		{
			return Double.valueOf(0);
		}
	}

	public static final Integer getNumberOrZero(Integer valor)
	{
		try
		{
			if (valor == null)
				return Integer.valueOf(0);
			else
				return valor;
		}
		catch (Exception e)
		{
			return Integer.valueOf(0);
		}
	}

	public static final BigInteger getNumberOrZero(BigInteger valor)
	{
		try
		{
			if (valor == null)
				return BigInteger.valueOf(0);
			else
				return valor;
		}
		catch (Exception e)
		{
			return BigInteger.valueOf(0);
		}
	}

	public static final Double converteObjectToDouble(Object valor)
	{
		if (valor == null)
			return 0D;
		if (valor instanceof BigDecimal)
			return ((BigDecimal) valor).doubleValue();
		else
			return ((Double) valor).doubleValue();
	}

	/**
	 * Receber um numero double e arredonda para cima todos os valores maior q 0.1 * Exemplo: (53.04); (53.80); (53.01) todos arredondado fica 54
	 */
	public static final Long RoundUp(Double valor)
	{
		int intValor = fracao(NumberUtil.round(valor));
		long somaVal = 0;
		if (intValor > 0 && intValor < 50)
		{
			somaVal = (int) (Math.round(valor) + 1);
		}
		else
			somaVal = Math.round(valor);
		return somaVal;
	}

	public static final Integer fracao(Double double1)
	{
		double number = double1;
		String numberAsString = String.valueOf(number);
		String decimalPart = numberAsString.split("\\.")[1];
		int intNumber = Integer.parseInt(decimalPart);
		return intNumber;
	}

	public static final Long getNumberOrZero(Long valor)
	{
		try
		{
			if (valor == null)
				return Long.valueOf(0);
			else
				return valor;
		}
		catch (Exception e)
		{
			return Long.valueOf(0);
		}
	}

	public static final Integer getValorNumerico(Boolean valor)
	{
		if (valor)
		{
			return 1;
		}
		else
		{
			return 0;
		}
	}

	public static String formatadorDoubleNotaFiscalSalinas(Double valor, int decimal)
	{
		if (valor == null)
		{
			valor = new Double(0);
		}
		DecimalFormat formatador = null;
		if (decimal > 0)
			formatador = new DecimalFormat("####0." + StringUtil.padL(decimal, "", "0"));
		else
			formatador = new DecimalFormat("####0");
		String s = formatador.format(valor);
		return s;
	}

	public static List<Integer> getMeses(Integer referenciaInicio, Integer referenciaFim)
	{
		// TODO Auto-generated method stub
		Integer r = referenciaInicio;
		List<Integer> meses = new ArrayList<Integer>();
		String ref, ano, mes = null;
		while (true)
		{
			ref = Integer.toString(r);
			ano = ref.substring(0, 4);
			mes = ref.substring(4, 6);
			if (mes.equals("13"))
			{
				ano = Integer.toString(new Integer(ano).intValue() + 1);
				mes = "01";
			}
			r = new Integer(ano + mes);
			meses.add(r);
			r++;
			if (r.intValue() > referenciaFim.intValue())
			{
				break;
			}
		}
		return meses;
	}

	public static String getReferenciaAnterior(String ref)
	{
		int anoAnt = Integer.parseInt(ref.substring(0, 4));
		int mesAnt = Integer.parseInt(ref.substring(4, 6));
		if (mesAnt == 1)
		{
			anoAnt = anoAnt - 1;
			mesAnt = 12;
		}
		else
			mesAnt = mesAnt - 1;
		return anoAnt + StringUtil.padL(2, mesAnt, "0");
	}
	
	public static Double converteTextoToDouble(String texto)
	{
		String valor = texto;
		if (valor == null)
			valor = "0";
		valor = valor.replace("R$", "");
		
		if (valor.contains(",") && valor.contains("."))
		{
			valor = valor.replace(",", "");
		}
		else 
		if (valor.contains(",")) {
			valor = valor.replace(",", ".");
		}
		
		return Double.parseDouble(valor);
	}

	public static String ReferenciaSeguinte(String anoMes, int qtde)
	{
		int anoAnt = Integer.parseInt(anoMes.substring(0, 4));
		int mesAnt = Integer.parseInt(anoMes.substring(4, 6));
		if (mesAnt == 12)
		{
			anoAnt = anoAnt + 1;
			mesAnt = 1;
		}
		else
			mesAnt = mesAnt + qtde;
		return anoAnt + StringUtil.padL(2, mesAnt, "0");
	}

	public static final int getInteger(String valor)
	{
		try
		{
			if (StringUtil.isVazioOrNull(valor)) return 0;
			
			return Integer.valueOf(valor);
		}
		catch (Exception e)
		{
			return 0;
		}
	}


}