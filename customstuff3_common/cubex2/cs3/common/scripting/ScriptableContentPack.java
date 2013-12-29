package cubex2.cs3.common.scripting;

import cubex2.cs3.common.Alias;
import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.Fuel;
import net.minecraft.item.Item;
import net.minecraftforge.oredict.OreDictionary;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class ScriptableContentPack
{
    private BaseContentPack pack;
    private Context context;
    private Scriptable scope;

    public ScriptableContentPack(BaseContentPack pack, Context cx, Scriptable scope)
    {
        this.pack = pack;
        context = cx;
        this.scope = scope;
    }

    /**
     * Adds an alias for an item.
     *
     * @param item
     *         The item in the format "[id] [damage value]". The damage value is optional and defaults to 32767.
     * @param aliasName
     *         The name for the alias
     */
    public void addAlias(String item, String aliasName)
    {
        String[] split = item.split(" ");
        int id = Integer.parseInt(split[0]);
        int damageValue = split.length > 1 ? Integer.parseInt(split[1]) : OreDictionary.WILDCARD_VALUE;
        Alias alias = new Alias(Item.itemsList[id], damageValue, aliasName, pack);
        alias.apply();
    }

    /**
     * Adds a fuel for the vanilla furnace.
     *
     * @param alias
     *         The alias.
     * @param duration
     *         The duration in ticks.
     */
    public void addFuel(String alias, int duration)
    {
        Fuel fuel = new Fuel(pack.aliasManager.getAlias(alias), duration, pack);
        fuel.apply();
    }
}

