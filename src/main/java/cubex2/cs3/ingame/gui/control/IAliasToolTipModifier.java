package cubex2.cs3.ingame.gui.control;

import cubex2.cs3.common.Alias;

import java.util.List;

public interface IAliasToolTipModifier
{
    void modifyToolTip(List<String> toolTipList, Alias alias);
}
