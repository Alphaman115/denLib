package denoflionsx.denLib.Mod.Proxy;

import cpw.mods.fml.client.FMLTextureFX;
import java.io.File;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.MinecraftForgeClient;

public class ProxyClient extends Proxy {
    
    public static ArrayList<FMLTextureFX> fx = new ArrayList();

    @Override
    public String preloadTextures(String texture) {
        MinecraftForgeClient.preloadTexture(texture);
        print("Preloaded Spritesheet: " + texture);
        return texture;
    }

    @Override
    public void sendMessageToPlayer(String msg) {
        Minecraft.getMinecraft().thePlayer.addChatMessage(msg);
    }

    @Override
    public String getConfigDir() {
        return Minecraft.getMinecraftDir() + File.separator + "config" + File.separator + "denoflionsx" + File.separator;
    }
}
