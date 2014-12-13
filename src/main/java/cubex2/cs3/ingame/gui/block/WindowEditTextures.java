package cubex2.cs3.ingame.gui.block;

import cubex2.cs3.common.WrappedBlock;
import cubex2.cs3.ingame.gui.control.*;
import cubex2.cs3.ingame.gui.control.builder.LabelBuilder;
import cubex2.cs3.util.SimulatedWorld;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class WindowEditTextures extends WindowEditBlockAttribute
{
    private static final String[] directions = new String[]{"bottom", "top", "north", "south", "east", "east"};

    private Label[] labels;
    private TextBox[] textBoxes;
    private ResourceLocation[] locations;

    private CheckBox cbTransparent;
    private CheckBox cbSemiTransparent;
    private CheckBox cbTileTransparent;

    private WorldDisplay blockDisplay;

    private SimulatedWorld world;

    public WindowEditTextures(WrappedBlock block)
    {
        super(block, "textures", 150 + 153, 200);

        world = new SimulatedWorld(-1, -1, -2, 2, 0, 1);
        world.setBlock(wrappedBlock.block, 0, 0, -1);
        world.setBlock(wrappedBlock.block, 1, 0, -1);
        world.setBlock(wrappedBlock.block, 0, 0, 0);
        world.setBlock(wrappedBlock.block, 1, 0, 0);

        for (int i = -1; i < 3; i++)
        {
            for (int j = -2; j < 2; j++)
            {
                world.setBlock(Blocks.stone, i, -1, j);
            }
        }

        labels = new Label[6];
        textBoxes = new TextBox[6];
        locations = new ResourceLocation[6];

        for (int i = 0; i < labels.length; i++)
        {
            LabelBuilder lb = label(directions[i]);
            if (i == 0) lb = (LabelBuilder) lb.at(7, 7);
            else if (i == 1) lb = (LabelBuilder) lb.at(7 + 153, 7);
            else lb = (LabelBuilder) lb.below(textBoxes[i - 2]);
            labels[i] = lb.add();

            textBoxes[i] = textBox().below(labels[i]).height(13).width(150 - 14 - 18).add();
            textBoxes[i].setMaxLength(256);

            String textureString = wrappedBlock.container.getTexture(i).iconString;
            if (textureString.contains(":") && textureString.split(":")[0].equals(wrappedBlock.getPack().id.toLowerCase()) && !textureString.endsWith(":"))
                textureString = textureString.split(":")[1];
            textBoxes[i].setText(textureString);
        }

        cbTransparent = checkBox().below(textBoxes[4], 9).add();
        cbTransparent.setIsChecked(wrappedBlock.container.transparent);
        Label lbl = label("Transparent").rightTo(cbTransparent).add();

        cbSemiTransparent = checkBox().below(cbTransparent, 7).add();
        cbSemiTransparent.setIsChecked(wrappedBlock.container.semiTransparent);
        label("Semi Transparent").rightTo(cbSemiTransparent).add();

        cbTileTransparent = checkBox().below(cbSemiTransparent, 7).add();
        cbTileTransparent.setIsChecked(wrappedBlock.container.tileTransparent);
        label("Tile Transparent").rightTo(cbTileTransparent).add();

        blockDisplay = worldDisplay(world).rightTo(lbl, 30).size(75, 75).add();
        blockDisplay.rotate = false;
        blockDisplay.camY = 2.0f;
        blockDisplay.camX = 0.5f;
        blockDisplay.lookX = 1.0f;

        updatePreviewLocations();
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY)
    {
        if (cbTileTransparent.getIsChecked() && !cbTransparent.getIsChecked())
        {
            cbTransparent.setIsChecked(true);
        }

        handleDefaultButtonClick(c);
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
                wrappedBlock.container.getTexture(i).iconString = modId + ":" + textureName;
        }

        wrappedBlock.container.transparent = cbTransparent.getIsChecked();
        wrappedBlock.container.semiTransparent = cbSemiTransparent.getIsChecked();
        wrappedBlock.container.tileTransparent = cbTileTransparent.getIsChecked();

        mc.refreshResources();
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
        boolean prevTile = wrappedBlock.container.tileTransparent;
        boolean prevTrans = wrappedBlock.container.transparent;
        boolean prevSemi = wrappedBlock.container.semiTransparent;
        wrappedBlock.container.tileTransparent = cbTileTransparent.getIsChecked();
        wrappedBlock.container.transparent = cbTransparent.getIsChecked();
        wrappedBlock.container.semiTransparent = cbSemiTransparent.getIsChecked();
        super.draw(mouseX, mouseY, renderTick);
        wrappedBlock.container.tileTransparent = prevTile;
        wrappedBlock.container.transparent = prevTrans;
        wrappedBlock.container.semiTransparent = prevSemi;

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
