package cubex2.cs3.lib;

import com.google.common.collect.Maps;
import cubex2.cs3.util.ClientHelper;

import java.util.Map;

public class Docs
{
    private static final Map<String, String> map = Maps.newHashMap();

    public static String get(String file)
    {
        if (!map.containsKey(file))
            map.put(file, ClientHelper.loadDocFile(file));

        return map.get(file);
    }
}
