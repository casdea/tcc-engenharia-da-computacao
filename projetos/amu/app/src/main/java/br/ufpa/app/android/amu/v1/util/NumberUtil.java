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
	public static boolean isZeroOrNull(Integer valor)
	{
		return valor == null || valor.equals(Integer.valueOf(0));
	}

	public static String toString(Integer valor)
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
	 * Retorna true caso o valor do Long seja zero ou null, caso contrario retorna false
	 * 
	 * @param valor
	 * @return java.lang.Integer
	 */
	public static boolean isZeroOrNull(Long valor)
	{
		return valor == null || valor.equals(Long.valueOf(0));
	}

	public static String toString(Long valor)
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
	 * Retorna true caso o valor do Double seja zero ou null, caso contrario retorna false
	 * 
	 * @param valor
	 * @return java.lang.Double
	 */
	public static boolean isZeroOrNull(Double valor)
	{
		return valor == null || valor.equals(Double.valueOf(0.0));
	}

	public static String toString(Double valor)
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


}