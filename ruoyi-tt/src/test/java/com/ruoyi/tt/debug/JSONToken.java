package com.ruoyi.tt.debug;

import java.util.regex.Pattern;

public class JSONToken
{
    private int pos;
    private String json;
    public JSONToken(String json)
    {
        this.json = json;
    }

    public Object nextValue()
    {
        int c = nextClean();
        switch (c)
        {
            case -1:
                break;
            case '[':
                return readArray();
            case '{':
                return readObject();
            case '\\':
                pos --;
                return readString();
            case '\'':
            case '"':
                return readString(c);
            default:
                pos --;
                return readCharacter();
        }
        return null;
    }
    private int nextClean()
    {
        while (pos < json.length())
        {
            char c = json.charAt(pos ++);
            switch (c)
            {
                case ' ':
                case '\r':
                case '\n':
                case '\t':
                    continue;
                default:
                    return c;
            }
        }
        return -1;
    }

    private JSONArray readArray()
    {
        JSONArray result = new JSONArray();
        while (true)
        {
            switch (nextClean())
            {
                case -1:
                    return result.length() == 0 ? null : result;
                case ']':
                    return result;
                case ',':
                    continue;
                default:
                    pos --;
            }
            result.put(nextValue());
        }
    }
    private JSONObject readObject()
    {
        JSONObject result = new JSONObject();
        while (true)
        {
            switch (nextClean())
            {
                case -1:
                    return result.length() == 0 ? null : result;
                case ']':
                case '}':
                    return result;
                case ',':
                    continue;
                default:
                    pos --;
            }
            Object name = nextValue();
            switch (nextClean())
            {
                case ':':
                    break;
                case -1:
                    return result.put(name.toString(), null);
            }
            result.put(name.toString(), nextValue());
        }
    }
    private String readString()
    {
        int quote = 0;
        int count = 0;
        int start = pos;
        while (pos < json.length())
        {
            char c = json.charAt(pos ++);
            if ((c == '"' || c == '\'') && count == 0)
            {
                quote = c;
                count = (pos - 1) - start;
            }
            else if (c == '\\' && count > 0)
            {
                if ((pos - 1) + count < json.length())
                {
                    if (json.charAt((pos - 1) + count) == quote)
                    {
                        return json.substring(start + count + 1, (pos += count) - count - 1);
                    }
                    else
                    {
                        while (pos < json.length())
                        {
                            if (json.charAt(pos ++) != '\\')
                                break;
                        }
                    }
                }
            }
        }
        return json.substring(pos);
    }
    private String readString(int quote)
    {
        int start = pos;
        StringBuilder builder = new StringBuilder();
        while (pos < json.length())
        {
            char c = json.charAt(pos ++);
            if (c == quote)
            {
                return builder.append(json, start, pos -1).toString();
            }
            else if (c == '\\')
            {
                builder.append(json, start, pos -1);
                builder.append(readIdentifier());
                start = pos;
            }
        }

        if (pos == json.length())
            return builder.append(json, start, pos).toString();
        else
            return null;
    }
    private String readString(String excluded)
    {
        int start = pos;
        for (; pos < json.length() ; pos ++ )
        {
            if (excluded.indexOf(json.charAt(pos)) != -1)
            {
                return json.substring(start, pos);
            }
        }
        return json.substring(start);
    }
    private char readIdentifier()
    {
        char c = json.charAt(pos ++);
        switch (c)
        {
            case 'r':
                return '\r';
            case 'n':
                return '\n';
            case 'b':
                return '\b';
            case 't':
                return '\t';
            case 'f':
                return '\f';
            case 'u':
                if (pos + 4 < json.length())
                {
                    return (char) Integer.parseInt(json.substring(pos, pos += 4), 16);
                }
        }
        return c;
    }

    private Object readCharacter()
    {
        String string = readString("{}[]/\\:,=;# \r\n\t\f");
        if (string.length() > 0)
        {
            switch (string)
            {
                case "null":
                    return null;
                case "true":
                    return true;
                case "false":
                    return false;
                default:
                    if (string.contains(".") && isNumber(string.replace(".", "")))
                    {
                        if (string.split("\\.").length == 2)
                            return Float.parseFloat(string);
                        else if (string.split("\\.").length > 2)
                            return Double.parseDouble(string);
                    }
                    else
                    {
                        if (string.toLowerCase().startsWith("0x") && isNumber(string.substring(2)))
                        {
                            long longValue = Long.parseLong(string.substring(2), 16);
                            if (longValue < Integer.MAX_VALUE)
                                return (int) longValue;
                            else
                                return longValue;
                        }
                        else if (isNumber(string))
                        {
                            if (string.startsWith("0"))
                                return 0;
                            else
                            {
                                long longValue = Long.parseLong(string);
                                if (longValue < Integer.MAX_VALUE)
                                    return (int) longValue;
                                else
                                    return longValue;
                            }
                        }
                    }
            }
        }
        return string;
    }

    private boolean isNumber(String value)
    {
        return Pattern.compile("^[-\\+]?[\\d]*$").matcher(value).matches();
    }
}