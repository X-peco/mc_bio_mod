package good.modid.block;

import good.modid.item.ModItems;
import good.modid.screen.BioCentrifugeScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class BioCentrifugeBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, ImplementedInventory, Inventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(2, ItemStack.EMPTY); // 1 input, 1 output

    private static final int INPUT_SLOT = 0;
    private static final int OUTPUT_SLOT = 1;

    public BioCentrifugeBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.BIO_CENTRIFUGE_BLOCK_ENTITY, pos, state);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public Text getDisplayName() {
        return Text.literal("Bio-Centrifuge");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new BioCentrifugeScreenHandler(syncId, playerInventory, this);
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

    public static void tick(World world, BlockPos pos, BlockState state, BioCentrifugeBlockEntity entity) {
        if (world.isClient()) {
            return;
        }

        if(hasRecipe(entity)) {
            craftItem(entity);
        }
    }

    private static void craftItem(BioCentrifugeBlockEntity entity) {
        SimpleInventory inventory = new SimpleInventory(entity.size());
        for (int i = 0; i < entity.size(); i++) {
            inventory.setStack(i, entity.getStack(i));
        }

        Item result = getResult(inventory.getStack(INPUT_SLOT).getItem());

        if (result != null) {
            inventory.getStack(INPUT_SLOT).decrement(1);
            entity.setStack(OUTPUT_SLOT, new ItemStack(result, entity.getStack(OUTPUT_SLOT).getCount() + 1));
        }
    }

    private static boolean hasRecipe(BioCentrifugeBlockEntity entity) {
        SimpleInventory inventory = new SimpleInventory(entity.size());
        for (int i = 0; i < entity.size(); i++) {
            inventory.setStack(i, entity.getStack(i));
        }

        Item result = getResult(inventory.getStack(INPUT_SLOT).getItem());

        return result != null && canInsertAmountIntoOutputSlot(inventory) &&
                canInsertItemIntoOutputSlot(inventory, result);
    }

    private static Item getResult(Item input) {
        Map<Item, Item> recipes = Map.of(
                ModItems.AQP4_GENE, ModItems.AQP4_NUTRIENT,
                ModItems.RHO7_GENE, ModItems.RHO7_NUTRIENT,
                ModItems.NEC1_GENE, ModItems.NEC1_NUTRIENT,
                ModItems.TITAN2_GENE, ModItems.TITAN2_NUTRIENT,
                ModItems.PYR9_GENE, ModItems.PYR9_NUTRIENT,
                ModItems.LTX4_GENE, ModItems.LTX4_NUTRIENT
        );
        return recipes.get(input);
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
