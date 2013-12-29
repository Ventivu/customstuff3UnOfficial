package cubex2.cs3.ingame.gui.control;

import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class PictureBox extends Control
{
    private ResourceLocation texture;
    private int u;
    private int v;

    public PictureBox(ResourceLocation texture, int u, int v, int x, int y, int width, int height, Control parent)
    {
        super(x, y, width, height, parent);
        this.texture = texture;
        this.u = u;
        this.v = v;
    }

    public void setTexture(ResourceLocation texture)
    {
        this.texture = texture;
    }

    public void setUV(int u, int v)
    {
        this.u = u;
        this.v = v;
    }

    @Override
    public void draw(int mouseX, int mouseY)
    {
        mc.renderEngine.bindTexture(texture);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.drawTexturedModalRect(rect.getX(), rect.getY(), u, v, rect.getWidth(), rect.getHeight());
    }
}
