package cubex2.cs3.common.scripting;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.Fuel;
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
     * Adds a fuel for the vanilla furnace.
     *
     * @param alias
     *         The stack.
     * @param duration
     *         The duration in ticks.
     */
    public void addFuel(String alias, int duration)
    {
        //Fuel fuel = new Fuel(pack.aliasRegistry.getAlias(stack), duration, pack);
        //fuel.apply();
    }
}

