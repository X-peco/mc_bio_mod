package good.modid.item;

import good.modid.Good;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.FoodComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    // Core Tools
    public static final Item GENE_EXTRACTOR = new GeneExtractorItem(new FabricItemSettings().maxCount(1));
    public static final Item SYRINGE = new SyringeItem(new FabricItemSettings().maxCount(1));

    // Gene Items
    public static final Item AQP4_GENE = new Item(new FabricItemSettings());
    public static final Item RHO7_GENE = new Item(new FabricItemSettings());
    public static final Item NEC1_GENE = new Item(new FabricItemSettings());
    public static final Item TITAN2_GENE = new Item(new FabricItemSettings());
    public static final Item PYR9_GENE = new Item(new FabricItemSettings());
    public static final Item LTX4_GENE = new Item(new FabricItemSettings());

    // Nutrient Items
    public static final Item AQP4_NUTRIENT = new Item(new FabricItemSettings());
    public static final Item RHO7_NUTRIENT = new Item(new FabricItemSettings());
    public static final Item NEC1_NUTRIENT = new Item(new FabricItemSettings());
    public static final Item TITAN2_NUTRIENT = new Item(new FabricItemSettings());
    public static final Item PYR9_NUTRIENT = new Item(new FabricItemSettings());
    public static final Item LTX4_NUTRIENT = new Item(new FabricItemSettings());

    // Loaded Syringes
    public static final Item LOADED_SYRINGE_AQP4 = new LoadedSyringeItem(new FabricItemSettings().maxCount(1), new StatusEffectInstance(StatusEffects.WATER_BREATHING, 2400, 2), "Aquaporin Nutrient");
    public static final Item LOADED_SYRINGE_RHO7 = new LoadedSyringeItem(new FabricItemSettings().maxCount(1), new StatusEffectInstance(StatusEffects.NIGHT_VISION, 2400, 1), "Rhodopsin Nutrient");
    public static final Item LOADED_SYRINGE_NEC1 = new LoadedSyringeItem(new FabricItemSettings().maxCount(1), new StatusEffectInstance(StatusEffects.REGENERATION, 600, 0), "Regeneration Factor");
    public static final Item LOADED_SYRINGE_TITAN2 = new LoadedSyringeItem(new FabricItemSettings().maxCount(1), new StatusEffectInstance(StatusEffects.RESISTANCE, 1200, 0), "Structural Protein");
    public static final Item LOADED_SYRINGE_PYR9 = new LoadedSyringeItem(new FabricItemSettings().maxCount(1), new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 3600, 0), "Thermophilic Enzyme");

    // Synergy Food Items
    public static final Item ARMORED_TUBER_FOOD = new Item(new FabricItemSettings().food(new FoodComponent.Builder().hunger(6).saturationModifier(0.6f).alwaysEdible().statusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 1200, 1), 1.0f).build()));
    public static final Item ABYSSAL_ALGAE_FOOD = new Item(new FabricItemSettings().food(new FoodComponent.Builder().hunger(4).saturationModifier(0.4f).alwaysEdible().statusEffect(new StatusEffectInstance(StatusEffects.WATER_BREATHING, 3600, 0), 1.0f).statusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 3600, 0), 1.0f).build()));
    public static final Item MAGMA_BEET_FOOD = new Item(new FabricItemSettings().food(new FoodComponent.Builder().hunger(5).saturationModifier(0.5f).alwaysEdible().statusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 3600, 0), 1.0f).statusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 1200, 1), 1.0f).build()));

    // Register our items
    public static void registerModItems() {
        // Core Tools
        Registry.register(Registries.ITEM, new Identifier(Good.MOD_ID, "gene_extractor"), GENE_EXTRACTOR);
        Registry.register(Registries.ITEM, new Identifier(Good.MOD_ID, "syringe"), SYRINGE);

        // Gene Items
        Registry.register(Registries.ITEM, new Identifier(Good.MOD_ID, "aqp4_gene"), AQP4_GENE);
        Registry.register(Registries.ITEM, new Identifier(Good.MOD_ID, "rho7_gene"), RHO7_GENE);
        Registry.register(Registries.ITEM, new Identifier(Good.MOD_ID, "nec1_gene"), NEC1_GENE);
        Registry.register(Registries.ITEM, new Identifier(Good.MOD_ID, "titan2_gene"), TITAN2_GENE);
        Registry.register(Registries.ITEM, new Identifier(Good.MOD_ID, "pyr9_gene"), PYR9_GENE);
        Registry.register(Registries.ITEM, new Identifier(Good.MOD_ID, "ltx4_gene"), LTX4_GENE);

        // Nutrient Items
        Registry.register(Registries.ITEM, new Identifier(Good.MOD_ID, "aqp4_nutrient"), AQP4_NUTRIENT);
        Registry.register(Registries.ITEM, new Identifier(Good.MOD_ID, "rho7_nutrient"), RHO7_NUTRIENT);
        Registry.register(Registries.ITEM, new Identifier(Good.MOD_ID, "nec1_nutrient"), NEC1_NUTRIENT);
        Registry.register(Registries.ITEM, new Identifier(Good.MOD_ID, "titan2_nutrient"), TITAN2_NUTRIENT);
        Registry.register(Registries.ITEM, new Identifier(Good.MOD_ID, "pyr9_nutrient"), PYR9_NUTRIENT);
        Registry.register(Registries.ITEM, new Identifier(Good.MOD_ID, "ltx4_nutrient"), LTX4_NUTRIENT);

        // Loaded Syringes
        Registry.register(Registries.ITEM, new Identifier(Good.MOD_ID, "loaded_syringe_aqp4"), LOADED_SYRINGE_AQP4);
        Registry.register(Registries.ITEM, new Identifier(Good.MOD_ID, "loaded_syringe_rho7"), LOADED_SYRINGE_RHO7);
        Registry.register(Registries.ITEM, new Identifier(Good.MOD_ID, "loaded_syringe_nec1"), LOADED_SYRINGE_NEC1);
        Registry.register(Registries.ITEM, new Identifier(Good.MOD_ID, "loaded_syringe_titan2"), LOADED_SYRINGE_TITAN2);
        Registry.register(Registries.ITEM, new Identifier(Good.MOD_ID, "loaded_syringe_pyr9"), LOADED_SYRINGE_PYR9);

        // Synergy Food Items
        Registry.register(Registries.ITEM, new Identifier(Good.MOD_ID, "armored_tuber_food"), ARMORED_TUBER_FOOD);
        Registry.register(Registries.ITEM, new Identifier(Good.MOD_ID, "abyssal_algae_food"), ABYSSAL_ALGAE_FOOD);
        Registry.register(Registries.ITEM, new Identifier(Good.MOD_ID, "magma_beet_food"), MAGMA_BEET_FOOD);
    }
}