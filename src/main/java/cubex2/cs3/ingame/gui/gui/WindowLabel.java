package cubex2.cs3.ingame.gui.gui;

import cubex2.cs3.gui.GuiData;
import cubex2.cs3.gui.LabelData;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.control.Label;
import cubex2.cs3.ingame.gui.control.TextBox;
import cubex2.cs3.lib.TextBoxValidators;

public class WindowLabel extends WindowEditOrCreateControl<Label, LabelData>
{
    private TextBox tbText;

    public WindowLabel(GuiData guiData, Window window, int x, int y)
    {
        super("Create Label", guiData, window, x, y, -1, -1);
    }

    public WindowLabel(GuiData guiData, Window window, Label control, LabelData data)
    {
        super("Edit Label", guiData, window, control, data);

        tbText.setText(data.text);
    }

    @Override
    protected void initControls(boolean hasSize)
    {
        super.initControls(hasSize);

        row("Text");
        tbText = row(textBox());
        tbText.setValidityProvider(TextBoxValidators.NOT_EMPTY);
    }

    @Override
    protected LabelData createData()
    {
        return new LabelData(nupX.getValue(), nupY.getValue(), tbText.getText().replace("\\n", "\n"));
    }

    @Override
    protected void edit()
    {
        data.text = tbText.getText();
        control.setText(data.text);
    }
}
