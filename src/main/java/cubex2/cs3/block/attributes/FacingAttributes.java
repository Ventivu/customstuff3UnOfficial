package cubex2.cs3.block.attributes;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.util.IconWrapper;

public class FacingAttributes extends BlockAttributes
{
    public IconWrapper textureFront = new IconWrapper("");
    public IconWrapper textureBack = new IconWrapper("");
    public IconWrapper textureSides = new IconWrapper("");

    public boolean canFaceTop = false;
    public boolean canFaceBottom = false;
    public boolean canFaceSides = false;

    public boolean faceBySide = false;

    public boolean rotateSideTextures = false;

    public FacingAttributes(BaseContentPack pack)
    {
        super(pack);
    }
}
