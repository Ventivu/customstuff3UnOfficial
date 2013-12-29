package cubex2.cs3.common.attribute;

import net.minecraft.util.ResourceLocation;

public class AttributeTexture extends AttributeString
{
    private String subDir;
    public ResourceLocation location;

    public AttributeTexture(AttributeCollection attributeCollection, String name, String subDir, String defaultValue)
    {
        super(attributeCollection, name, defaultValue);
        this.subDir = subDir;
    }

    @Override
    public boolean readValue(Object jsValue)
    {
        boolean b = super.readValue(jsValue);
        if (!b)
            return false;

        String modName = "minecraft";

        if (!value.startsWith("/"))
        {
            modName = pack.getName();
        }
        else
        {
            value = value.substring(1);
        }

        location = new ResourceLocation(modName.toLowerCase(), "textures/" + subDir + "/" + value);
        return true;
    }

}
