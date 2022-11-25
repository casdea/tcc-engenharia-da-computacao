package br.ufpa.app.android.amu.v1.util;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DataUtil {

    public static Date encodeDateByDiaMesAno(int day, int month, int year)  {
        Date data = null;
        try {
            Calendar cal = GregorianCalendar.getInstance();
            cal.setTime(new Date());
            data = (new SimpleDateFormat("dd/MM/yyyy")).parse(day + "/" + month + "/" + year);
        }
        catch (ParseException e)
        {
            Log.i("Erro ao converter data ",e.getMessage());
        }

        return data;
    }

    public static Date encodeTimeByHoraMinuto(int hourOfDay, int minute) {
        Date hora = null;
        try {
            Calendar cal = GregorianCalendar.getInstance();
            cal.setTime(new Date());
            hora = (new SimpleDateFormat("HH:mm")).parse(hourOfDay + ":" + minute);
        }
        catch (ParseException e)
        {
            Log.i("Erro ao converter hora ",e.getMessage());
        }

        return hora;
    }

    public static final String convertDateToString(java.util.Date date)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(date);
    }

    public static final String convertTimeToString(java.util.Date date)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(date);
    }

    public static final Date convertStringToDate(String str)
    {
        try
        {
            if (StringUtil.isVazioOrNull(str))
            {
                Log.e("Conversao de Data ","Data não pode ser nula ou vazia "+str);
                return null;
            }
            SimpleDateFormat sdf = new SimpleDateFormat( "dd/MM/yyyy");
            Date data = new Date(sdf.parse(str).getTime());
            return data;
        }
        catch (ParseException e)
        {
            Log.e("Conversao de Data ","Não foi possível converter a data "+str);
            return null;
        }
    }

    public static final boolean isDataValida(String str)
    {
        if (str == null || str.trim().equals(""))
        {
            return false;
        }
        try
        {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            format.setLenient(false);
            @SuppressWarnings("unused")
            java.util.Date data = new Date(format.parse(str).getTime());
            return true;
        }
        catch (ParseException e)
        {
            return false;
        }
    }

    public static SimpleDateFormat converteStringToTime(String hora) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        simpleDateFormat.format(Calendar.getInstance());
        return simpleDateFormat;
    }
}