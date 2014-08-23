package cubex2.cs3.item.attributes;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.attribute.Attribute;
import cubex2.cs3.common.attribute.AttributeContainer;
import cubex2.cs3.ingame.gui.item.*;
import cubex2.cs3.util.IconWrapper;
import cubex2.cs3.util.ScriptWrapper;
import net.minecraft.creativetab.CreativeTabs;
import org.mozilla.javascript.Script;

public class ItemAttributes extends AttributeContainer
{
    @Attribute(windowClass = WindowEditDisplayName.class)
    public String displayName = "Unnamed";

    @Attribute(windowClass = WindowEditInformation.class)
    public String information = null;

    @Attribute(windowClass = WindowEditIcon.class)
    public IconWrapper icon = new IconWrapper("");

    @Attribute(windowClass = WindowEditMaxStack.class)
    public int maxStack = 64;

    public int maxUsingDuration = 0;

    @Attribute(windowClass = WindowEditFull3D.class)
    public boolean full3d = false;

    @Attribute(windowClass = WindowEditHasEffect.class)
    public boolean hasEffect = false;

    @Attribute(windowClass = WindowEditCreativeTab.class)
    public CreativeTabs creativeTab = CreativeTabs.tabAllSearch;

    @Attribute(windowClass = WindowEditScript.class)
    public ScriptWrapper onRightClick = null;
    @Attribute(windowClass = WindowEditScript.class)
    public ScriptWrapper onUse = null;
    @Attribute(windowClass = WindowEditScript.class)
    public ScriptWrapper onUseFirst = null;
    @Attribute(windowClass = WindowEditScript.class)
    public ScriptWrapper onUseOnEntity = null;
    @Attribute(windowClass = WindowEditScript.class)
    public ScriptWrapper onUseOnPlayer = null;
    @Attribute(windowClass = WindowEditScript.class)
    public ScriptWrapper onBlockDestroyed = null;
    @Attribute(windowClass = WindowEditScript.class)
    public ScriptWrapper onHitEntity = null;
    @Attribute(windowClass = WindowEditScript.class)
    public ScriptWrapper onLeftClickLiving = null;
    @Attribute(windowClass = WindowEditScript.class)
    public ScriptWrapper onLeftClickPlayer = null;
    @Attribute(windowClass = WindowEditScript.class)
    public ScriptWrapper onUpdate = null;
    @Attribute(windowClass = WindowEditScript.class)
    public ScriptWrapper onCreated = null;
    @Attribute(windowClass = WindowEditScript.class)
    public ScriptWrapper onUsing = null;
    @Attribute(windowClass = WindowEditScript.class)
    public ScriptWrapper onStoppedUsing = null;
    @Attribute(windowClass = WindowEditScript.class)
    public ScriptWrapper onDroppedByPlayer = null;
    @Attribute(windowClass = WindowEditScript.class)
    public ScriptWrapper onBlockStartBreak = null;
    @Attribute(windowClass = WindowEditScript.class)
    public ScriptWrapper onEaten = null;

    public ItemAttributes(BaseContentPack pack)
    {
        super(pack);
    }
}