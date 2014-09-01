package cubex2.cs3.handler.event;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cubex2.cs3.block.BlockCS;
import net.minecraftforge.event.entity.player.BonemealEvent;

public class BonemealHandler
{
    private static BonemealHandler instance;

    public static BonemealHandler instance()
    {
        if (instance == null)
        {
            instance = new BonemealHandler();
        }
        return instance;
    }

    private BonemealHandler()
    {
    }

    @SubscribeEvent
    public void onUseBonemeal(BonemealEvent event)
    {
        if (event.block instanceof BlockCS)
        {
            BlockCS block = (BlockCS) event.block;
            if (block.onBonemeal(event.world, event.x, event.y, event.z, event.entityPlayer))
            {
                event.setResult(Event.Result.ALLOW);
            }
        }
    }
}
