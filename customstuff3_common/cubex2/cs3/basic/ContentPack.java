package cubex2.cs3.basic;

import cubex2.cs3.common.BaseContentPack;

import java.io.File;

public class ContentPack extends BaseContentPack
{
    public ContentPack(File directory)
    {
        super(directory);
    }

    @Override
    public void prepare()
    {
    }

    @Override
    public void init()
    {
        logger.info("Initializing...");
    }
}
