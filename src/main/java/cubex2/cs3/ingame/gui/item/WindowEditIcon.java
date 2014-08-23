package cubex2.cs3.ingame.gui.item;

import cubex2.cs3.common.WrappedItem;
import cubex2.cs3.ingame.gui.GuiBase;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.InfoButton;
import cubex2.cs3.ingame.gui.control.Label;
import cubex2.cs3.ingame.gui.control.TextBox;
import cubex2.cs3.lib.Color;
import cubex2.cs3.util.GuiHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Rectangle;

public class WindowEditIcon extends Window
{
    private WrappedItem wrappedItem;

    private Label lblIcon;
    private InfoButton infoButton;
    private TextBox textBox;
    private ResourceLocation location;

    public WindowEditIcon(WrappedItem item)
    {
        super("icon", EDIT | CANCEL, 150, 100);
        wrappedItem = item;
    }

    @Override
    public void init()
    {
        super.init();

        lblIcon = label("Icon name:").at(7, 7).add();
        infoButton = infoButton("The format is [modId]:[texture]|" +
                "[modId] is the mod's ID in all lowercase|" +
                "  this has to match the name of folder|" +
                "  located in the assets folder of the mod|" +
                "[texture] is name of the file without \".png\"||" +
                "Example: If the texture is located at|" +
                " \"assets/mymod/textures/items/myItem.png\"|" +
                "you have to enter|" +
                " \"mymod:myItem\"").rightTo(lblIcon).add();
        textBox = textBox().below(lblIcon).height(16).fillWidth(7).add();
        textBox.setMaxLength(256);

        String iconString = wrappedItem.container.icon.iconString;
        if (iconString.contains(":") && iconString.split(":")[0].equals(wrappedItem.getPack().id.toLowerCase()))
            iconString = iconString.split(":")[1];
        textBox.setText(iconString);

        updatePreviewLocation();
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY, int button)
    {
        if (button != 0)
            return;

        if (c == btnEdit)
        {
            String text = textBox.getText().trim();

            String modId = text.contains(":") ? text.split(":")[0] : wrappedItem.getPack().id.toLowerCase();
            String textureName = text.contains(":") && text.indexOf(':') != text.length() - 1 ? text.split(":")[1] : text;

            wrappedItem.container.icon.iconString = modId + ":" + textureName;
            wrappedItem.getPack().save();

            wrappedItem.item.setTextureName(modId + ":" + textureName);

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

        updatePreviewLocation();
    }

    private void updatePreviewLocation()
    {
        String text = textBox.getText().trim();

        String modId = text.contains(":") ? text.split(":")[0] : wrappedItem.getPack().id.toLowerCase();
        String textureName = text.contains(":") && text.indexOf(':') != text.length() - 1 ? text.split(":")[1] : text;

        location = new ResourceLocation(modId + ":" + "textures/items/" + textureName + ".png");
    }

    @Override
    public void draw(int mouseX, int mouseY)
    {
        super.draw(mouseX, mouseY);

        if (location != null)
        {
            mc.renderEngine.bindTexture(location);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glScalef(1 / 16.0F, 1 / 16.0F, 1.0F);
            drawTexturedModalRect((getX() + 7 + 1) * 16, (getY() + 40 + 1) * 16, 0, 0, 256, 256);
            GL11.glScalef(16.0F, 16.0F, 1.0F);
        }

        if (new Rectangle(getX() + 7, getY() + 40, 18, 18).contains(mouseX, mouseY))
        {
            GuiHelper.drawItemToolTip(new ItemStack(wrappedItem.item), mouseX, mouseY, mc.fontRenderer);
        }

        GuiHelper.drawBorder(getX() + 7, getY() + 40, getX() + 7 + 18, getY() + 40 + 18, Color.DARK_GREY);
    }
}