package cubex2.cs3;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;

public class ClientProxy extends CommonProxy
{
    @Override
    public void registerKeyBindings()
    {
        ClientRegistry.registerKeyBinding(KeyBindingHandler.openGuiKey);
        FMLCommonHandler.instance().bus().register(KeyBindingHandler.INSTANCE);
    }

}
