package cubex2.cs3.ingame.gui.block;

import cubex2.cs3.common.WrappedBlock;
import cubex2.cs3.ingame.gui.GuiBase;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.control.CheckBox;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.Label;
import cubex2.cs3.ingame.gui.control.TextBox;
import cubex2.cs3.ingame.gui.control.builder.LabelBuilder;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class WindowEditTextures extends Window
{
    private static final String[] directions = new String[]{"bottom", "top", "north", "south", "east", "east"};
    private WrappedBlock wrappedBlock;

    private Label[] labels;
    private TextBox[] textBoxes;
    private ResourceLocation[] locations;

    private CheckBox cbTransparent;
    private CheckBox cbSemiTransparent;
    private CheckBox cbTileTransparent;

    public WindowEditTextures(WrappedBlock block)
    {
        super("textures", EDIT | CANCEL, 150 + 153, 200);
        wrappedBlock = block;
    }

    @Override
    public void init()
    {
        super.init();

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
        label("Transparent").rightTo(cbTransparent).add();

        cbSemiTransparent = checkBox().below(cbTransparent, 7).add();
        cbSemiTransparent.setIsChecked(wrappedBlock.container.semiTransparent);
        label("Semi Transparent").rightTo(cbSemiTransparent).add();

        cbTileTransparent = checkBox().below(cbSemiTransparent, 7).add();
        cbTileTransparent.setIsChecked(wrappedBlock.container.tileTransparent);
        label("Tile Transparent").rightTo(cbTileTransparent).add();

        updatePreviewLocations();
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY, int button)
    {
        if (button != 0)
            return;

        if (c == btnEdit)
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

            wrappedBlock.getPack().save();

            mc.refreshResources();

            GuiBase.openPrevWindow();
        } else
        {
            super.controlClicked(c, mouseX, mouseY, button);
        }
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
    public void draw(int mouseX, int mouseY)
    {
        super.draw(mouseX, mouseY);

        for (int i = 0; i < locations.length; i++)
        {
            if (textBoxes[i] == null)
                continue;

            int posX = textBoxes[i].getX() + textBoxes[i].getWidth() + 3;
            int posY = textBoxes[i].getY();

            if (locations[i] != null)
            {
                mc.renderEngine.bindTexture(locations[i]);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                GL11.glScalef(1 / 16.0F, 1 / 16.0F, 1.0F);
                drawTexturedModalRect(posX * 16, posY * 16, 0, 0, 256, 256);
                GL11.glScalef(16.0F, 16.0F, 1.0F);
            }
        }
    }
}
