package cubex2.cs3.ingame.gui.control;

import cubex2.cs3.lib.Color;
import cubex2.cs3.util.GuiHelper;
import cubex2.cs3.util.SimulatedWorld;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.init.Blocks;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.util.glu.Project;

public class WorldDisplay extends Control
{
    private RenderBlocks renderer = new RenderBlocks();

    private float rotation = 0.0f;

    public boolean rotate = true;

    public float camX = 0.0f;
    public float camY = 5.0f;
    public float camZ = 2.0f;

    public float lookX = 0.0f;
    public float lookY = 0.0f;
    public float lookZ = 0.0f;

    public IBlockDisplayRenderer renderProvider;

    private SimulatedWorld world;

    public WorldDisplay(SimulatedWorld world, int width, int height, Anchor anchor, int offsetX, int offsetY, Control parent)
    {
        super(width, height, anchor, offsetX, offsetY, parent);
        setWorld(world);
    }

    public void setWorld(SimulatedWorld world)
    {
        this.world = world;
        renderer.blockAccess = world;
        renderer.setRenderFromInside(false);
        renderer.setRenderAllFaces(false);
        renderer.flipTexture = false;
    }

    @Override
    public void draw(int mouseX, int mouseY, float renderTick)
    {
        GuiHelper.drawRect(getBounds(), Color.BLACK);

        GL11.glPushMatrix();
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glPushMatrix();
        GL11.glLoadIdentity();
        ScaledResolution res = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
        GL11.glViewport(getX() * res.getScaleFactor(), mc.displayHeight - getY() * res.getScaleFactor() - getHeight() * res.getScaleFactor(), getWidth() * res.getScaleFactor(), getHeight() * res.getScaleFactor());

        Project.gluPerspective(70.0F, (float) getWidth() / getHeight(), 0.1F, 2000.0F);

        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
        Project.gluLookAt(camX, camY, camZ, lookX, lookY, lookZ, 0, 1, 0);
        RenderHelper.enableStandardItemLighting();

        GL11.glRotatef(rotation, 0.0f, 1.0f, 0.0f);

        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(true);

        renderBlocks();

        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        RenderHelper.disableStandardItemLighting();
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glViewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
        GL11.glPopMatrix();
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glPopMatrix();
    }

    @Override
    public void onUpdate()
    {
        if (rotate)
        {
            rotation += 1.5f;
        }
    }

    private void renderBlocks()
    {
        mc.renderEngine.bindTexture(TextureMap.locationBlocksTexture);

        GL11.glPushMatrix();
        GL11.glTranslatef(0.0f, 0.0f, 0.0f);
        renderer.renderBlockAsItem(Blocks.air, 0, 1.0f);
        GL11.glPopMatrix();

        if (renderProvider != null)
        {
            renderProvider.renderBlocks(renderer);
            return;
        }

        if (world != null)
        {

            Tessellator.instance.startDrawingQuads();
            RenderHelper.enableGUIStandardItemLighting();

            for (int x = world.minX; x <= world.maxX; x++)
            {
                for (int y = world.minY; y <= world.maxY; y++)
                {
                    for (int z = world.minZ; z <= world.maxZ; z++)
                    {
                        Block block = world.getBlock(x, y, z);
                        if (block.getMaterial() != Material.air)
                        {
                            renderer.renderBlockByRenderType(block, x, y, z);
                        }
                    }
                }
            }
            Tessellator.instance.draw();

        }
    }

    private void preRenderBlocks(int pass)
    {

    }

    private void renderDefaultBlock(Block block, int meta, int x, int y, int z)
    {
        Tessellator tessellator = Tessellator.instance;

        block.setBlockBoundsForItemRender();
        renderer.setRenderBoundsFromBlock(block);

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
    }
}
