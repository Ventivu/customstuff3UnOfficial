package cubex2.cs3.network;

import cpw.mods.fml.common.network.ByteBufUtils;
import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.BaseContentPackLoader;
import cubex2.cs3.common.WrappedGui;
import cubex2.cs3.gui.InventoryItemStack;
import cubex2.cs3.gui.WindowContainerNormal;
import cubex2.cs3.ingame.gui.GuiBase;
import cubex2.cs3.registry.GuiRegistry;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class PacketOpenUserContainerGuiClient extends PacketOpenCustomGuiClient
{
    private String packName;
    private String guiName;
    private int slotInvType; // 0=stack
    private int slotId;

    public PacketOpenUserContainerGuiClient()
    {
    }

    public PacketOpenUserContainerGuiClient(WrappedGui gui, int slotId)
    {
        this.packName = gui.getPack().getName();
        this.guiName = gui.getName();
        this.slotInvType = 0;
        this.slotId = slotId;
    }

    public PacketOpenUserContainerGuiClient(int windowId, WrappedGui gui)
    {
        super(windowId);
        this.packName = gui.getPack().getName();
        this.guiName = gui.getName();
    }

    @Override
    public void fromBytes(ByteBuf buffer)
    {
        super.fromBytes(buffer);

        packName = ByteBufUtils.readUTF8String(buffer);
        guiName = ByteBufUtils.readUTF8String(buffer);
        slotInvType = buffer.readByte();
        if (slotInvType == 0)
        {
            slotId = buffer.readByte();
        }
    }

    @Override
    public void toBytes(ByteBuf buffer)
    {
        super.toBytes(buffer);

        ByteBufUtils.writeUTF8String(buffer, packName);
        ByteBufUtils.writeUTF8String(buffer, guiName);
        buffer.writeByte(slotInvType);
        if (slotInvType == 0)
        {
            buffer.writeByte(slotId);
        }
    }

    public static class Handler extends PacketOpenCustomGuiClient.Handler<PacketOpenUserContainerGuiClient>
    {
        @Override
        protected Object getClientGuiElement(PacketOpenUserContainerGuiClient message, EntityPlayer player)
        {
            BaseContentPack pack = BaseContentPackLoader.instance().getContentPack(message.packName);
            WrappedGui gui = ((GuiRegistry) pack.getContentRegistry(WrappedGui.class)).getGui(message.guiName);

            return GuiBase.createContainerGui(new WindowContainerNormal(gui, new InventoryItemStack(gui, player, message.slotId)));
        }
    }
}
