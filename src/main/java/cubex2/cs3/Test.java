package cubex2.cs3;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cubex2.cs3.asm.ICSMod;

public class Test implements ICSMod
{
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        CustomStuff3.onPreInitPack(this);
    }

    @Override
    public String getName()
    {
        return null;
    }

    @Override
    public String getId()
    {
        return null;
    }

    @Override
    public String getVersion()
    {
        return null;
    }

    @Override
    public boolean isIngamePack()
    {
        return true;
    }
}
