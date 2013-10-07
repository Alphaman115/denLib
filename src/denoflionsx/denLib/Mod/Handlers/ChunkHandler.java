package denoflionsx.denLib.Mod.Handlers;

import denoflionsx.denLib.Lib.denLib;
import denoflionsx.denLib.Mod.denLibMod;
import denoflionsx.denLib.NewConfig.ConfigField;
import java.util.ArrayList;
import java.util.HashMap;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.ChunkPosition;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.world.ChunkEvent;

public class ChunkHandler {

    @ConfigField(category = "TERemover", comment = "This is to remove busted TEs from your world. See target list.")
    public static boolean TileEntityRemover_Enabled = false;
    @ConfigField(category = "TERemover", comment = "Put your target TEs here.")
    public static String[] TileEntityTargetList = new String[]{};

    @ForgeSubscribe
    public void onEvent(ChunkEvent.Load e) {
        ArrayList<TileEntity> targets = new ArrayList();
        HashMap<ChunkPosition, TileEntity> map = (HashMap<ChunkPosition, TileEntity>) e.getChunk().chunkTileEntityMap;
        for (TileEntity t : map.values()) {
            for (String s : TileEntityTargetList) {
                if (t.getClass().getName().equals(denLib.StringUtils.removeSpaces(s))) {
                    targets.add(t);
                }
            }
        }
        for (TileEntity t : targets) {
            e.world.setBlock(t.xCoord, t.yCoord, t.zCoord, 0);
            denLibMod.Proxy.print("Removed TileEntity");
        }
    }
}
