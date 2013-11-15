package denoflionsx.denLib.Mod.Net.Packet;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import denoflionsx.denLib.Lib.denLib;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;

public class PacketManager implements IPacketHandler {

    private final BiMap<String, Integer> map = HashBiMap.create();
    private final BiMap<Integer, IPacketHandler> handlerMap = HashBiMap.create();

    public final int registerPacket(String name, IPacketHandler handler) {
        int newID = denLib.MathUtils.getLastID(map.inverse());
        map.put(name, newID);
        handlerMap.put(newID, handler);
        return newID;
    }

    public final DenPacket createNewPacket(String name) {
        return new DenPacket(map.get(name));
    }

    public final DenPacket createNewPacket(String name, NBTTagCompound tag) {
        return createNewPacket(name).setPayload(tag);
    }

    private void handlePacket(INetworkManager manager, DenPacket packet, Player player) {
        handlerMap.get(packet.getPacketID()).onPacketData(manager, packet, player);
    }

    private void handlePacket(INetworkManager manager, Packet250CustomPayload packet, Player player) {
        this.handlePacket(manager, new DenPacket(packet), player);
    }

    @Override
    public final void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
        this.handlePacket(manager, packet, player);
    }

}
