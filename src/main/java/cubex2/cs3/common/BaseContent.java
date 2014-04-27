package cubex2.cs3.common;

public abstract class BaseContent implements Content
{
    protected final BaseContentPack pack;

    public BaseContent(BaseContentPack pack)
    {
        this.pack = pack;
    }

    @Override
    public void apply()
    {
        pack.getContentRegistry(this).add(this);
        pack.save();
    }

    @Override
    public void edit()
    {
        pack.save();
    }

    @Override
    public void remove()
    {
        pack.getContentRegistry(this).remove(this);
        pack.save();
    }

    @Override
    public boolean canEdit()
    {
        return true;
    }

    @Override
    public boolean canRemove()
    {
        return true;
    }
}
