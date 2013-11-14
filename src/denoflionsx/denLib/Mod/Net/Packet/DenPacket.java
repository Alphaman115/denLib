package denoflionsx.denLib.Mod.Net.Packet;

import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet250CustomPayload;

public class DenPacket extends Packet250CustomPayload {
    
    public DenPacket(int packetID, NBTTagCompound payload) {
        try {
            NBTTagCompound t = (NBTTagCompound) payload.copy();
            t.setInteger("id", packetID);
            this.data = CompressedStreamTools.compress(t);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
    
    public NBTTagCompound getPayload() {
        try {
            return CompressedStreamTools.decompress(this.data);
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return new NBTTagCompound();
    }
    
}
