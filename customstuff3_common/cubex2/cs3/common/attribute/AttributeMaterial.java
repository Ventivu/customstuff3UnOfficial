package cubex2.cs3.common.attribute;

import cubex2.cs3.lib.Materials;
import net.minecraft.block.material.Material;

public class AttributeMaterial extends AttributeStringToOther<Material>
{
    public AttributeMaterial(AttributeCollection attributeCollection, String name, Material defaultValue)
    {
        super(attributeCollection, name, defaultValue);
    }

    @Override
    public Material getValue(String s)
    {
        return Materials.getMaterial(s);
    }

    @Override
    public String getString(Material value)
    {
        return Materials.getMaterialName(value);
    }

}
