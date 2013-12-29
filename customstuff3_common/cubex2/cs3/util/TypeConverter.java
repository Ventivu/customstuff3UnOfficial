package cubex2.cs3.util;

import org.mozilla.javascript.ConsString;

public class TypeConverter
{
    private Object value;

    public TypeConverter(Object value)
    {
        this.value = value;
    }

    public boolean isNumber()
    {
        return value instanceof Number;
    }

    public boolean isString()
    {
        return value instanceof String;
    }

    public boolean isBoolean()
    {
        return value instanceof Boolean;
    }

    public boolean isIntegerArray()
    {
        return value instanceof int[];
    }

    public boolean isFloatArray()
    {
        return value instanceof float[];
    }

    public boolean isStringArray()
    {
        return value instanceof String[];
    }

    public boolean isBooleanArray()
    {
        return value instanceof boolean[];
    }

    public Number getNumberValue()
    {
        if (isNumber())
            return (Number) value;
        if (isString())
            return Float.parseFloat((String) value);
        return null;
    }

    public String getStringValue()
    {
        if (isString())
            return (String) value;
        if (isNumber())
            return ((Number) value).toString();
        if (isBoolean())
            return ((Boolean) value).toString();
        if (value instanceof ConsString)
            return value.toString();
        return null;
    }

    public Boolean getBooleanValue()
    {
        if (isBoolean())
            return (Boolean) value;
        if (isString())
            return Boolean.parseBoolean((String) value);
        return null;
    }

    public int[] getIntegerArrayValue()
    {
        if (isIntegerArray())
            return (int[]) value;
        if (isString())
        {
            String s = (String) value;
            s = s.replaceAll("[ \t]*", "");
            if (s.equals(""))
                return null;
            String[] s1 = s.split(",");
            int[] ints = new int[s1.length];
            for (int i = 0; i < s1.length; i++)
            {
                ints[i] = Integer.parseInt(s1[i]);
            }
            return ints;
        }
        return null;
    }

    public int[] getIntegerArrayValue(int altLength)
    {
        if (isIntegerArray())
            return (int[]) value;
        if (isNumber())
            return ArrayHelper.createIntArray(altLength, ((Number) value).intValue());
        if (isString())
            return ArrayHelper.createIntArray(altLength, Integer.parseInt((String) value));
        return null;
    }

    public float[] getFloatArrayValue()
    {
        if (isFloatArray())
            return (float[]) value;
        if (isString())
        {
            String s = (String) value;
            s = s.replaceAll("[ \t]*", "");
            if (s.equals(""))
                return null;
            String[] s1 = s.split(",");
            float[] f = new float[s1.length];
            for (int i = 0; i < s1.length; i++)
            {
                f[i] = Float.parseFloat(s1[i]);
            }
            return f;
        }
        return null;
    }

    public float[] getFloatArrayValue(int altLength)
    {
        if (isFloatArray())
            return (float[]) value;
        if (isNumber())
            return ArrayHelper.createArray(altLength, ((Number) value).floatValue());
        if (isString())
            return ArrayHelper.createArray(altLength, Float.parseFloat((String) value));
        return null;
    }

    public String[] getStringArrayValue()
    {
        if (isStringArray())
            return (String[]) value;
        if (isString())
        {
            String s = (String) value;
            s = s.replaceAll("[ \t]*", "");
            if (s.equals(""))
                return null;
            return s.split(",");

        }
        return null;
    }

    public String[] getStringArrayValue(int altLength)
    {
        if (isStringArray())
            return (String[]) value;
        if (isString())
            return ArrayHelper.createArray(String.class, (String) value, altLength);
        return null;
    }

    public boolean[] getBooleanArrayValue()
    {
        if (isBooleanArray())
            return (boolean[]) value;
        return null;
    }

    public boolean[] getBooleanArrayValue(int altLength)
    {
        if (isBooleanArray())
            return (boolean[]) value;
        if (isBoolean())
            return ArrayHelper.createArray(altLength, (Boolean) value);
        return null;
    }
}
