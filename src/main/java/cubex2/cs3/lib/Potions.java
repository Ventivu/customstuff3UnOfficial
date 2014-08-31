package cubex2.cs3.lib;

import com.google.common.collect.Maps;
import net.minecraft.potion.Potion;

import java.util.Map;

public class Potions
{
    private static Map<String, Potion> potionMap = Maps.newHashMap();

    public static Potion getPotion(String name)
    {
        if (name == null)
            return null;

        Potion potion = null;

        if (!name.startsWith("potion."))
        {
            name = "potion.".concat(name);
        }

        if (potionMap.containsKey(name))
        {
            potion = potionMap.get(name);
        }
        else
        {
            for (Potion p : Potion.potionTypes)
            {
                if (p == null)
                {
                    continue;
                }
                if (p.getName().equals(name))
                {
                    potionMap.put(p.getName(), p);
                    potion = p;
                    break;
                }
            }
        }

        return potion;
    }

    public static String getPotionName(Potion potion)
    {
        return potion == null ? null : potion.getName().replaceFirst("potion\\.", "");
    }
}
