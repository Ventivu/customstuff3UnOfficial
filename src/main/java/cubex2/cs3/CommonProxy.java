package cubex2.cs3;

import cubex2.cs3.handler.event.BonemealHandler;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy
{
    public void registerKeyBindings()
    {
        // do nothing
    }

    public void registerEventHandlers()
    {
        MinecraftForge.EVENT_BUS.register(BonemealHandler.instance());
    }

    public void initRendering()
    {
        // do nothing
    }
}
