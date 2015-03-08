package cubex2.cs3.lib;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.minecraft.world.biome.BiomeGenBase;

import java.util.Map;
import java.util.Set;

public class Biomes
{
    private static Map<String, BiomeGenBase> biomeMap = Maps.newHashMap();

    public static BiomeGenBase getBiome(String name)
    {
        if (name == null)
            return null;

        BiomeGenBase biome = null;

        if (biomeMap.containsKey(name))
        {
            biome = biomeMap.get(name);
        }
        else
        {
            for (BiomeGenBase b : BiomeGenBase.getBiomeGenArray())
            {
                if (b != null && b.biomeName.equals(name))
                {
                    biomeMap.put(b.biomeName, b);
                    biome = b;
                    break;
                }
            }
        }

        return biome;
    }

    public static Set<String> getBiomeNames()
    {
        Set<String> res = Sets.newHashSet();
        for (BiomeGenBase b : BiomeGenBase.getBiomeGenArray())
        {
            if (b != null)
            {
                res.add(b.biomeName);
            }
        }
        return res;
    }
}
