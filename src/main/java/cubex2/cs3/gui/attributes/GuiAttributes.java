package cubex2.cs3.gui.attributes;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.attribute.Attribute;
import cubex2.cs3.common.attribute.AttributeContainer;
import cubex2.cs3.gui.data.GuiData;
import cubex2.cs3.ingame.gui.common.WindowEditInteger;
import cubex2.cs3.ingame.gui.gui.WindowEditUserGui;

public class GuiAttributes extends AttributeContainer
{
    @Attribute(windowClass = WindowEditInteger.class)
    public int width = 150;
    @Attribute(windowClass = WindowEditInteger.class)
    public int height = 150;

    @Attribute(windowClass = WindowEditUserGui.class)
    public GuiData guiData = new GuiData();

    public GuiAttributes(BaseContentPack pack)
    {
        super(pack);
    }
}
