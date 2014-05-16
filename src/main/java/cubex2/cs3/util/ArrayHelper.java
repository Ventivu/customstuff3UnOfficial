package cubex2.cs3.util;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class ArrayHelper
{
    public static int[] concatInt(List<int[]> arrays)
    {
        int size = 0;
        for (int[] array : arrays)
        {
            size += array.length;
        }

        int[] ret = new int[size];
        int curIdx = 0;
        for (int[] array : arrays)
        {
            System.arraycopy(array, 0, ret, curIdx, array.length);
            curIdx += array.length;
        }

        return ret;
    }

    public static <T> T[] concat(List<T[]> arrays)
    {
        int size = 0;
        for (T[] array : arrays)
        {
            size += array.length;
        }

        T[] ret = (T[]) Array.newInstance(arrays.get(0).getClass().getComponentType(), size);
        int curIdx = 0;
        for (T[] array : arrays)
        {
            System.arraycopy(array, 0, ret, curIdx, array.length);
            curIdx += array.length;
        }

        return ret;
    }

    public static int[] createIntArray(int size, int value)
    {
        int[] temp = new int[size];
        Arrays.fill(temp, value);
        return temp;
    }

    public static boolean[] createArray(int size, boolean value)
    {
        boolean[] temp = new boolean[size];
        Arrays.fill(temp, value);
        return temp;
    }

    public static float[] createArray(int size, float value)
    {
        float[] temp = new float[size];
        Arrays.fill(temp, value);
        return temp;
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] createArray(Class<T> c, T value, int size)
    {
        T[] temp = (T[]) Array.newInstance(c, size);
        Arrays.fill(temp, value);
        return temp;
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] createArray(T value, int size)
    {
        T[] temp = (T[]) Array.newInstance(value.getClass(), size);
        Arrays.fill(temp, value);
        return temp;
    }

    public static <T> int indexOf(T value, T[] array)
    {
        for (int i = 0; i < array.length; i++)
        {
            if (array[i] == value)
            {
                return i;
            }
        }
        return -1;
    }
}
