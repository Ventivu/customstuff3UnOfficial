package cubex2.cs3.ingame.gui.block;

import cubex2.cs3.common.WrappedBlock;
import cubex2.cs3.ingame.gui.control.CheckBox;
import cubex2.cs3.ingame.gui.control.Label;
import cubex2.cs3.ingame.gui.control.TextBox;
import cubex2.cs3.ingame.gui.control.WorldDisplay;
import cubex2.cs3.util.ClientHelper;
import cubex2.cs3.util.SimulatedWorld;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class WindowEditTextureSingle  extends WindowEditBlockAttribute
{
    private Label label;
    private TextBox textBox;
    private ResourceLocation location;
    private CheckBox cbSemiTransparent;

    protected WorldDisplay worldDisplay;
    protected SimulatedWorld world;

    private String texture;

    public WindowEditTextureSingle(String texture, WrappedBlock block)
    {
        super(block, "texture", 153, 200);
        this.texture = texture;

        world = new SimulatedWorld(-2, -1, -2, 2, 0, 2);
        for (int i = -2; i < 3; i++)
        {
            for (int j = -2; j < 3; j++)
            {
                world.setBlock(Blocks.stone, i, -1, j);
            }
        }
        world.setBlock(wrappedBlock.block, 0, 0, 0);


        label = label("texture").at(7, 7).add();
        textBox = textBox().below(label).height(13).width(150 - 14 - 18).add();
        textBox.setMaxLength(256);

        String textureString = container.getTexture(texture).getTextForGui(wrappedBlock.getPack());
        textBox.setText(textureString);

        cbSemiTransparent = checkBox("Semi Transparent", container.semiTransparent).left(7).top(textBox, 5).add();

        worldDisplay = worldDisplay(world).fillWidth(7).bottom(btnCancel, 3).height(153 - 14 - 50).add();
        worldDisplay.rotate = false;
        worldDisplay.camY = 1.5f;
        worldDisplay.camX = -0.25f;
        worldDisplay.camZ = 0.5f;
        worldDisplay.lookX = 0.5f;
        worldDisplay.lookY = 0.5f;
        worldDisplay.lookZ = 0.5f;

        updatePreviewLocations();
    }

    @Override
    protected void applyChanges()
    {
        String text = textBox.getText().trim();

        String modId = text.contains(":") ? text.split(":")[0] : wrappedBlock.getPack().id.toLowerCase();
        String textureName = text.contains(":") && text.indexOf(':') != text.length() - 1 ? text.split(":")[1] : text;

        if (textureName.length() > 0)
        {
            container.getTexture(texture).iconString = modId + ":" + textureName;
        }

        container.semiTransparent = cbSemiTransparent.getIsChecked();

        ClientHelper.refreshResources(mc);
    }

    @Override
    public void keyTyped(char c, int key)
    {
        super.keyTyped(c, key);

        updatePreviewLocations();
    }

    private void updatePreviewLocations()
    {
        String text = textBox.getText().trim();

        String modId = text.contains(":") ? text.split(":")[0] : wrappedBlock.getPack().id.toLowerCase();
        String textureName = text.contains(":") && text.indexOf(':') != text.length() - 1 ? text.split(":")[1] : text;

        location = new ResourceLocation(modId + ":" + "textures/blocks/" + textureName + ".png");
    }

    @Override
    public void draw(int mouseX, int mouseY, float renderTick)
    {
        boolean prevSemi = container.semiTransparent;
        container.semiTransparent = cbSemiTransparent.getIsChecked();
        super.draw(mouseX, mouseY, renderTick);
        container.semiTransparent = prevSemi;

        GL11.glEnable(GL11.GL_BLEND);
        OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        GL11.glEnable(GL11.GL_ALPHA_TEST);

        int posX = textBox.getX() + textBox.getWidth() + 3;
        int posY = textBox.getY() - 1;

        if (location != null)
        {
            mc.renderEngine.bindTexture(location);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glScalef(1 / 16.0F, 1 / 16.0F, 1.0F);
            drawTexturedModalRect(posX * 16, posY * 16, 0, 0, 256, 256);
            GL11.glScalef(16.0F, 16.0F, 1.0F);
        }

        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
    }
}