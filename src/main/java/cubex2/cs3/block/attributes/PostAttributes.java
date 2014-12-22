package cubex2.cs3.block.attributes;

import cubex2.cs3.common.BaseContentPack;

public class PostAttributes extends FacingAttributes
{
    public float thickness = 0.5f;

    public PostAttributes(BaseContentPack pack)
    {
        super(pack);
        opacity = 0;
        faceBySide = true;
        canFaceTop = true;
        canFaceBottom = true;
    }
}
