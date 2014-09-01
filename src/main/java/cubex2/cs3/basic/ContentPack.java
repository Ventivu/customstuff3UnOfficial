package cubex2.cs3.basic;

import cubex2.cs3.common.BaseContentPack;

import java.io.File;

public class ContentPack extends BaseContentPack
{
    public ContentPack(File directory, String name, String id)
    {
        super(directory, name, id);
    }

    @Override
    public void init()
    {
        logger.info("Initializing...");
    }
}
