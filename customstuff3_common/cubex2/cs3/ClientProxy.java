package cubex2.cs3;

import cpw.mods.fml.client.registry.KeyBindingRegistry;

public class ClientProxy extends CommonProxy
{
    @Override
    public void registerKeyBindings()
    {
        KeyBindingRegistry.registerKeyBinding(new KeyBindingHandler());
    }

}
