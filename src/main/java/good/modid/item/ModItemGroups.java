package good.modid.item;

import good.modid.Good;
import good.modid.block.ModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import java.util.concurrent.atomic.AtomicReference;

public class ModItemGroups {

    // Use AtomicReference for lazy and safe initialization
    public static final AtomicReference<ItemGroup> GENE_TECH_GROUP_REF = new AtomicReference<>();

    public static void registerItemGroups() {
        if (GENE_TECH_GROUP_REF.get() == null) {
            ItemGroup registeredGroup = Registry.register(Registries.ITEM_GROUP,
                    new Identifier(Good.MOD_ID, "gene_tech"),
                    FabricItemGroup.builder()
                            .displayName(Text.translatable("itemgroup.good.gene_tech")) // The name of the tab
                            .icon(() -> new ItemStack(ModItems.SYRINGE)) // The icon of the tab
                            .entries((displayContext, entries) -> {
                                // Add items to the tab here
                                entries.add(ModItems.GENE_EXTRACTOR);
                                entries.add(ModItems.SYRINGE);
                                entries.add(ModBlocks.BIO_CENTRIFUGE);
                                entries.add(ModBlocks.GENE_EDITING_BENCH);

                                // Genes
                                entries.add(ModItems.AQP4_GENE);
                                entries.add(ModItems.RHO7_GENE);
                                entries.add(ModItems.NEC1_GENE);
                                entries.add(ModItems.TITAN2_GENE);
                                entries.add(ModItems.PYR9_GENE);
                                entries.add(ModItems.LTX4_GENE);

                                // Nutrients
                                entries.add(ModItems.AQP4_NUTRIENT);
                                entries.add(ModItems.RHO7_NUTRIENT);
                                entries.add(ModItems.NEC1_NUTRIENT);
                                entries.add(ModItems.TITAN2_NUTRIENT);
                                entries.add(ModItems.PYR9_NUTRIENT);
                                entries.add(ModItems.LTX4_NUTRIENT);

                                // Synergy Foods
                                entries.add(ModItems.ARMORED_TUBER_FOOD);
                                entries.add(ModItems.ABYSSAL_ALGAE_FOOD);
                                entries.add(ModItems.MAGMA_BEET_FOOD);
                            }).build());
            GENE_TECH_GROUP_REF.set(registeredGroup);
        }
        Good.LOGGER.info("Registering Item Groups for " + Good.MOD_ID);
    }
}