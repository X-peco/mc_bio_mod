package good.modid;

import good.modid.screen.BioCentrifugeScreen;
import good.modid.screen.GeneEditingBenchScreen;
import good.modid.screen.ModScreenHandlers;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class GoodClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HandledScreens.register(ModScreenHandlers.BIO_CENTRIFUGE_SCREEN_HANDLER, BioCentrifugeScreen::new);
        HandledScreens.register(ModScreenHandlers.GENE_EDITING_BENCH_SCREEN_HANDLER, GeneEditingBenchScreen::new);
    }
}
