package cubex2.cs3;

import cpw.mods.fml.common.registry.EntityRegistry;
import cubex2.cs3.entity.EntityCSGravityBlock;
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

    public void registerEntities()
    {
        EntityRegistry.registerModEntity(EntityCSGravityBlock.class, "cs_gravityblock", 1, CustomStuff3.instance, 80, 1, true);
    }

    public void initRendering()
    {
        // do nothing
    }
}
