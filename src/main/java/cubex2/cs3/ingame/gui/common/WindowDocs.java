package cubex2.cs3.ingame.gui.common;

import com.google.common.base.Joiner;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.control.ControlContainer;
import cubex2.cs3.ingame.gui.control.Label;
import cubex2.cs3.ingame.gui.control.ScrollContainer;

public class WindowDocs extends Window
{
    protected final ScrollContainer scroll;
    protected final ControlContainer content;
    protected final Label label;

    public WindowDocs(String title, String doc)
    {
        super(title, BACK, 256, 200);

        doc = Joiner.on('\n').join(mc.fontRenderer.listFormattedStringToWidth(doc, width - 14 - 3 - ScrollContainer.SLIDER_WIDTH));
        scroll = scrollContainer(1/*Label.calcTextHeight(doc)*/).fillWidth(7).top(7).bottom(btnBack, 5).add();
        content = scroll.content();
        label = content.label(doc).at(0, 0).add();
        scroll.automaticTotalHeight();
    }
}
