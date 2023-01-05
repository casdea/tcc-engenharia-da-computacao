package br.ufpa.app.android.amu.v1.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import br.ufpa.app.android.amu.v1.BuildConfig;

public class DataUtil {


     public static Date encodeTimeByHoraMinuto(int hourOfDay, int minute) {
        Date hora = null;
        try {
            Calendar cal = GregorianCalendar.getInstance();
            cal.setTime(new Date());
            hora = (new SimpleDateFormat("HH:mm",java.util.Locale.getDefault())).parse(hourOfDay + ":" + minute);
        }
        catch (ParseException e)
        {
            if (BuildConfig.DEBUG)
                Log.i("Erro ao converter hora ",e.getMessage());
        }

        return hora;
    }

    public static String convertDateToString(java.util.Date date)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy",java.util.Locale.getDefault());
        return sdf.format(date);
    }

    public static String convertDateTimeToString(java.util.Date date)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm",java.util.Locale.getDefault());
        return sdf.format(date);
    }

    public static String convertTimeToString(java.util.Date date)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm",java.util.Locale.getDefault());
        return sdf.format(date);
    }

    public static Date convertStringToDate(String str)
    {
        try
        {
            if (StringUtil.isVazioOrNull(str))
            {
                Log.e("Conversao de Data ","Data não pode ser nula ou vazia "+str);
                return null;
            }
            SimpleDateFormat sdf = new SimpleDateFormat( "dd/MM/yyyy",java.util.Locale.getDefault());
            return new Date(sdf.parse(str).getTime());
        }
        catch (ParseException e)
        {
            Log.e("Conversao de Data ","Não foi possível converter a data "+str);
            return null;
        }
    }

    public static boolean isDataInvalida(String str)
    {
        if (str == null || str.trim().equals(""))
        {
            return true;
        }
        try
        {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy",java.util.Locale.getDefault());
            format.setLenient(false);
            @SuppressWarnings("unused")
            java.util.Date data = new Date(format.parse(str).getTime());
            return false;
        }
        catch (ParseException e)
        {
            return true;
        }
    }

    public static String unmask(String s) {
        return s.replaceAll("[.]", "").replaceAll("[-]", "")
                .replaceAll("[/]", "").replaceAll("[(]", "")
                .replaceAll("[)]", "");
    }

    public static TextWatcher mask(final String mask, final EditText ediTxt) {
        return new TextWatcher() {
            boolean isUpdating;
            String old = "";

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str = unmask(s.toString());
                String mascara = "";
                if (isUpdating) {
                    old = str;
                    isUpdating = false;
                    return;
                }
                int i = 0;
                for (char m : mask.toCharArray()) {
                    if (m != '#' && str.length() > old.length()) {
                        mascara += m;
                        continue;
                    }
                    try {
                        mascara += str.charAt(i);
                    } catch (Exception e) {
                        break;
                    }
                    i++;
                }
                isUpdating = true;
                ediTxt.setText(mascara);
                ediTxt.setSelection(mascara.length());
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }
        };
    }

    public static Date getDataAtual()
    {
        return new java.util.Date();
    }

    public static Date convertStringToDate(String str, String formato) throws Exception
    {
        try
        {
            if (StringUtil.isVazioOrNull(str))
            {
                throw new Exception("Data não pode ser nula ou vazia");
            }
            SimpleDateFormat sdf = new SimpleDateFormat(formato,java.util.Locale.getDefault());
            return new Date(sdf.parse(str).getTime());
        }
        catch (ParseException e)
        {
            throw new Exception("Não foi possível converter a data");
        }
    }

    public static Date somaHoras(Date dataInicial, int horas) throws Exception
    {
        if (dataInicial == null)
        {
            throw new Exception("Data inicial não pode ser nula");
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(dataInicial);
        cal.add(Calendar.HOUR, horas);
        return new Date(cal.getTimeInMillis());
    }

    public static Date somaMinutos(Date dataInicial, int minutos)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dataInicial);
        cal.add(Calendar.MINUTE, minutos);
        return new Date(cal.getTimeInMillis());
    }

    public static int getDiferencaEmMinutos(Date dataInicio, Date dataFim)
    {
        Calendar calInicio = Calendar.getInstance();
        Calendar calFim = Calendar.getInstance();
        calInicio.setTime(dataInicio);
        calFim.setTime(dataFim);
        long m1 = calInicio.getTimeInMillis();
        long m2 = calFim.getTimeInMillis();
        return (int) ((m2 - m1) / (60 * 1000));
    }

    public static Date convertStringToDateTime(String str) {
        try {
            return convertStringToDate(str, "dd/MM/yyyy HH:mm");
        } catch (Exception e)
        {
            return null;
        }
    }

}
