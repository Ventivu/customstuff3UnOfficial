package cubex2.cs3;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cubex2.cs3.handler.KeyBindingHandler;
import cubex2.cs3.lib.RenderIds;
import cubex2.cs3.renderer.*;

public class ClientProxy extends CommonProxy
{
    @Override
    public void registerKeyBindings()
    {
        ClientRegistry.registerKeyBinding(KeyBindingHandler.openGuiKey);
        FMLCommonHandler.instance().bus().register(KeyBindingHandler.INSTANCE);
    }

    @Override
    public void registerEventHandlers()
    {
        super.registerEventHandlers();
    }

    @Override
    public void initRendering()
    {
        RenderIds.chestRenderId = RenderingRegistry.getNextAvailableRenderId();
        RenderIds.doorRenderId = RenderingRegistry.getNextAvailableRenderId();
        RenderIds.fenceRenderId = RenderingRegistry.getNextAvailableRenderId();
        RenderIds.fenceGateRenderId = RenderingRegistry.getNextAvailableRenderId();
        RenderIds.paneRenderId = RenderingRegistry.getNextAvailableRenderId();
        RenderIds.stairsRenderId = RenderingRegistry.getNextAvailableRenderId();
        RenderIds.torchRenderId = RenderingRegistry.getNextAvailableRenderId();
        RenderIds.wallRenderId = RenderingRegistry.getNextAvailableRenderId();
        RenderIds.wheatRenderId = RenderingRegistry.getNextAvailableRenderId();
        RenderIds.furnaceRenderId = RenderingRegistry.getNextAvailableRenderId();
        RenderIds.flatRenderId = RenderingRegistry.getNextAvailableRenderId();
        RenderIds.crossTexturePostRenderId = RenderingRegistry.getNextAvailableRenderId();
        RenderIds.facingRenderId = RenderingRegistry.getNextAvailableRenderId();
        RenderIds.postRenderId = RenderingRegistry.getNextAvailableRenderId();
        RenderIds.slopeRenderId = RenderingRegistry.getNextAvailableRenderId();

        //RenderingRegistry.registerBlockHandler(chestRenderer);
        RenderingRegistry.registerBlockHandler(new CSDoorRenderer());
        RenderingRegistry.registerBlockHandler(new CSFenceRenderer());
        RenderingRegistry.registerBlockHandler(new CSFenceGateRenderer());
        RenderingRegistry.registerBlockHandler(new CSPaneRenderer());
        RenderingRegistry.registerBlockHandler(new CSStairsRenderer());
        RenderingRegistry.registerBlockHandler(new CSTorchRenderer());
        RenderingRegistry.registerBlockHandler(new CSWallRenderer());
        RenderingRegistry.registerBlockHandler(new CSWheatRenderer());
        //RenderingRegistry.registerBlockHandler(furnaceRenderer);
        RenderingRegistry.registerBlockHandler(new CSFlatRenderer());
        RenderingRegistry.registerBlockHandler(new CSCrossTexturePostRenderer());
        RenderingRegistry.registerBlockHandler(new CSFacingRenderer());
        RenderingRegistry.registerBlockHandler(new CSPostRenderer());
        //RenderingRegistry.registerBlockHandler(slopeRenderer);
    }
}
