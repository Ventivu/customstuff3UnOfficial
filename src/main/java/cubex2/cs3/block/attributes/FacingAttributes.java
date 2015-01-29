package cubex2.cs3.block.attributes;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.attribute.Attribute;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.block.WindowEditTexturesFacing;
import cubex2.cs3.util.IconWrapper;

public class FacingAttributes extends BlockAttributes
{
    @Attribute(windowClass = Window.class, hasOwnWindow = false)
    public IconWrapper textureFront = new IconWrapper("");
    @Attribute(windowClass = Window.class, hasOwnWindow = false)
    public IconWrapper textureBack = new IconWrapper("");
    @Attribute(windowClass = Window.class, hasOwnWindow = false)
    public IconWrapper textureSides = new IconWrapper("");

    public boolean canFaceTop = false;
    public boolean canFaceBottom = false;
    public boolean canFaceSides = false;

    public boolean faceBySide = false;

    public boolean rotateSideTextures = false;

    public FacingAttributes(BaseContentPack pack)
    {
        super(pack);
        textureWindow = WindowEditTexturesFacing.class;
    }

    @Override
    public IconWrapper getTexture(String name)
    {
        if (name.equals("front"))
            return textureFront;
        if (name.equals("back"))
            return textureBack;
        if (name.equals("sides"))
            return textureSides;
        return super.getTexture(name);
    }
}
