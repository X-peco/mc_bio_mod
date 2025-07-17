package good.modid.block;

import good.modid.item.ModItems;
import good.modid.screen.GeneEditingBenchScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class GeneEditingBenchBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, ImplementedInventory, Inventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(4, ItemStack.EMPTY); // 3 input, 1 output

    private static final int INPUT1_SLOT = 0;
    private static final int INPUT2_SLOT = 1;
    private static final int INPUT3_SLOT = 2;
    private static final int OUTPUT_SLOT = 3;

    public GeneEditingBenchBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.GENE_EDITING_BENCH_BLOCK_ENTITY, pos, state);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public Text getDisplayName() {
        return Text.literal("Gene Editing Bench");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new GeneEditingBenchScreenHandler(syncId, playerInventory, this);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, inventory);
    }

    public static void tick(World world, BlockPos pos, BlockState state, GeneEditingBenchBlockEntity entity) {
        if (world.isClient()) {
            return;
        }

        if (hasRecipe(entity)) {
            craftItem(entity);
        }
    }

    private static boolean hasRecipe(GeneEditingBenchBlockEntity entity) {
        SimpleInventory inventory = new SimpleInventory(entity.size());
        for (int i = 0; i < entity.size(); i++) {
            inventory.setStack(i, entity.getStack(i));
        }

        // Check if input slots are not empty
        if (inventory.getStack(INPUT1_SLOT).isEmpty() || inventory.getStack(INPUT2_SLOT).isEmpty() || inventory.getStack(INPUT3_SLOT).isEmpty()) {
            return false;
        }

        // Get the result item based on the recipe
        Item result = getRecipe(inventory);

        // Check if a valid recipe exists and output slot can accept the item
        return result != null && canInsertAmountIntoOutputSlot(inventory) && canInsertItemIntoOutputSlot(inventory, result);
    }

    private static void craftItem(GeneEditingBenchBlockEntity entity) {
        SimpleInventory inventory = new SimpleInventory(entity.size());
        for (int i = 0; i < entity.size(); i++) {
            inventory.setStack(i, entity.getStack(i));
        }

        Item result = getRecipe(inventory);

        if (result != null) {
            // Consume input items
            inventory.getStack(INPUT1_SLOT).decrement(1);
            inventory.getStack(INPUT2_SLOT).decrement(1);
            inventory.getStack(INPUT3_SLOT).decrement(1);

            // Set output item
            entity.setStack(OUTPUT_SLOT, new ItemStack(result, entity.getStack(OUTPUT_SLOT).getCount() + 1));
        }
    }

    private static Item getRecipe(SimpleInventory inventory) {
        List<Item> inputItems = new ArrayList<>();
        inputItems.add(inventory.getStack(INPUT1_SLOT).getItem());
        inputItems.add(inventory.getStack(INPUT2_SLOT).getItem());
        inputItems.add(inventory.getStack(INPUT3_SLOT).getItem());

        // Sort items to handle order-independent recipes
        // Use a custom comparator to ensure consistent sorting of different item types
        inputItems.sort((item1, item2) -> {
            String id1 = Registries.ITEM.getId(item1).toString();
            String id2 = Registries.ITEM.getId(item2).toString();
            return id1.compareTo(id2);
        });

        // Define recipes
        // Armored Tuber Food: TITAN-2 + NEC-1 + Potato
        if (inputItems.get(0) == ModItems.NEC1_GENE && inputItems.get(1) == ModItems.TITAN2_GENE && inputItems.get(2) == Items.POTATO) {
            return ModItems.ARMORED_TUBER_FOOD;
        }
        // Abyssal Algae Food: AQP-4 + RHO-7 + Carrot
        if (inputItems.get(0) == ModItems.AQP4_GENE && inputItems.get(1) == ModItems.RHO7_GENE && inputItems.get(2) == Items.CARROT) {
            return ModItems.ABYSSAL_ALGAE_FOOD;
        }
        // Magma Beet Food: PYR-9 + TITAN-2 + Beetroot
        if (inputItems.get(0) == ModItems.PYR9_GENE && inputItems.get(1) == ModItems.TITAN2_GENE && inputItems.get(2) == Items.BEETROOT) {
            return ModItems.MAGMA_BEET_FOOD;
        }

        return null;
    }

    private static boolean canInsertItemIntoOutputSlot(SimpleInventory inventory, Item output) {
        return inventory.getStack(OUTPUT_SLOT).getItem() == output || inventory.getStack(OUTPUT_SLOT).isEmpty();
    }

    private static boolean canInsertAmountIntoOutputSlot(SimpleInventory inventory) {
        return inventory.getStack(OUTPUT_SLOT).getMaxCount() > inventory.getStack(OUTPUT_SLOT).getCount();
    }

    @Override
    public int size() {
        return inventory.size();
    }

    @Override
    public boolean isEmpty() {
        for (int i = 0; i < size(); i++) {
            ItemStack stack = inventory.get(i);
            if (!stack.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack getStack(int slot) {
        return inventory.get(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        ItemStack result = Inventories.splitStack(inventory, slot, amount);
        if (!result.isEmpty()) {
            markDirty();
        }
        return result;
    }

    @Override
    public ItemStack removeStack(int slot) {
        return Inventories.removeStack(inventory, slot);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        inventory.set(slot, stack);
        if (stack.getCount() > getMaxCountPerStack()) {
            stack.setCount(getMaxCountPerStack());
        }
        markDirty();
    }

    @Override
    public int getMaxCountPerStack() {
        return 64;
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return Inventory.canPlayerUse(this, player);
    }

    @Override
    public void clear() {
        inventory.clear();
        markDirty();
    }
}