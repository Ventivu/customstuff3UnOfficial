package cubex2.cs3.block.attributes;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.ingame.gui.block.WindowEditTexturesPost;

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
        textureWindow = WindowEditTexturesPost.class;
    }
}
