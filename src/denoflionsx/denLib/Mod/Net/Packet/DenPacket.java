package denoflionsx.denLib.Mod.Net.Packet;

import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet250CustomPayload;

public class DenPacket extends Packet250CustomPayload {

    private static final String id = "id";

    public DenPacket(int packetID, NBTTagCompound payload) {
        try {
            NBTTagCompound t = (NBTTagCompound) payload.copy();
            t.setInteger(id, packetID);
            this.data = CompressedStreamTools.compress(t);
            this.length = data.length;
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public DenPacket(Packet250CustomPayload packet) {
        this.data = packet.data;
        this.channel = packet.channel;
        this.isChunkDataPacket = packet.isChunkDataPacket;
        this.length = packet.length;
    }

    public NBTTagCompound getPayload() {
        try {
            return CompressedStreamTools.decompress(this.data);
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return new NBTTagCompound();
    }

    public int getPacketID() {
        return getPayload().getInteger(id);
    }

}
