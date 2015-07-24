package cubex2.cs3;

import cubex2.cs3.handler.event.BonemealHandler;
import cubex2.cs3.handler.event.FillBucketHandler;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy
{
    public void registerKeyBindings()
    {
        // do nothing
    }

    public void registerEventHandlers()
    {
        MinecraftForge.EVENT_BUS.register(new BonemealHandler());
        MinecraftForge.EVENT_BUS.register(new FillBucketHandler());
    }

    public void initRendering()
    {
        // do nothing
    }
}
