package com.ruoyi.tt.debug;

import java.util.ArrayList;
import java.util.List;

public class JSONArray extends JSON
{
    public List<Object> list;
    public JSONArray()
    {
        this.list = new ArrayList<>();
    }
    public JSONArray(List<Object> list)
    {
        this.list = list;
    }
    public JSONArray(String json)
    {
        this(new JSONToken(json));
    }
    public JSONArray(JSONToken token)
    {
        Object nextValue = token.nextValue();
        if (nextValue instanceof JSONArray)
        {
            this.list = ((JSONArray) nextValue).list;
        }
    }

    public JSONArray put(Object value)
    {
        if (value instanceof Integer || value instanceof Long || value instanceof Float || value instanceof Double || value instanceof Boolean || value instanceof String || value instanceof JSONArray || value instanceof JSONObject)
        {
            list.add(value);
        }
        else
        {

        }
        return this;
    }

    public Object opt(int index)
    {
        return list.get(index);
    }

    public int optInt(int index)
    {
        return optInt(index, 0);
    }
    public int optInt(int index, int defValue)
    {
        return toInteger(list.get(index), defValue);
    }

    public float optFloat(int index)
    {
        return optFloat(index, 0F);
    }
    public float optFloat(int index, float defValue)
    {
        return toFloat(list.get(index), defValue);
    }

    public long optLong(int index)
    {
        return optLong(index, 0L);
    }
    public long optLong(int index, long defValue)
    {
        return toLong(list.get(index), defValue);
    }

    public double optDouble(int index)
    {
        return optDouble(index, 0.0D);
    }
    public double optDouble(int index, double defValue)
    {
        return toDouble(list.get(index), defValue);
    }

    public boolean optBoolean(int index)
    {
        return optBoolean(index, false);
    }
    public boolean optBoolean(int index, boolean defValue)
    {
        return toBoolean(list.get(index), defValue);
    }

    public String optString(int index)
    {
        return optString(index, null);
    }
    public String optString(int index, String defValue)
    {
        return toString(list.get(index), defValue);
    }

    public JSONArray optJSONArray(int index)
    {
        return optJSONArray(index, null);
    }
    public JSONArray optJSONArray(int index, JSONArray defValue)
    {
        if (list.get(index) instanceof JSONArray)
            return (JSONArray) list.get(index);
        else if (list.get(index) instanceof String)
            return new JSONArray((String) list.get(index));
        return defValue;
    }

    public JSONObject optJSONObject(int index)
    {
        return optJSONObject(index, null);
    }
    public JSONObject optJSONObject(int index, JSONObject defValue)
    {
        if (list.get(index) instanceof JSONObject)
            return (JSONObject) list.get(index);
        else if (list.get(index) instanceof String)
            return new JSONObject((String) list.get(index));
        return defValue;
    }

    public String optDate(int index, String format)
    {
        return optDate(index, format, null);
    }
    public String optDate(int index, String format, String defValue)
    {
        return toDate(list.get(index), format, defValue);
    }

    public long optTime(int index, String format)
    {
        return optTime(index, format, 0L);
    }
    public long optTime(int index, String format, long defValue)
    {
        return toTime(list.get(index), format, defValue);
    }
    public int length()
    {
        return list.size();
    }

    public JSONArray remove(int index)
    {
        list.remove(index);
        return this;
    }
}
