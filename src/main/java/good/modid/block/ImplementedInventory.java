package good.modid.block;

import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

public interface ImplementedInventory {
    DefaultedList<ItemStack> getItems();

    default int size() {
        return getItems().size();
    }

    default boolean isEmpty() {
        for (int i = 0; i < size(); i++) {
            ItemStack stack = getStack(i);
            if (!stack.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    default ItemStack getStack(int slot) {
        return getItems().get(slot);
    }

    default ItemStack removeStack(int slot, int count) {
        ItemStack result = Inventories.splitStack(getItems(), slot, count);
        if (!result.isEmpty()) {
            markDirty();
        }
        return result;
    }

    default ItemStack removeStack(int slot) {
        return Inventories.removeStack(getItems(), slot);
    }

    default void setStack(int slot, ItemStack stack) {
        getItems().set(slot, stack);
        if (stack.getCount() > getMaxCountPerStack()) {
            stack.setCount(getMaxCountPerStack());
        }
        markDirty();
    }

    default void clear() {
        getItems().clear();
        markDirty();
    }

    default int getMaxCountPerStack() {
        return 64;
    }

    default void markDirty() {
        // Override if you want custom behavior.
    }
}