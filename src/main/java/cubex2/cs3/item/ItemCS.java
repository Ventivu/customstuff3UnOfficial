package cubex2.cs3.item;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.WrappedItem;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemCS extends Item
{
    protected BaseContentPack pack;
    private WrappedItem wrappedItem;

    public ItemCS(WrappedItem item)
    {
        super();
        this.pack = item.getPack();
        this.wrappedItem = item;
    }

    @Override
    public void registerIcons(IIconRegister p_94581_1_)
    {
        super.registerIcons(p_94581_1_);
    }

    @Override
    public int getItemStackLimit(ItemStack stack)
    {
        return wrappedItem.getItemStackLimit(stack);
    }

    @Override
    public boolean isFull3D()
    {
        return wrappedItem.isFull3D();
    }

    @Override
    public boolean hasEffect(ItemStack stack)
    {
        return wrappedItem.hasEffect(stack);
    }

    @Override
    public CreativeTabs getCreativeTab()
    {
        return wrappedItem.getCreativeTab();
    }
}
