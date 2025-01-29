package ovo.xsvf.transformers;

import ovo.xsvf.FileUtils;
import ovo.xsvf.api.ITransformer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

public class ItemRenameTransformer implements ITransformer {
    private final HashMap<String, String> renamePairs = new HashMap<>();
    {
        renamePairs.put("gold_sword.png", "golden_sword.png");
        renamePairs.put("wood_sword.png", "wooden_sword.png");
        renamePairs.put("gold_helmet.png", "golden_helmet.png");
        renamePairs.put("gold_chestplate.png", "golden_chestplate.png");
        renamePairs.put("gold_leggings.png", "golden_leggings.png");
        renamePairs.put("gold_boots.png", "golden_boots.png");
        renamePairs.put("apple_golden.png", "golden_apple.png");
        renamePairs.put("bow_standby.png", "bow.png");
        renamePairs.put("book_enchanted.png", "enchanted_book.png");
        renamePairs.put("wood_axe.png", "wooden_axe.png");
        renamePairs.put("wood_pickaxe.png", "wooden_pickaxe.png");
        renamePairs.put("wood_shovel.png", "wooden_shovel.png");
        renamePairs.put("wood_hoe.png", "wooden_hoe.png");
        renamePairs.put("gold_axe.png", "golden_axe.png");
        renamePairs.put("gold_pickaxe.png", "golden_pickaxe.png");
        renamePairs.put("gold_shovel.png", "golden_shovel.png");
        renamePairs.put("gold_hoe.png", "golden_hoe.png");
        renamePairs.put("fishing_rod_uncast.png", "fishing_rod.png");
        renamePairs.put("potion_bottle_empty.png", "potion.png");
        renamePairs.put("potion_bottle_splash.png", "splash_potion.png");
        renamePairs.put("spider_eye_fermented.png", "fermented_spider_eye.png");
        renamePairs.put("melon_speckled.png", "glistering_melon_slice.png");
        renamePairs.put("melon.png", "melon_slice.png");
        renamePairs.put("carrot_golden.png", "golden_carrot.png");
        renamePairs.put("porkchop_raw.png", "porkchop.png");
        renamePairs.put("porkchop_cooked.png", "cooked_porkchop.png");
        renamePairs.put("chicken_raw.png", "chicken.png");
        renamePairs.put("chicken_cooked.png", "cooked_chicken.png");
        renamePairs.put("rabbit_raw.png", "rabbit.png");
        renamePairs.put("rabbit_cooked.png", "cooked_rabbit.png");
        renamePairs.put("beef_raw.png", "beef.png");
        renamePairs.put("beef_cooked.png", "cooked_beef.png");
        renamePairs.put("boat.png", "oak_boat.png");
        renamePairs.put("book_normal.png", "book.png");
        renamePairs.put("book_writable.png", "writable_book.png");
        renamePairs.put("book_written.png", "written_book.png");
        renamePairs.put("bucket_empty.png", "bucket.png");
        renamePairs.put("bucket_lava.png", "lava_bucket.png");
        renamePairs.put("bucket_water.png", "water_bucket.png");
        renamePairs.put("bucket_milk.png", "milk_bucket.png");
        renamePairs.put("door_acacia.png", "acacia_door.png");
        renamePairs.put("door_birch.png", "birch_door.png");
        renamePairs.put("door_dark_oak.png", "dark_oak_door.png");
        renamePairs.put("door_iron.png", "iron_door.png");
        renamePairs.put("door_jungle.png", "jungle_door.png");
        renamePairs.put("door_spruce.png", "spruce_door.png");
        renamePairs.put("door_wood.png", "oak_door.png");
        renamePairs.put("dye_powder_black.png", "black_dye.png");
        renamePairs.put("dye_powder_blue.png", "blue_dye.png");
        renamePairs.put("dye_powder_brown.png", "brown_dye.png");
        renamePairs.put("dye_powder_cyan.png", "cyan_dye.png");
        renamePairs.put("dye_powder_gray.png", "gray_dye.png");
        renamePairs.put("dye_powder_green.png", "green_dye.png");
        renamePairs.put("dye_powder_light_blue.png", "light_blue_dye.png");
        renamePairs.put("dye_powder_lime.png", "lime_dye.png");
        renamePairs.put("dye_powder_magenta.png", "magenta_dye.png");
        renamePairs.put("dye_powder_orange.png", "orange_dye.png");
        renamePairs.put("dye_powder_pink.png", "pink_dye.png");
        renamePairs.put("dye_powder_purple.png", "purple_dye.png");
        renamePairs.put("dye_powder_red.png", "red_dye.png");
        renamePairs.put("dye_powder_silver.png", "light_gray_dye.png");
        renamePairs.put("dye_powder_white.png", "white_dye.png");
        renamePairs.put("dye_powder_yellow.png", "yellow_dye.png");
        renamePairs.put("fireball.png", "fire_charge.png");
        renamePairs.put("fireworks.png", "firework_rocket.png");
        renamePairs.put("fireworks_charge.png", "firework_star.png");
        renamePairs.put("firework_charge_overlay.png", "firework_star_overlay.png");
        renamePairs.put("map_empty.png", "map.png");
        renamePairs.put("map_filled.png", "filled_map.png");
        renamePairs.put("minecart_chest.png", "chest_minecart.png");
        renamePairs.put("minecart_command_block.png", "command_block_minecart.png");
        renamePairs.put("minecart_furnace.png", "furnace_minecart.png");
        renamePairs.put("minecart_hopper.png", "hopper_minecart.png");
        renamePairs.put("minecart_normal.png", "minecart.png");
        renamePairs.put("minecart_tnt.png", "tnt_minecart.png");
        renamePairs.put("netherbrick.png", "nether_brick.png");
        renamePairs.put("record_11.png", "music_disc_11.png");
        renamePairs.put("record_13.png", "music_disc_13.png");
        renamePairs.put("record_blocks.png", "music_disc_blocks.png");
        renamePairs.put("record_cat.png", "music_disc_cat.png");
        renamePairs.put("record_chirp.png", "music_disc_chirp.png");
        renamePairs.put("record_far.png", "music_disc_far.png");
        renamePairs.put("record_mail.png", "music_disc_mail.png");
        renamePairs.put("record_mellohi.png", "music_disc_mellohi.png");
        renamePairs.put("record_stal.png", "music_disc_stal.png");
        renamePairs.put("record_strad.png", "music_disc_strad.png");
        renamePairs.put("record_wait.png", "music_disc_wait.png");
        renamePairs.put("record_ward.png", "music_disc_ward.png");
        renamePairs.put("redstone_dust.png", "redstone.png");
        renamePairs.put("seeds_melon.png", "melon_seeds.png");
        renamePairs.put("seeds_pumpkin.png", "pumpkin_seeds.png");
        renamePairs.put("seeds_wheat.png", "wheat_seeds.png");
        renamePairs.put("sign.png", "oak_sign.png");
        renamePairs.put("slimeball.png", "slime_ball.png");
        renamePairs.put("wooden_armorstand.png", "armor_stand.png");
        renamePairs.put("gold_horse_armor.png", "golden_horse_armor.png");
    }

    @Override
    public void transform(File inDir, File outDir, HashMap<String, Boolean> processed) throws IOException {
        Path itemsPathOld = Paths.get("assets", "minecraft", "textures", "items");
        Path itemsPathNew = Paths.get("assets", "minecraft", "textures", "item");

        for (var entry : renamePairs.entrySet()) {
            String oldName = entry.getKey();
            String newName = entry.getValue();

            FileUtils.copyFile(inDir.toPath().resolve(itemsPathOld.resolve(oldName + ".png")), outDir.toPath().resolve(itemsPathNew.resolve(newName + ".png")));
            FileUtils.deleteFile(outDir.toPath().resolve(itemsPathNew.resolve(oldName + ".png")));
            FileUtils.copyFile(inDir.toPath().resolve(itemsPathOld.resolve(oldName + ".mcmeta")), outDir.toPath().resolve(itemsPathNew.resolve(newName + ".mcmeta")));
            FileUtils.deleteFile(outDir.toPath().resolve(itemsPathNew.resolve(oldName + ".mcmeta")));

            // no need to put true in processed, because it has already been processed in the previous step
        }
    }
}
