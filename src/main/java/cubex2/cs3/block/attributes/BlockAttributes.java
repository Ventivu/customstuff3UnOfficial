package cubex2.cs3.block.attributes;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.attribute.Attribute;
import cubex2.cs3.common.attribute.AttributeContainer;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.block.*;
import cubex2.cs3.ingame.gui.common.WindowEditInteger;
import cubex2.cs3.ingame.gui.common.WindowEditScript;
import cubex2.cs3.util.ScriptWrapper;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class BlockAttributes extends AttributeContainer
{
    @Attribute(windowClass = WindowEditDisplayName.class)
    public String displayName = "Unnamed";

    @Attribute(windowClass = WindowEditCreativeTab.class)
    public CreativeTabs creativeTab = CreativeTabs.tabAllSearch;

    @Attribute(windowClass = WindowEditMaxStack.class)
    public int maxStack = 64;

    @Attribute(windowClass = WindowEditOpacity.class)
    public int opacity = 255;

    @Attribute(windowClass = WindowEditInteger.class)
    public int light = 10;

    @Attribute(windowClass = WindowEditInteger.class)
    public int flammability = 10;

    @Attribute(windowClass = WindowEditInteger.class)
    public int fireSpreadSpeed = 10;

    @Attribute(windowClass = WindowEditInteger.class)
    public int tickrate = 10;

    @Attribute(windowClass = WindowEditContainerItem.class)
    public ItemStack containerItem = null;

    @Attribute(windowClass = Window.class, hasOwnWindow = false)
    public boolean leaveContainerItem = false;

    @Attribute(windowClass = WindowEditMaterial.class)
    public Material material = Material.ground;

    @Attribute(windowClass = WindowEditScript.class)
    public ScriptWrapper onUpdate = null;
    @Attribute(windowClass = WindowEditScript.class)
    public ScriptWrapper onDestroyedByPlayer = null;
    @Attribute(windowClass = WindowEditScript.class)
    public ScriptWrapper onNeighborChange = null;
    @Attribute(windowClass = WindowEditScript.class)
    public ScriptWrapper onAdded = null;
    @Attribute(windowClass = WindowEditScript.class)
    public ScriptWrapper onBreak = null;
    @Attribute(windowClass = WindowEditScript.class)
    public ScriptWrapper onActivated = null;
    @Attribute(windowClass = WindowEditScript.class)
    public ScriptWrapper onWalking = null;
    @Attribute(windowClass = WindowEditScript.class)
    public ScriptWrapper onWalkingLiving = null;
    @Attribute(windowClass = WindowEditScript.class)
    public ScriptWrapper onWalkingPlayer = null;
    @Attribute(windowClass = WindowEditScript.class)
    public ScriptWrapper onPlaced = null;
    @Attribute(windowClass = WindowEditScript.class)
    public ScriptWrapper onClicked = null;
    @Attribute(windowClass = WindowEditScript.class)
    public ScriptWrapper onCollided = null;
    @Attribute(windowClass = WindowEditScript.class)
    public ScriptWrapper onCollidedWithPlayer = null;
    @Attribute(windowClass = WindowEditScript.class)
    public ScriptWrapper onCollidedWithLiving = null;
    @Attribute(windowClass = WindowEditScript.class)
    public ScriptWrapper onPlacedBy = null;
    @Attribute(windowClass = WindowEditScript.class)
    public ScriptWrapper onPlacedByPlayer = null;
    @Attribute(windowClass = WindowEditScript.class)
    public ScriptWrapper onFallenUpon = null;
    @Attribute(windowClass = WindowEditScript.class)
    public ScriptWrapper onBonemeal = null;
    @Attribute(windowClass = WindowEditScript.class)
    public ScriptWrapper onRedstoneSignal = null;

    public BlockAttributes(BaseContentPack pack)
    {
        super(pack);
    }
}
