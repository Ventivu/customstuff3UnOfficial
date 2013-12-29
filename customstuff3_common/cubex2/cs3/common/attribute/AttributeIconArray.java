package cubex2.cs3.common.attribute;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;

public class AttributeIconArray extends AttributeStringArray
{
    public Icon[] icons;

    public AttributeIconArray(AttributeCollection attributeCollection, String name, int size)
    {
        super(attributeCollection, name, "", size);
    }

    public void setIcons(IconRegister iconRegister)
    {
        icons = new Icon[value.length];
        for (int i = 0; i < icons.length; i++)
        {
            if (!value[i].equals(""))
            {
                icons[i] = iconRegister.registerIcon(value[i]);
            }
        }
    }

    @Override
    public boolean readValue(Object value)
    {
        boolean b = super.readValue(value);
        if (!b)
            return false;
        for (int i = 0; i < this.value.length; i++)
        {
            if (this.value[i].endsWith(".png"))
            {
                this.value[i] = this.value[i].substring(0, this.value[i].length() - 4);
            }
            if (this.value[i].equals(""))
            {
                // Do nothing
            }
            else if (!this.value[i].startsWith("/"))
            {
                this.value[i] = pack.getName() + ":" + this.value[i];
            }
            else
            {
                this.value[i] = this.value[i].substring(1);
            }
        }
        return true;
    }

}
