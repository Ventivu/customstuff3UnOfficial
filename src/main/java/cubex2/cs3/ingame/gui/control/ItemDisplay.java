package cubex2.cs3.ingame.gui.control;

import com.google.common.collect.Lists;
import cpw.mods.fml.common.registry.GameData;
import cubex2.cs3.ingame.gui.GuiBase;
import cubex2.cs3.lib.Textures;
import cubex2.cs3.util.GuiHelper;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.oredict.OreDictionary;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import java.util.List;

public class ItemDisplay extends Control
{
    private ItemStack originStack;
    private ItemStack currentRenderStack;
    private List<ItemStack> renderStacks;

    private boolean drawSlotBackground = false;
    private boolean showIdAndDamageValue = true;
    private int tickCounter = 1;
    private int currentIndex = 0;

    private IToolTipModifier toolTipModifier;

    public ItemDisplay(int x, int y, Control parent)
    {
        super(x, y, 16, 16, parent);
    }

    public ItemDisplay setDrawSlotBackground()
    {
        drawSlotBackground = true;
        return this;
    }

    public void setToolTipModifier(IToolTipModifier modifier)
    {
        toolTipModifier = modifier;
    }

    public void setStackSize(int stackSize)
    {
        if (originStack != null)
        {
            originStack.stackSize = stackSize;
            if (renderStacks != null)
            {
                for (ItemStack renderStack : renderStacks)
                {
                    renderStack.stackSize = originStack.stackSize;
                }
            }
            else
            {
                currentRenderStack.stackSize = originStack.stackSize;
            }
        }
    }

    public ItemStack getItemStack()
    {
        return originStack;
    }

    public void setItemStack(ItemStack stack)
    {
        if (stack == null)
        {
            originStack = null;
            currentRenderStack = null;
            renderStacks = null;
            return;
        }
        originStack = stack;
        currentRenderStack = stack;
        if (stack.getItem() != null)
        {
            if (stack.getHasSubtypes() && stack.getItemDamage() == OreDictionary.WILDCARD_VALUE)
            {
                renderStacks = Lists.newArrayList();
                stack.getItem().getSubItems(stack.getItem(), null, renderStacks);
                if (renderStacks.size() == 0)
                {
                    renderStacks = null;
                } else
                {
                    for (ItemStack renderStack : renderStacks)
                    {
                        renderStack.stackSize = originStack.stackSize;
                    }
                    currentRenderStack = renderStacks.get(0);
                }
            } else
            {
                renderStacks = null;
            }

            if (currentRenderStack.getItemDamage() == OreDictionary.WILDCARD_VALUE)
            {
                currentRenderStack.setItemDamage(0);
            }
        }
    }

    @Override
    public void update()
    {
        if (renderStacks != null && tickCounter++ % 20 == 0)
        {
            currentIndex = (currentIndex + 1) % renderStacks.size();
            currentRenderStack = renderStacks.get(currentIndex);
        }
    }

    @Override
    public void draw(int mouseX, int mouseY)
    {
        if (drawSlotBackground)
        {
            GL11.glColor3f(1f, 1f, 1f);
            mc.renderEngine.bindTexture(Textures.CONTROLS);
            drawTexturedModalRect(rect.getX() - 1, rect.getY() - 1, 200, 0, 18, 18);
        }

        if (currentRenderStack == null)
            return;

        GL11.glPushMatrix();
        RenderHelper.enableGUIStandardItemLighting();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glEnable(GL11.GL_COLOR_MATERIAL);
        GL11.glEnable(GL11.GL_LIGHTING);
        GuiBase.itemRenderer.zLevel = 100.0F;
        GuiBase.itemRenderer.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.renderEngine, currentRenderStack, rect.getX(), rect.getY());
        GuiBase.itemRenderer.renderItemOverlayIntoGUI(mc.fontRenderer, mc.renderEngine, currentRenderStack, rect.getX(), rect.getY());
        GuiBase.itemRenderer.zLevel = 0.0F;
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();


        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        RenderHelper.enableStandardItemLighting();
        RenderHelper.disableStandardItemLighting();
    }

    @Override
    public void drawForeground(int mouseX, int mouseY)
    {
        if (currentRenderStack == null)
            return;

        if (isMouseOverControl(mouseX, mouseY))
        {
            List list = currentRenderStack.getTooltip(mc.thePlayer, mc.gameSettings.advancedItemTooltips);

            for (int k = 0; k < list.size(); ++k)
            {
                if (k == 0)
                {
                    list.set(k, currentRenderStack.getRarity().rarityColor.toString() + list.get(k));
                } else
                {
                    list.set(k, EnumChatFormatting.GRAY + (String) list.get(k));
                }
            }

            if (showIdAndDamageValue)
            {
                list.add(EnumChatFormatting.GRAY + "Name: " + GameData.itemRegistry.getNameForObject(originStack.getItem()));
                list.add(EnumChatFormatting.GRAY + "DV: " + originStack.getItemDamage());
            }

            if (toolTipModifier != null)
            {
                toolTipModifier.modifyToolTip(list, originStack);
            }

            FontRenderer font = currentRenderStack.getItem().getFontRenderer(currentRenderStack);
            GuiHelper.drawHoveringText(list, mouseX, mouseY, font == null ? mc.fontRenderer : font);
        }
    }
}
