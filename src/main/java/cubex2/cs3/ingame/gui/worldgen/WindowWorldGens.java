package cubex2.cs3.ingame.gui.worldgen;

import cubex2.cs3.common.WrappedWorldGen;
import cubex2.cs3.ingame.IngameContentPack;
import cubex2.cs3.ingame.gui.GuiBase;
import cubex2.cs3.ingame.gui.IWindowClosedListener;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.common.WindowEditAttributeContent;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.listbox.IListBoxItemClickListener;
import cubex2.cs3.ingame.gui.control.listbox.ListBox;
import cubex2.cs3.ingame.gui.control.listbox.ListBoxDescription;

public class WindowWorldGens extends Window implements IWindowClosedListener, IListBoxItemClickListener<WrappedWorldGen>
{
    private final IngameContentPack pack;

    private ListBox<WrappedWorldGen> listBox;

    public WindowWorldGens(IngameContentPack pack)
    {
        super("World Generators", NEW | EDIT | DELETE | BACK, 263, 160);
        this.pack = pack;

        ListBoxDescription<WrappedWorldGen> desc = new ListBoxDescription<WrappedWorldGen>(7, 7);
        desc.rows = 5;
        desc.columns = 1;
        desc.elementHeight = 22;
        desc.elements = pack.getContentRegistry(WrappedWorldGen.class).getContentList();
        desc.canSelect = true;
        desc.sorted = true;
        listBox = listBox(desc).fillWidth(7).top(7).add();

        btnEdit.setEnabled(false);
        btnDelete.setEnabled(false);
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY)
    {
        if (c == btnNew)
        {
            GuiBase.openWindow(new WindowCreateWorldGen(pack));
        } else if (c == btnEdit)
        {
            GuiBase.openWindow(new WindowEditAttributeContent(listBox.getSelectedItem()));
        } else if (c == btnDelete)
        {
            listBox.getSelectedItem().remove();
            listBox.updateElements(pack.getContentRegistry(WrappedWorldGen.class).getContentList());
            btnDelete.setEnabled(false);
            btnEdit.setEnabled(false);
        } else
        {
            handleDefaultButtonClick(c);
        }
    }

    @Override
    public void windowClosed(Window window)
    {
        listBox.updateElements(pack.getContentRegistry(WrappedWorldGen.class).getContentList());
        btnDelete.setEnabled(false);
        btnEdit.setEnabled(false);
    }

    @Override
    public void itemClicked(WrappedWorldGen item, ListBox<WrappedWorldGen> listBox, int button)
    {
        btnEdit.setEnabled(listBox.getSelectedIndex() != -1);
        btnDelete.setEnabled(listBox.getSelectedIndex() != -1);
    }
}
