package cubex2.cs3.ingame.gui;

import cubex2.cs3.ingame.IngameContentPack;
import cubex2.cs3.ingame.IngameContentPackLoader;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.IValidityProvider;
import cubex2.cs3.ingame.gui.control.Label;
import cubex2.cs3.ingame.gui.control.TextBox;
import cubex2.cs3.ingame.gui.control.listbox.ListBox;

public class WindowNewPack extends Window implements IValidityProvider
{
    private TextBox tbName;
    private Label lblName;
    private ListBox<IngameContentPack> parentListBox;

    public WindowNewPack(ListBox<IngameContentPack> listBox)
    {
        super("New Content Pack", CREATE | CANCEL, 180, 201);
        parentListBox = listBox;
    }

    @Override
    public void init()
    {
        super.init();

        lblName = new Label("Name:", 7, 7, this);
        addControl(lblName);

        tbName = new TextBox(7, 17, 166, 17, this);
        tbName.setValidityProvider(this);
        addControl(tbName);

        btnCreate.setEnabled(false);
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY, int button)
    {
        if (c == btnCreate && button == 0)
        {
            IngameContentPack newPack = IngameContentPackLoader.instance().newContentPack(tbName.getText().trim());
            parentListBox.updateElements(IngameContentPackLoader.instance().getContentPacks());
            GuiBase.openPrevWindow();
        }
        else
        {
            super.controlClicked(c, mouseX, mouseY, button);
        }
    }

    @Override
    public String checkValidity(TextBox tb)
    {
        String message = null;

        String text = tb.getText().trim();
        if (text.length() == 0)
            message = "Enter a name.";
        else
        {
            for (IngameContentPack pack : IngameContentPackLoader.instance().getContentPacks())
            {
                if (pack.name.equals(text))
                {
                    message = "There is already a pack with this name.";
                    break;
                }
            }
        }

        btnCreate.setEnabled(message == null);
        return message;
    }
}
