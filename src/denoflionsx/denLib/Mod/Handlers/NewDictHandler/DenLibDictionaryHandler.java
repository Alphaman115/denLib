package denoflionsx.denLib.Mod.Handlers.NewDictHandler;

import denoflionsx.denLib.Mod.denLibMod;
import java.util.ArrayList;
import java.util.HashMap;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.oredict.OreDictionary;

public class DenLibDictionaryHandler {

    private HashMap<String, ArrayList<ItemStack>> cache = new HashMap();
    private HashMap<String, ArrayList<IDenLibDictionaryHandler>> lists = new HashMap();

    public DenLibDictionaryHandler() {
        this.register();
        this.setup();
    }

    private void register() {
        denLibMod.Proxy.registerForgeSubscribe(this);
    }

    private void setup() {
        // Copied these from Forge. There is no way to catch these as events since they are in the static init.
        addToCache("logWood", new ItemStack(Block.wood, 1, OreDictionary.WILDCARD_VALUE));
        addToCache("plankWood", new ItemStack(Block.planks, 1, OreDictionary.WILDCARD_VALUE));
        addToCache("slabWood", new ItemStack(Block.woodSingleSlab, 1, OreDictionary.WILDCARD_VALUE));
        addToCache("stairWood", Block.stairsWoodOak);
        addToCache("stairWood", Block.stairsWoodBirch);
        addToCache("stairWood", Block.stairsWoodJungle);
        addToCache("stairWood", Block.stairsWoodSpruce);
        addToCache("stickWood", Item.stick);
        addToCache("treeSapling", new ItemStack(Block.sapling, 1, OreDictionary.WILDCARD_VALUE));
        addToCache("treeLeaves", new ItemStack(Block.leaves, 1, OreDictionary.WILDCARD_VALUE));
        addToCache("oreGold", Block.oreGold);
        addToCache("oreIron", Block.oreIron);
        addToCache("oreLapis", Block.oreLapis);
        addToCache("oreDiamond", Block.oreDiamond);
        addToCache("oreRedstone", Block.oreRedstone);
        addToCache("oreEmerald", Block.oreEmerald);
        addToCache("oreQuartz", Block.oreNetherQuartz);
        addToCache("stone", Block.stone);
        addToCache("cobblestone", Block.cobblestone);
        addToCache("record", Item.record13);
        addToCache("record", Item.recordCat);
        addToCache("record", Item.recordBlocks);
        addToCache("record", Item.recordChirp);
        addToCache("record", Item.recordFar);
        addToCache("record", Item.recordMall);
        addToCache("record", Item.recordMellohi);
        addToCache("record", Item.recordStal);
        addToCache("record", Item.recordStrad);
        addToCache("record", Item.recordWard);
        addToCache("record", Item.record11);
        addToCache("record", Item.recordWait);
        String[] dyes = {
            "dyeBlack",
            "dyeRed",
            "dyeGreen",
            "dyeBrown",
            "dyeBlue",
            "dyePurple",
            "dyeCyan",
            "dyeLightGray",
            "dyeGray",
            "dyePink",
            "dyeLime",
            "dyeYellow",
            "dyeLightBlue",
            "dyeMagenta",
            "dyeOrange",
            "dyeWhite"
        };
        for (int i = 0; i < 16; i++) {
            ItemStack dye = new ItemStack(Item.dyePowder, 1, i);
            addToCache(dyes[i], dye);
        }
    }

    public void register(IDenLibDictionaryHandler handler) {
        if (handler.lookingFor() == null) {
            addToList("null", handler);
        } else {
            addToList(handler.lookingFor(), handler);
        }
        for (String key : cache.keySet()) {
            for (ItemStack value : cache.get(key)) {
                doEvent(key, value);
            }
        }
    }

    private void addToList(String s, IDenLibDictionaryHandler handler) {
        if (!lists.containsKey(s)) {
            lists.put(s, new ArrayList());
        }
        lists.get(s).add(handler);
    }

    private boolean doesMatchWildcard(String handler, String ore) {
        String[] search = handler.split("\\*");
        for (String s : search) {
            if (ore.contains(s)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasWildcard(String s) {
        return s.contains("*");
    }

    @ForgeSubscribe
    public void onEvent(OreDictionary.OreRegisterEvent evt) {
        addToCache(evt.Name, evt.Ore);
        doEvent(evt.Name, evt.Ore);
    }

    private void addToCache(String Name, ItemStack Ore) {
        if (!cache.containsKey(Name)) {
            cache.put(Name, new ArrayList());
        }
        cache.get(Name).add(Ore);
    }

    private void addToCache(String Name, Block block) {
        addToCache(Name, new ItemStack(block));
    }

    private void addToCache(String name, Item item) {
        addToCache(name, new ItemStack(item));
    }

    private void doEvent(String Name, ItemStack Ore) {
        denLibMod.log("Ore: " + Name);
        for (String s : lists.keySet()) {
            if (hasWildcard(s)) {
                denLibMod.log(s + " has wildcard.");
                if (doesMatchWildcard(s, Name)) {
                    for (IDenLibDictionaryHandler h : lists.get(s)) {
                        h.onEvent(Ore);
                    }
                }
            } else {
                if (s.equals(Name)) {
                    for (IDenLibDictionaryHandler h : lists.get(s)) {
                        h.onEvent(Ore);
                    }
                }
            }
        }
    }
}
