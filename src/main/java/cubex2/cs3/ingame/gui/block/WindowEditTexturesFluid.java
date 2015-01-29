package cubex2.cs3.ingame.gui.block;

import cubex2.cs3.common.WrappedBlock;
import cubex2.cs3.ingame.gui.control.Label;
import cubex2.cs3.ingame.gui.control.TextBox;
import cubex2.cs3.ingame.gui.control.WorldDisplay;
import cubex2.cs3.ingame.gui.control.builder.LabelBuilder;
import cubex2.cs3.util.ClientHelper;
import cubex2.cs3.util.SimulatedWorld;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class WindowEditTexturesFluid extends WindowEditBlockAttribute
{
    private static final String[] TEXTURE_NAMES = new String[]{"still", "flowing"};

    private Label[] labels;
    private TextBox[] textBoxes;
    private ResourceLocation[] locations;

    protected WorldDisplay worldDisplay;
    protected SimulatedWorld world;

    public WindowEditTexturesFluid(WrappedBlock block)
    {
        super(block, "textures", 153, 200);

        world = new SimulatedWorld(-2, -1, -2, 2, 0, 2);
        for (int i = -2; i < 3; i++)
        {
            for (int j = -2; j < 3; j++)
            {
                world.setBlock(Blocks.stone, i, -1, j);
            }
        }
        world.setBlock(wrappedBlock.block, 0, -1, 0);
        world.setBlock(wrappedBlock.block, 1, -1, 0);
        world.setBlock(wrappedBlock.block, 0, -1, -1);
        world.setMetadata(1, 0, -1, -1);

        labels = new Label[2];
        textBoxes = new TextBox[2];
        locations = new ResourceLocation[2];

        for (int i = 0; i < labels.length; i++)
        {
            LabelBuilder lb = label(TEXTURE_NAMES[i]);
            if (i == 0) lb.at(7, 7);
            else lb.below(textBoxes[i - 1]);
            labels[i] = lb.add();

            textBoxes[i] = textBox().below(labels[i]).height(13).width(150 - 14 - 18).add();
            textBoxes[i].setMaxLength(256);

            String textureString = container.getTexture(TEXTURE_NAMES[i]).getTextForGui(wrappedBlock.getPack());
            textBoxes[i].setText(textureString);
        }

        worldDisplay = worldDisplay(world).fillWidth(7).bottom(btnCancel, 3).height(153 - 14 - 50).add();
        worldDisplay.rotate = false;
        worldDisplay.camY = 1.5f;
        worldDisplay.camX = 0.5f;
        worldDisplay.camZ = 1.5f;
        worldDisplay.lookX = 0.5f;
        worldDisplay.lookY = 0.5f;
        worldDisplay.lookZ = 0.5f;

        updatePreviewLocations();
    }

    @Override
    protected void applyChanges()
    {
        for (int i = 0; i < textBoxes.length; i++)
        {
            String text = textBoxes[i].getText().trim();

            String modId = text.contains(":") ? text.split(":")[0] : wrappedBlock.getPack().id.toLowerCase();
            String textureName = text.contains(":") && text.indexOf(':') != text.length() - 1 ? text.split(":")[1] : text;

            if (textureName.length() > 0)
            {
                container.getTexture(TEXTURE_NAMES[i]).iconString = modId + ":" + textureName;
            }
        }

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
        for (int i = 0; i < textBoxes.length; i++)
        {
            String text = textBoxes[i].getText().trim();

            String modId = text.contains(":") ? text.split(":")[0] : wrappedBlock.getPack().id.toLowerCase();
            String textureName = text.contains(":") && text.indexOf(':') != text.length() - 1 ? text.split(":")[1] : text;

            locations[i] = new ResourceLocation(modId + ":" + "textures/blocks/" + textureName + ".png");
        }
    }

    @Override
    public void draw(int mouseX, int mouseY, float renderTick)
    {
        super.draw(mouseX, mouseY, renderTick);

        GL11.glEnable(GL11.GL_BLEND);
        OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        GL11.glEnable(GL11.GL_ALPHA_TEST);

        for (int i = 0; i < locations.length; i++)
        {
            if (textBoxes[i] == null)
                continue;

            int posX = textBoxes[i].getX() + textBoxes[i].getWidth() + 3;
            int posY = textBoxes[i].getY() - 1;

            if (locations[i] != null)
            {
                mc.renderEngine.bindTexture(locations[i]);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                GL11.glScalef(1 / 16.0F, 1 / 16.0F, 1.0F);
                drawTexturedModalRect(posX * 16, posY * 16, 0, 0, 256, 256);
                GL11.glScalef(16.0F, 16.0F, 1.0F);
            }
        }

        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
    }
}
