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
	 * Retorna true caso o valor do Integer seja zero ou null, caso contrario retorna false
	 * 
	 * @param valor
	 * @return boolean
	 */
	public static final boolean isZeroOrNull(Integer valor)
	{
		if (valor != null && !valor.equals(Integer.valueOf(0)))
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
		Integer aux1 = isZeroOrNull(valor1) ? Integer.valueOf(0) : valor1;
		Integer aux2 = isZeroOrNull(valor2) ? Integer.valueOf(0) : valor2;
		if (aux1.equals(aux2))
		{
			return true;
		}
		return false;
	}

	/**
	 * Retorna true caso o valor do Long seja zero ou null, caso contrario retorna false
	 * 
	 * @param valor
	 * @return java.lang.Integer
	 */
	public static final boolean isZeroOrNull(Long valor)
	{
		if (valor != null && !valor.equals(Long.valueOf(0)))
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
		Long aux1 = isZeroOrNull(valor1) ? Long.valueOf(0) : valor1;
		Long aux2 = isZeroOrNull(valor2) ? Long.valueOf(0) : valor2;
		if (aux1.equals(aux2))
		{
			return true;
		}
		return false;
	}

	/**
	 * Retorna true caso o valor do Double seja zero ou null, caso contrario retorna false
	 * 
	 * @param valor
	 * @return java.lang.Double
	 */
	public static final boolean isZeroOrNull(Double valor)
	{
		if (valor != null && !valor.equals(Double.valueOf(0.0)))
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
		Double aux1 = isZeroOrNull(valor1) ? Double.valueOf(0.0) : valor1;
		Double aux2 = isZeroOrNull(valor2) ? Double.valueOf(0.0) : valor2;
		if (aux1.equals(aux2))
		{
			return true;
		}
		return false;
	}

}