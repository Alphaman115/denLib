package denoflionsx.denLib.Mod.Proxy;

import cpw.mods.fml.common.FMLLog;
import denoflionsx.denLib.CoreMod.denLibCore;
import denoflionsx.denLib.Mod.Changelog.ChangelogReader;
import denoflionsx.denLib.Mod.Changelog.IChangeLogHandler;
import static denoflionsx.denLib.Mod.denLibMod.configManager;
import static denoflionsx.denLib.Mod.denLibMod.coreConfig;
import java.io.File;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatMessageComponent;
import net.minecraftforge.common.MinecraftForge;

public class denLibProxy implements IdenLibProxy {

    @Override
    public void print(String msg) {
        FMLLog.info("[denLib]: " + msg);
    }

    @Override
    public void warning(String warning) {
        FMLLog.warning("[denLib]: " + warning);
    }

    @Override
    public void registerForgeSubscribe(Object o) {
        MinecraftForge.EVENT_BUS.register(o);
    }

    @Override
    public void sendMessageToPlayer(String msg) {
    }

    @Override
    public void registerChangelogHandler(Object o) {
        ChangelogReader.handlers.add((IChangeLogHandler) o);
    }

    @Override
    public void makeDirs(File configDir) {
        if (!configDir.exists()) {
            configDir.mkdirs();
        }
    }

    @Override
    public void reloadConfig() {
        configManager.setup(denLibCore.location, coreConfig);
    }

    @Override
    public void sendMessageToCommandSender(ICommandSender sender, String msg) {
        sender.sendChatToPlayer(ChatMessageComponent.createFromText(msg));
    }
}
