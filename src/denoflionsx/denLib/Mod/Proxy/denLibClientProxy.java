package denoflionsx.denLib.Mod.Proxy;

import net.minecraft.client.Minecraft;

public class denLibClientProxy extends denLibProxy {

    @Override
    public void sendMessageToPlayer(String msg) {
        try {
            Minecraft.getMinecraft().thePlayer.sendChatToPlayer("[denLibCore]: " + msg);
        } catch (Exception ex) {
        }
    }
}
