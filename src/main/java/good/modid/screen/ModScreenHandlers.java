package good.modid.screen;

import good.modid.Good;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreenHandlers {
    public static final ScreenHandlerType<BioCentrifugeScreenHandler> BIO_CENTRIFUGE_SCREEN_HANDLER =
            new ScreenHandlerType<>(BioCentrifugeScreenHandler::new, FeatureFlags.VANILLA_FEATURES);

    public static final ScreenHandlerType<GeneEditingBenchScreenHandler> GENE_EDITING_BENCH_SCREEN_HANDLER =
            new ScreenHandlerType<>(GeneEditingBenchScreenHandler::new, FeatureFlags.VANILLA_FEATURES);

    public static void registerScreenHandlers() {
        Registry.register(Registries.SCREEN_HANDLER, new Identifier(Good.MOD_ID, "bio_centrifuge"), BIO_CENTRIFUGE_SCREEN_HANDLER);
        Registry.register(Registries.SCREEN_HANDLER, new Identifier(Good.MOD_ID, "gene_editing_bench"), GENE_EDITING_BENCH_SCREEN_HANDLER);
    }
}
