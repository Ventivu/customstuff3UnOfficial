package cubex2.cs3.tests;

import cubex2.cs3.util.ItemStackHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ItemStackHelperTest
{
    @Test
    public void damageItemTest()
    {
        Item item = new Item();
        item.setMaxDamage(1);

        ItemStack stack = new ItemStack(item, 2, 0);

        ItemStackHelper.damageItem(stack, 1);
        assertEquals(1, stack.getItemDamage());
        assertEquals(2, stack.stackSize);

        ItemStackHelper.damageItem(stack, 1);
        assertEquals(0, stack.getItemDamage());
        assertEquals(1, stack.stackSize);

        ItemStackHelper.damageItem(stack, 2);
        assertEquals(0, stack.getItemDamage());
        assertEquals(0, stack.stackSize);
    }

    @Test
    public void hasEnchantmentTest()
    {
        Item item = new Item();
        ItemStack stack = new ItemStack(item);

        assertEquals(false, ItemStackHelper.hasEnchantment(stack, 1, 1));

        stack.addEnchantment(Enchantment.aquaAffinity, 1);
        assertEquals(false, ItemStackHelper.hasEnchantment(stack, Enchantment.aquaAffinity.effectId, 0));
        assertEquals(true, ItemStackHelper.hasEnchantment(stack, Enchantment.aquaAffinity.effectId, 1));
        assertEquals(false, ItemStackHelper.hasEnchantment(stack, Enchantment.baneOfArthropods.effectId, 1));
    }

    @Test
    public void clearEnchantmentsTest()
    {
        Item item = new Item();
        ItemStack stack = new ItemStack(item);
        stack.addEnchantment(Enchantment.aquaAffinity, 1);
        stack.addEnchantment(Enchantment.baneOfArthropods, 1);

        ItemStackHelper.clearEnchantments(stack);
        assertEquals(false, stack.getTagCompound().hasKey("ench"));
    }

    @Test
    public void addEnchantmentTest()
    {
        Item item = new Item();
        ItemStack stack = new ItemStack(item);

        ItemStackHelper.addEnchantment(stack, 1, 1);
        assertEquals(1, EnchantmentHelper.getEnchantmentLevel(1, stack));
    }

    @Test
    public void removeEnchantmentTest()
    {
        Item item = new Item();
        ItemStack stack = new ItemStack(item);

        stack.addEnchantment(Enchantment.aquaAffinity, 1);
        stack.addEnchantment(Enchantment.baneOfArthropods, 1);

        ItemStackHelper.removeEnchantment(stack, Enchantment.aquaAffinity.effectId);
        assertEquals(0, EnchantmentHelper.getEnchantmentLevel(Enchantment.aquaAffinity.effectId, stack));
        assertEquals(1, EnchantmentHelper.getEnchantmentLevel(Enchantment.baneOfArthropods.effectId, stack));
    }

    @Test
    public void itemStackEqualTest()
    {
        Item item = new Item();
        Item item1 = new Item();
        ItemStack stack1 = new ItemStack((Item) null);
        ItemStack stack2 = new ItemStack(item);
        ItemStack stack3 = new ItemStack(item1);
        ItemStack stack4 = new ItemStack(item, 1, 0);
        ItemStack stack5 = new ItemStack(item, 1, 1);
        ItemStack stack6 = new ItemStack(item, 1, OreDictionary.WILDCARD_VALUE);
        ItemStack stack7 = new ItemStack(item, 33, 1);

        assertEquals(true, ItemStackHelper.itemStackEqual(null, null));
        assertEquals(false, ItemStackHelper.itemStackEqual(stack1, stack2));
        assertEquals(true, ItemStackHelper.itemStackEqual(stack1, stack1));
        assertEquals(true, ItemStackHelper.itemStackEqual(stack2, stack2));
        assertEquals(false, ItemStackHelper.itemStackEqual(stack2, stack3));
        assertEquals(false, ItemStackHelper.itemStackEqual(stack4, stack5));
        assertEquals(true, ItemStackHelper.itemStackEqual(stack4, stack6));
        assertEquals(true, ItemStackHelper.itemStackEqual(stack6, stack5));
        assertEquals(true, ItemStackHelper.itemStackEqual(stack5, stack7));
    }
}
