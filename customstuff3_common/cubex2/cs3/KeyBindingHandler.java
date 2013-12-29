package cubex2.cs3;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.KeyBindingRegistry.KeyHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cubex2.cs3.ingame.gui.GuiBase;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

import java.util.EnumSet;

@SideOnly(Side.CLIENT)
public class KeyBindingHandler extends KeyHandler
{
    public static KeyBinding cs3Ingame = new KeyBinding("CS3 Ingame", Keyboard.KEY_C);

    public KeyBindingHandler()
    {
        super(new KeyBinding[] { cs3Ingame }, new boolean[] { false });
    }

    @Override
    public String getLabel()
    {
        return "CS2 Ingame";
    }

    @Override
    public void keyDown(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd, boolean isRepeat)
    {
        if (types.contains(TickType.CLIENT) && FMLClientHandler.instance().getClient().inGameHasFocus)
        {
            Keyboard.enableRepeatEvents(true);
            FMLClientHandler.instance().showGuiScreen(GuiBase.instance);
        }

    }

    @Override
    public void keyUp(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd)
    {

    }

    @Override
    public EnumSet<TickType> ticks()
    {
        return EnumSet.of(TickType.CLIENT);
    }

}
