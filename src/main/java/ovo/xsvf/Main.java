package ovo.xsvf;

import ovo.xsvf.api.IConverter;
import ovo.xsvf.transformers.*;

import java.io.File;

public class Main {
    public static void main(String[] args) throws Exception {
        IConverter converter = new ResPackConverter(
                new File("run", "input.zip"),
                new File("run", "output.zip"),

                // transformers
                new OverlayIconsTransformer(),
                new BlcokStatesTransformer(),
                new ModelsTransformer(),
                new WidgetsTransformer(),
                new CreativeInventoryImageTransformer(),
                new FurnaceTransformer(),
                new Generic54ImageTransformer(),
                new ItemsPathTransformer(),
                new NetheriteItemsTransformer(),
                new ItemRenameTransformer(),
                new ClockTransformer(),
                new CompassTransformer(),
                new ArrowTransformer(),
                new BlockRenamingTransformer(),
                new RedstoneTransformer(),
                new BlockImageTransformer(),
                new ChestImageTransformer(),
                new InventoryImageTransformer(),
                new EnchantedItemGlintTransformer(),
                new SmithingImageTransformer(),
                new GrindStoneTransformer(),
                new CartographyTableTransformer(),
                new StonecutterTransformer(),
                new LoomImageTransformer(),
                new BrewingStandImageTransformer(),
                new ArmorLayersTransformer(),
                new ParticlesTransformer(),
                new PackInfoTransformer()
        );
        converter.convert();
    }
}