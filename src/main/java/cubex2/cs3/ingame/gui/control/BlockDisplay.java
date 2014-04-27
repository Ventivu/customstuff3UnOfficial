package cubex2.cs3.ingame.gui.control;

import cubex2.cs3.lib.Color;
import cubex2.cs3.util.GuiHelper;
import cubex2.cs3.util.ScissorHelper;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class BlockDisplay extends Control
{
    private RenderBlocks renderer = new RenderBlocks();

    private final ItemStack stoneStack = new ItemStack(Blocks.stone);

    public float scale = 1.0f;
    public float translateY = 0f;
    public float translateX = 0f;
    public float rotX = 0f;
    public float rotY = 0f;
    public float rotZ = 0f;

    public BlockDisplay(int x, int y, int width, int height, Control parent)
    {
        super(x, y, width, height, parent);
    }

    @Override
    public void draw(int mouseX, int mouseY)
    {
        ScissorHelper.startScissor(mc, getX(), getY(), getWidth(), getHeight());

        GuiHelper.drawRect(getRect(), Color.BLACK);

        renderBlocks();

        ScissorHelper.endScissor();
    }


    private void renderBlocks()
    {
        GL11.glPushMatrix();
        RenderHelper.enableGUIStandardItemLighting();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glEnable(GL11.GL_COLOR_MATERIAL);
        GL11.glEnable(GL11.GL_LIGHTING);

        GL11.glColor3f(1.0f, 1.0f, 1.0f);
        mc.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
        GL11.glPushMatrix();
        GL11.glTranslatef((float) getX() + getWidth() / 2 - 8f, (float) (getY() + getHeight() / 2), -3.0F + 100.0f);
        GL11.glScalef(10.0F * scale, 10.0F * scale, 10.0F * scale);
        GL11.glTranslatef(translateX, translateY, 1.0F);
        GL11.glScalef(1.0F, 1.0F, -1.0F);
        GL11.glRotatef(210.0F + rotX, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(45.0F + rotY, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(00.0F + rotZ, 0.0F, 0.0F, 1.0F);

        GL11.glEnable(GL11.GL_DEPTH_TEST);


        renderDefaultBlock(Blocks.log, 0, 0, 0, 0);

        GL11.glDisable(GL11.GL_DEPTH_TEST);

        GL11.glPopMatrix();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();

        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        RenderHelper.enableStandardItemLighting();
        RenderHelper.disableStandardItemLighting();
    }

    private void renderDefaultBlock(Block block, int meta, int x, int y, int z)
    {
        Tessellator tessellator = Tessellator.instance;

        block.setBlockBoundsForItemRender();
        renderer.setRenderBoundsFromBlock(block);
        GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);

        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, -1.0F, 0.0F);
        renderer.renderFaceYNeg(block, x, y, z, renderer.getBlockIconFromSideAndMetadata(block, 0, meta));
        tessellator.draw();

        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        renderer.renderFaceYPos(block, x, y, z, renderer.getBlockIconFromSideAndMetadata(block, 1, meta));
        tessellator.draw();

        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, -1.0F);
        renderer.renderFaceZNeg(block, x, y, z, renderer.getBlockIconFromSideAndMetadata(block, 2, meta));
        tessellator.draw();

        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, 1.0F);
        renderer.renderFaceZPos(block, x, y, z, renderer.getBlockIconFromSideAndMetadata(block, 3, meta));
        tessellator.draw();

        tessellator.startDrawingQuads();
        tessellator.setNormal(-1.0F, 0.0F, 0.0F);
        renderer.renderFaceXNeg(block, x, y, z, renderer.getBlockIconFromSideAndMetadata(block, 4, meta));
        tessellator.draw();

        tessellator.startDrawingQuads();
        tessellator.setNormal(1.0F, 0.0F, 0.0F);
        renderer.renderFaceXPos(block, x, y, z, renderer.getBlockIconFromSideAndMetadata(block, 5, meta));
        tessellator.draw();

        GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
    }
}
