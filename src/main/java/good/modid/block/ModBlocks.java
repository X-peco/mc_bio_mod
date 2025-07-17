package good.modid.block;

import good.modid.Good;
import good.modid.block.BioCentrifugeBlock;
import good.modid.block.GeneEditingBenchBlock;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlocks {

    // Declare the block itself
    public static final Block BIO_CENTRIFUGE = new BioCentrifugeBlock(FabricBlockSettings.create().strength(4.0f).requiresTool());
    public static final Block GENE_EDITING_BENCH = new GeneEditingBenchBlock(FabricBlockSettings.create().strength(4.0f).requiresTool());

    // Declare the Block Entity Type
    public static final BlockEntityType<BioCentrifugeBlockEntity> BIO_CENTRIFUGE_BLOCK_ENTITY = 
            Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(Good.MOD_ID, "bio_centrifuge_block_entity"),
                    BlockEntityType.Builder.create(BioCentrifugeBlockEntity::new, BIO_CENTRIFUGE).build());

    public static final BlockEntityType<GeneEditingBenchBlockEntity> GENE_EDITING_BENCH_BLOCK_ENTITY = 
            Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(Good.MOD_ID, "gene_editing_bench_block_entity"),
                    BlockEntityType.Builder.create(GeneEditingBenchBlockEntity::new, GENE_EDITING_BENCH).build());

    private static void registerBlock(String name, Block block) {
        // Register the block
        Registry.register(Registries.BLOCK, new Identifier(Good.MOD_ID, name), block);
    }

    private static void registerBlockWithItem(String name, Block block) {
        registerBlock(name, block);
        // Register the corresponding BlockItem
        Registry.register(Registries.ITEM, new Identifier(Good.MOD_ID, name), new BlockItem(block, new FabricItemSettings()));
    }

    public static void registerModBlocks() {
        registerBlockWithItem("bio_centrifuge", BIO_CENTRIFUGE);
        registerBlockWithItem("gene_editing_bench", GENE_EDITING_BENCH);
    }
}