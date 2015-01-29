package cubex2.cs3.util;

import net.minecraft.client.Minecraft;

public class ClientHelper
{
    public static void refreshResources(Minecraft mc)
    {
        mc.getSoundHandler().pauseSounds();
        mc.refreshResources();
        mc.getSoundHandler().resumeSounds();
    }
}
