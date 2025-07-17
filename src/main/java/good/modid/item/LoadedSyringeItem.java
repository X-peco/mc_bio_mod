package good.modid.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LoadedSyringeItem extends Item {
    private final StatusEffectInstance effect;
    private final String nutrientName;

    public LoadedSyringeItem(Settings settings, StatusEffectInstance effect, String nutrientName) {
        super(settings);
        this.effect = effect;
        this.nutrientName = nutrientName;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        user.setCurrentHand(hand);
        return TypedActionResult.consume(itemStack);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (!(user instanceof PlayerEntity)) {
            return stack;
        }
        PlayerEntity player = (PlayerEntity) user;

        if (!world.isClient) {
            player.addStatusEffect(new StatusEffectInstance(this.effect));
            player.playSound(SoundEvents.ENTITY_GENERIC_DRINK, 1.0f, 1.0f);
        }

        if (!player.getAbilities().creativeMode) {
            stack.decrement(1);
        }
        return stack;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 32;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.literal("Loaded with: ").append(Text.literal(nutrientName).formatted(Formatting.GRAY)));
    }
}
