package denoflionsx.denLib.Mod;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import denoflionsx.denLib.Mod.API.denLibManagers;
import denoflionsx.denLib.Mod.Core.denLibCore;
import denoflionsx.denLib.Mod.Proxy.Proxy;
import denoflionsx.denLib.Mod.Version.Version;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.world.WorldEvent;


/* This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. See
 * http://sam.zoy.org/wtfpl/COPYING for more details. */

@Mod(modid = Version.name, name = Version.name, version = Version.version)
@NetworkMod(clientSideRequired = false, serverSideRequired = false)
public class denLibMod {

    @Mod.Instance(Version.name)
    public static Object instance;
    @SidedProxy(clientSide = Version.ProxyClient, serverSide = Version.ProxyServer)
    public static Proxy proxy;
    public static denLibCore core;
    public static final boolean debug = false;

    public denLibMod() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Mod.PreInit
    public void preload(FMLPreInitializationEvent evt) {
        core = new denLibCore();
        core.registerManagers();
    }

    @Mod.Init
    public void load(FMLInitializationEvent event) {
        if (debug) {
            denLibManagers.LeavesDropManager.registerDrop(new ItemStack(Item.book), 10);
        }
    }

    @Mod.PostInit
    public void modsLoaded(FMLPostInitializationEvent evt) {
    }

    @ForgeSubscribe
    public void onWorldLoaded(WorldEvent.Load event) {
    }

    @ForgeSubscribe
    public void onWorldEnded(WorldEvent.Unload event) {
        MinecraftForge.EVENT_BUS.unregister(this);
    }
}
