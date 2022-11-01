package com.ruoyi.tt.debug;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;

public class JSONObject extends JSON
{
    public LinkedHashMap<String, Object> maps;
    public JSONObject()
    {
        this.maps = new LinkedHashMap<>();
    }
    public JSONObject(String json)
    {
        this(new JSONToken(json));
    }
    public JSONObject(JSONToken token)
    {
        Object nextValue = token.nextValue();
        if (nextValue instanceof JSONObject)
        {
            this.maps = ((JSONObject) nextValue).maps;
        }
    }
    public JSONObject put(String name, Object value)
    {
        maps.put(name, value);
        return this;
    }

    public Object opt(String name)
    {
        return maps.get(name);
    }

    public int optInt(String name)
    {
        return optInt(name, 0);
    }
    public int optInt(String name, int defValue)
    {
        return toInteger(maps.get(name), defValue);
    }

    public float optFloat(String name)
    {
        return optFloat(name, 0F);
    }
    public float optFloat(String name, float defValue)
    {
        return toFloat(maps.get(name), defValue);
    }

    public long optLong(String name)
    {
        return optLong(name, 0L);
    }
    public long optLong(String name, long defValue)
    {
        return toLong(maps.get(name), defValue);
    }

    public double optDouble(String name)
    {
        return optDouble(name, 0.0D);
    }
    public double optDouble(String name, double defValue)
    {
        return toDouble(maps.get(name), defValue);
    }

    public boolean optBoolean(String name)
    {
        return optBoolean(name, false);
    }
    public boolean optBoolean(String name, boolean defValue)
    {
        return toBoolean(maps.get(name), defValue);
    }

    public String optString(String name)
    {
        return optString(name, null);
    }
    public String optString(String name, String defValue)
    {
        return toString(maps.get(name), defValue);
    }

    public JSONArray optJSONArray(String name)
    {
        return optJSONArray(name, null);
    }
    public JSONArray optJSONArray(String name, JSONArray defValue)
    {
        if (maps.get(name) instanceof JSONArray)
            return (JSONArray) maps.get(name);
        else if (maps.get(name) instanceof String)
        {
            maps.put(name, new JSONArray((String) maps.get(name)));
            return (JSONArray) maps.get(name);
        }
        return defValue;
    }

    public JSONObject optJSONObject(String name)
    {
        return optJSONObject(name, null);
    }
    public JSONObject optJSONObject(String name, JSONObject defValue)
    {
        if (maps.get(name) instanceof JSONObject)
            return (JSONObject) maps.get(name);
        else if (maps.get(name) instanceof String)
        {
            maps.put(name, new JSONObject((String) maps.get(name)));
            return (JSONObject) maps.get(name);
        }
        return defValue;
    }

    public String optDate(String name, String format)
    {
        return optDate(name, format, null);
    }
    public String optDate(String name, String format, String defValue)
    {
        return toDate(maps.get(name), format, defValue);
    }

    public long optTime(String name, String format)
    {
        return optTime(name, format, 0L);
    }
    public long optTime(String name, String format, long defValue)
    {
        return toTime(maps.get(name), format, defValue);
    }

    public int length()
    {
        return maps.size();
    }
    public boolean has(String name)
    {
        return maps.containsKey(name);
    }
    public Iterator<String> keys()
    {
        return maps.keySet().iterator();
    }
    public JSONArray names()
    {
        return maps.isEmpty() ? new JSONArray() : new JSONArray(new ArrayList<Object>(maps.keySet()));
    }
    public JSONObject remove(String name)
    {
        maps.remove(name);
        return this;
    }
}