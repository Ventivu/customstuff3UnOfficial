package cubex2.cs3.ingame.gui.control;

import cubex2.cs3.common.IItemMatcher;

import java.util.List;

public interface IItemMatcherToolTipModifier
{
    void modifyToolTip(List<String> toolTipList, IItemMatcher itemMatcher);
}
