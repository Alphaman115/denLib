package denoflionsx.denLib.Mod.Proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatMessageComponent;

public class denLibClientProxy extends denLibProxy {
    
    @Override
    public void sendMessageToPlayer(String msg) {
        try {
            Minecraft.getMinecraft().thePlayer.sendChatToPlayer(ChatMessageComponent.func_111066_d("[denLibCore]: " + msg));
        } catch (Exception ex) {
        }
    }
}
