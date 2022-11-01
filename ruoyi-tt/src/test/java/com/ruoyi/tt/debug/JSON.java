package com.ruoyi.tt.debug;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class JSON extends JSONString
{
    public static Integer toInteger(Object value, Integer defValue)
    {
        if (value instanceof Integer)
            return (Integer) value;
        else if (value instanceof Number)
            return ((Number) value).intValue();
        else if (value instanceof String)
            return Integer.parseInt(value.toString());
        return defValue;
    }
    public static Float toFloat(Object value, Float defValue)
    {
        if (value instanceof Float)
            return (Float) value;
        else if (value instanceof Number)
            return ((Number) value).floatValue();
        else if (value instanceof String)
            return Float.parseFloat(value.toString());
        return defValue;
    }
    public static Long toLong(Object value, Long defValue)
    {
        if (value instanceof Long)
            return (Long) value;
        else if (value instanceof Number)
            return ((Number) value).longValue();
        else if (value instanceof String)
            return Long.parseLong(value.toString());
        return defValue;
    }
    public static Double toDouble(Object value, Double defValue)
    {
        if (value instanceof Double)
            return (Double) value;
        else if (value instanceof Number)
            return ((Number) value).doubleValue();
        else if (value instanceof String)
            return Double.parseDouble(value.toString());
        return defValue;
    }
    public static Boolean toBoolean(Object value, Boolean defValue)
    {
        if (value instanceof Boolean)
            return (Boolean) value;
        else if (value instanceof String)
        {
            String string = (String) value;
            if (string.equals("true"))
                return true;
            else if (string.equals("false"))
                return false;
        }
        return defValue;
    }
    public static String toString(Object value, String defValue)
    {
        if (value instanceof String)
            return (String) value;
        else if (value != null)
            return String.valueOf(value);
        return defValue;
    }

    public static String toDate(Object value, String format, String defValue)
    {
        if (value instanceof Long)
        {
            if (value.toString().length() == 13)
                return new SimpleDateFormat(format, Locale.ENGLISH).format(new Date((long) value));
            else if (value.toString().length() == 10)
                return new SimpleDateFormat(format, Locale.ENGLISH).format(new Date(((long) value) * 1000));
        }
        else if (value instanceof String)
        {
            if (value.toString().length() == 13)
                return new SimpleDateFormat(format, Locale.ENGLISH).format(new Date(Long.parseLong(value.toString())));
            else if (value.toString().length() == 10)
                return new SimpleDateFormat(format, Locale.ENGLISH).format(new Date(Long.parseLong(value.toString()) * 1000));
        }
        return defValue;
    }
    public static long toTime(Object value, String format, long defValue)
    {
        if (value instanceof String)
        {
            try
            {
                return new SimpleDateFormat(format, Locale.ENGLISH).parse(value.toString()).getTime();
            }
            catch (ParseException e)
            {
                e.printStackTrace();
            }
        }
        return defValue;
    }

    public static <T extends JSON> T parse(String json)
    {
        return (T) new JSONToken(json).nextValue();
    }
}