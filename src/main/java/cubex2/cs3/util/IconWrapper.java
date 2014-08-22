package cubex2.cs3.util;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class IconWrapper
{
    public IIcon icon;
    public String iconString;

    public IconWrapper(String iconString)
    {
        this.iconString = iconString;
    }

    public void setIcon(IIconRegister iconRegister)
    {
        icon = iconString != null ? iconRegister.registerIcon(iconString) : null;
    }

    @Override
    public String toString()
    {
        return "icon(" + iconString + ")";
    }
}
