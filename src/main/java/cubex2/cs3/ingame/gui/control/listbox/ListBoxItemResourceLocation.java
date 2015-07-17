package cubex2.cs3.ingame.gui.control.listbox;

import cubex2.cs3.ingame.gui.control.Anchor;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.util.ClientHelper;
import cubex2.cs3.util.GuiHelper;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class ListBoxItemResourceLocation extends ListBoxItem<ResourceLocation>
{
    public ListBoxItemResourceLocation(ResourceLocation value, int idx, int width, int height, Anchor anchor, int offsetX, int offsetY, Control parent)
    {
        super(value, idx, width, height, anchor, offsetX, offsetY, parent);
    }

    @Override
    public void draw(int mouseX, int mouseY, float renderTick)
    {
        super.draw(mouseX, mouseY, renderTick);

        GL11.glEnable(GL11.GL_BLEND);
        OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        GL11.glEnable(GL11.GL_ALPHA_TEST);

        mc.renderEngine.bindTexture(value);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glScalef(1 / 16.0F, 1 / 16.0F, 1.0F);
        drawTexturedModalRect((getX() + 3) * 16, (getY() + 3) * 16, 0, 0, 256, 256);
        GL11.glScalef(16.0F, 16.0F, 1.0F);

        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
    }

    @Override
    public void drawForeground(int mouseX, int mouseY)
    {
        super.drawForeground(mouseX, mouseY);

        if (isMouseOverControl(mouseX, mouseY))
        {
            String s = ClientHelper.resourceToIconString(value);

            GuiHelper.drawToolTip(new String[]{s}, mouseX, mouseY, mc.fontRenderer);
        }
    }
}
