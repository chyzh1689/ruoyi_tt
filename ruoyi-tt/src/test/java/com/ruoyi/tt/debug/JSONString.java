package com.ruoyi.tt.debug;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

public class JSONString implements Serializable
{
    @Override
    public String toString()
    {
        StringBuilder output = new StringBuilder();
        if (this instanceof JSONArray)
        {
            Iterator<Object> iterator = ((JSONArray) this).list.iterator();
            output.append('[');
            while (iterator.hasNext())
            {
                final Object next = iterator.next();
                if (next == null)
                {
                    output.append("null");
                }
                else
                {
                    if (next instanceof JSONArray)
                    {
                        output.append(next.toString());
                    }
                    else if (next instanceof JSONObject)
                    {
                        output.append(next.toString());
                    }
                    else if (next instanceof Integer || next instanceof Long || next instanceof Float || next instanceof Double || next instanceof Boolean)
                    {
                        output.append(next);
                    }
                    else if (next instanceof String)
                    {
                        string(output, next.toString());
                    }
                }
                if (iterator.hasNext())
                {
                    output.append(',').append(' ');
                }
                else
                {
                    break;
                }
            }
            return output.append(']').toString();
        }
        else if (this instanceof JSONObject)
        {
            Iterator<Map.Entry<String, Object>> iterator = ((JSONObject)this).maps.entrySet().iterator();
            output.append('{');
            while (iterator.hasNext())
            {
                final Map.Entry entry = iterator.next();
                final Object name = entry.getKey();
                final Object value = entry.getValue();
                if (value == null)
                {
                    output
                            .append('\"')
                            .append(name)
                            .append('\"')
                            .append(':')
                            .append("null");
                }
                else
                {
                    if (value instanceof JSONObject)
                    {
                        output
                                .append('\"')
                                .append(name)
                                .append('\"')
                                .append(':')
                                .append(value.toString());
                    }
                    else if (value instanceof JSONArray)
                    {
                        output
                                .append('\"')
                                .append(name)
                                .append('\"')
                                .append(':')
                                .append(value.toString());
                    }
                    else if (value instanceof Integer || value instanceof Long || value instanceof Float || value instanceof Double || value instanceof Boolean)
                    {
                        output
                                .append('\"')
                                .append(name)
                                .append('\"')
                                .append(':')
                                .append(value);
                    }
                    else if (value instanceof String)
                    {
                        output
                                .append('\"')
                                .append(name)
                                .append('\"')
                                .append(':');
                        string(output, value.toString());
                    }
                }
                if (iterator.hasNext())
                {
                    output.append(',').append(' ');
                }
                else
                {
                    break;
                }
            }
            return output.append('}').toString();
        }
        return null;
    }
    private void string(StringBuilder output, String value)
    {
        output.append('"');
        for (int x = 0 ; x < value.length() ; x ++ )
        {
            char c = value.charAt(x);
            switch (c)
            {
                case '"':
                case '\\':
                    output.append('\\').append(c);
                    break;

                default:
                    output.append(c);
                    break;
            }
        }
        output.append('"');
    }
}
