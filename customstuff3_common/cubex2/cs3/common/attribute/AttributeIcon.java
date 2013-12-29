package cubex2.cs3.common.attribute;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;

public class AttributeIcon extends AttributeString
{
    public Icon icon;

    public AttributeIcon(AttributeCollection attributeCollection, String name)
    {
        super(attributeCollection, name, null);
    }

    public void setIcon(IconRegister iconRegister)
    {
        icon = iconRegister.registerIcon(value);
    }

    @Override
    public boolean readValue(Object value)
    {
        boolean b = super.readValue(value);
        if (!b)
            return false;
        if (this.value.endsWith(".png"))
        {
            this.value = this.value.substring(0, this.value.length() - 4);
        }
        if (!this.value.startsWith("/"))
        {
            this.value = pack.getName() + ":" + this.value;
        }
        else
        {
            this.value = this.value.substring(1);
        }
        return true;
    }

}
