package ovo.xsvf.transformers;

import ovo.xsvf.FileUtils;
import ovo.xsvf.api.ITransformer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

public class ItemRenamingTransformer implements ITransformer {
    private final HashMap<String, String> renamePairs = new HashMap<>();
    {
        renamePairs.put("gold_sword", "golden_sword");
        renamePairs.put("wood_sword", "wooden_sword");
        renamePairs.put("gold_helmet", "golden_helmet");
        renamePairs.put("gold_chestplate", "golden_chestplate");
        renamePairs.put("gold_leggings", "golden_leggings");
        renamePairs.put("gold_boots", "golden_boots");
        renamePairs.put("apple_golden", "golden_apple");
        renamePairs.put("bow_standby", "bow");
        renamePairs.put("book_enchanted", "enchanted_book");
        renamePairs.put("wood_axe", "wooden_axe");
        renamePairs.put("wood_pickaxe", "wooden_pickaxe");
        renamePairs.put("wood_shovel", "wooden_shovel");
        renamePairs.put("wood_hoe", "wooden_hoe");
        renamePairs.put("gold_axe", "golden_axe");
        renamePairs.put("gold_pickaxe", "golden_pickaxe");
        renamePairs.put("gold_shovel", "golden_shovel");
        renamePairs.put("gold_hoe", "golden_hoe");
        renamePairs.put("fishing_rod_uncast", "fishing_rod");
        renamePairs.put("potion_bottle_empty", "potion");
        renamePairs.put("potion_bottle_splash", "splash_potion");
        renamePairs.put("spider_eye_fermented", "fermented_spider_eye");
        renamePairs.put("melon_speckled", "glistering_melon_slice");
        renamePairs.put("melon", "melon_slice");
        renamePairs.put("carrot_golden", "golden_carrot");
        renamePairs.put("porkchop_raw", "porkchop");
        renamePairs.put("porkchop_cooked", "cooked_porkchop");
        renamePairs.put("chicken_raw", "chicken");
        renamePairs.put("chicken_cooked", "cooked_chicken");
        renamePairs.put("rabbit_raw", "rabbit");
        renamePairs.put("rabbit_cooked", "cooked_rabbit");
        renamePairs.put("beef_raw", "beef");
        renamePairs.put("beef_cooked", "cooked_beef");
        renamePairs.put("boat", "oak_boat");
        renamePairs.put("book_normal", "book");
        renamePairs.put("book_writable", "writable_book");
        renamePairs.put("book_written", "written_book");
        renamePairs.put("bucket_empty", "bucket");
        renamePairs.put("bucket_lava", "lava_bucket");
        renamePairs.put("bucket_water", "water_bucket");
        renamePairs.put("bucket_milk", "milk_bucket");
        renamePairs.put("door_acacia", "acacia_door");
        renamePairs.put("door_birch", "birch_door");
        renamePairs.put("door_dark_oak", "dark_oak_door");
        renamePairs.put("door_iron", "iron_door");
        renamePairs.put("door_jungle", "jungle_door");
        renamePairs.put("door_spruce", "spruce_door");
        renamePairs.put("door_wood", "oak_door");
        renamePairs.put("dye_powder_black", "black_dye");
        renamePairs.put("dye_powder_blue", "blue_dye");
        renamePairs.put("dye_powder_brown", "brown_dye");
        renamePairs.put("dye_powder_cyan", "cyan_dye");
        renamePairs.put("dye_powder_gray", "gray_dye");
        renamePairs.put("dye_powder_green", "green_dye");
        renamePairs.put("dye_powder_light_blue", "light_blue_dye");
        renamePairs.put("dye_powder_lime", "lime_dye");
        renamePairs.put("dye_powder_magenta", "magenta_dye");
        renamePairs.put("dye_powder_orange", "orange_dye");
        renamePairs.put("dye_powder_pink", "pink_dye");
        renamePairs.put("dye_powder_purple", "purple_dye");
        renamePairs.put("dye_powder_red", "red_dye");
        renamePairs.put("dye_powder_silver", "light_gray_dye");
        renamePairs.put("dye_powder_white", "white_dye");
        renamePairs.put("dye_powder_yellow", "yellow_dye");
        renamePairs.put("fireball", "fire_charge");
        renamePairs.put("fireworks", "firework_rocket");
        renamePairs.put("fireworks_charge", "firework_star");
        renamePairs.put("firework_charge_overlay", "firework_star_overlay");
        renamePairs.put("map_empty", "map");
        renamePairs.put("map_filled", "filled_map");
        renamePairs.put("minecart_chest", "chest_minecart");
        renamePairs.put("minecart_command_block", "command_block_minecart");
        renamePairs.put("minecart_furnace", "furnace_minecart");
        renamePairs.put("minecart_hopper", "hopper_minecart");
        renamePairs.put("minecart_normal", "minecart");
        renamePairs.put("minecart_tnt", "tnt_minecart");
        renamePairs.put("netherbrick", "nether_brick");
        renamePairs.put("record_11", "music_disc_11");
        renamePairs.put("record_13", "music_disc_13");
        renamePairs.put("record_blocks", "music_disc_blocks");
        renamePairs.put("record_cat", "music_disc_cat");
        renamePairs.put("record_chirp", "music_disc_chirp");
        renamePairs.put("record_far", "music_disc_far");
        renamePairs.put("record_mail", "music_disc_mail");
        renamePairs.put("record_mellohi", "music_disc_mellohi");
        renamePairs.put("record_stal", "music_disc_stal");
        renamePairs.put("record_strad", "music_disc_strad");
        renamePairs.put("record_wait", "music_disc_wait");
        renamePairs.put("record_ward", "music_disc_ward");
        renamePairs.put("redstone_dust", "redstone");
        renamePairs.put("seeds_melon", "melon_seeds");
        renamePairs.put("seeds_pumpkin", "pumpkin_seeds");
        renamePairs.put("seeds_wheat", "wheat_seeds");
        renamePairs.put("sign", "oak_sign");
        renamePairs.put("slimeball", "slime_ball");
        renamePairs.put("wooden_armorstand", "armor_stand");
        renamePairs.put("gold_horse_armor", "golden_horse_armor");
    }

    @Override
    public void transform(File inDir, File outDir, HashMap<String, Boolean> processed) throws IOException {
        Path itemsPathOld = Paths.get("assets", "minecraft", "textures", "items");
        Path itemsPathNew = Paths.get("assets", "minecraft", "textures", "item");

        for (var entry : renamePairs.entrySet()) {
            String oldName = entry.getKey();
            String newName = entry.getValue();

            FileUtils.copyFile(inDir.toPath().resolve(itemsPathOld.resolve(oldName + ".png")), outDir.toPath().resolve(itemsPathNew.resolve(newName + "")));
            FileUtils.deleteFile(outDir.toPath().resolve(itemsPathNew.resolve(oldName + ".png")));
            FileUtils.copyFile(inDir.toPath().resolve(itemsPathOld.resolve(oldName + ".mcmeta")), outDir.toPath().resolve(itemsPathNew.resolve(newName + ".mcmeta")));
            FileUtils.deleteFile(outDir.toPath().resolve(itemsPathNew.resolve(oldName + ".mcmeta")));

            // no need to put true in processed, because it has already been processed in the previous step
        }
    }
}
