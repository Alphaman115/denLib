package denoflionsx.denLib.Mod.Commands;

import denoflionsx.denLib.Mod.Handlers.ChunkHandler;
import denoflionsx.denLib.Mod.denLibMod;
import java.util.ArrayList;
import java.util.Arrays;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatMessageComponent;

public class denLibCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return "denLib";
    }

    @Override
    public String getCommandUsage(ICommandSender icommandsender) {
        return "denLib <function> <parameters> (Valid functions: teremover, backup)";
    }

    @Override
    public void processCommand(ICommandSender icommandsender, String[] astring) {
        try {
            if (astring[0].equals("teremover")) {
                if (astring[1].equals("add")) {
                    ArrayList<String> add = new ArrayList();
                    add.addAll(Arrays.asList(ChunkHandler.TileEntityTargetList));
                    if (!add.contains(astring[2])) {
                        add.add(astring[2]);
                        denLibMod.Proxy.sendMessageToCommandSender(icommandsender, "Added " + astring[2] + " from TE remover target list.");
                    }
                    add.trimToSize();
                    ChunkHandler.TileEntityTargetList = add.toArray(new String[add.size()]);
                    denLibMod.Proxy.reloadConfig();
                } else if (astring[1].equals("remove")) {
                    ArrayList<String> add = new ArrayList();
                    add.addAll(Arrays.asList(ChunkHandler.TileEntityTargetList));
                    if (add.contains(astring[2])) {
                        add.remove(astring[2]);
                        denLibMod.Proxy.sendMessageToCommandSender(icommandsender, "Removed " + astring[2] + " from TE remover target list.");
                    }
                    add.trimToSize();
                    ChunkHandler.TileEntityTargetList = add.toArray(new String[add.size()]);
                    denLibMod.Proxy.reloadConfig();
                } else if (astring[1].equals("removeAll")) {
                    denLibMod.Proxy.sendMessageToCommandSender(icommandsender, "Cleared TE target list.");
                    ChunkHandler.TileEntityTargetList = new String[]{};
                    denLibMod.Proxy.reloadConfig();
                }
            }
        } catch (Throwable t) {
        }
    }
}
