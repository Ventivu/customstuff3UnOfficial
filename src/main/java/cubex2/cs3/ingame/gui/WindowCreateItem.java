package cubex2.cs3.ingame.gui;

import cubex2.cs3.common.WrappedItem;
import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.ingame.gui.control.*;
import cubex2.cs3.item.EnumItemType;

public class WindowCreateItem extends Window implements IValidityProvider
{
    private BaseContentPack pack;

    private Label lblName;
    private TextBox tbName;
    private Label lblType;
    private DropBox<String> dbType;

    private Label lblInfo;

    public WindowCreateItem(BaseContentPack pack)
    {
        super("New Item", CREATE | CANCEL, 180, 201);
        this.pack = pack;

        lblName = label("Name:").at(7, 7).add();
        tbName = textBox().below(lblName).size(166, 17).add();
        lblType = label("Type:").below(tbName, 5).add();
        dbType = dropBox(EnumItemType.getNames()).below(lblType).size(100, 15).add();
        lblInfo = label("You need to restart Minecraft\nin order to use or edit this item.").below(dbType, 10).add();

        tbName.setValidityProvider(this);
        dbType.setSelectedValue(EnumItemType.NORMAL.name);
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY)
    {
        if (c == btnCreate)
        {
            WrappedItem item = new WrappedItem(tbName.getText().trim(), EnumItemType.get(dbType.getSelectedValue()), pack);
            item.apply();

            GuiBase.openPrevWindow();
        }
        else
        {
            handleDefaultButtonClick(c);
        }
    }

    @Override
    public String checkValidity(TextBox tb)
    {
        String message = null;

        String text = tb.getText().trim();
        if (text.length() == 0)
            message = "Enter a name";
        else
        {
            for (WrappedItem item : pack.getContentRegistry(WrappedItem.class).getContentList())
            {
                if (item.getName().equals(text))
                {
                    message = "There is already a item with this name.";
                    break;
                }
            }
        }

        btnCreate.setEnabled(message == null);
        return message;
    }
}
