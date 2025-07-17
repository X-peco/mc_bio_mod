package good.modid.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.SquidEntity;
import net.minecraft.entity.mob.BlazeEntity;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import net.minecraft.entity.ItemEntity;

public class GeneExtractorItem extends Item {
    public GeneExtractorItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) attacker;
            World world = player.getWorld();

            if (!world.isClient()) {
                ItemStack extractedGene = null;
                String geneName = "";

                if (target instanceof SquidEntity) {
                    extractedGene = new ItemStack(ModItems.AQP4_GENE);
                    geneName = "AQP-4 Gene";
                } else if (target instanceof BatEntity) {
                    extractedGene = new ItemStack(ModItems.RHO7_GENE);
                    geneName = "RHO-7 Gene";
                } else if (target instanceof ZombieEntity) {
                    extractedGene = new ItemStack(ModItems.NEC1_GENE);
                    geneName = "NEC-1 Gene";
                } else if (target instanceof IronGolemEntity) {
                    extractedGene = new ItemStack(ModItems.TITAN2_GENE);
                    geneName = "TITAN-2 Gene";
                } else if (target instanceof BlazeEntity) {
                    extractedGene = new ItemStack(ModItems.PYR9_GENE);
                    geneName = "PYR-9 Gene";
                } else if (target instanceof SpiderEntity) {
                    extractedGene = new ItemStack(ModItems.LTX4_GENE);
                    geneName = "LTX-4 Gene";
                }

                if (extractedGene != null) {
                    stack.damage(1, player, (p) -> p.sendToolBreakStatus(player.getActiveHand()));
                    if (world.random.nextFloat() < 0.8f) {
                        ItemEntity itemEntity = new ItemEntity(world, target.getX(), target.getY(), target.getZ(), extractedGene);
                        world.spawnEntity(itemEntity);
                        player.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
                        player.sendMessage(Text.literal("Successfully extracted " + geneName + "!"), true);
                    } else {
                        player.playSound(SoundEvents.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                        player.sendMessage(Text.literal("Extraction failed. Try again."), true);
                    }
                    return true;
                }
            }
        }
        return super.postHit(stack, target, attacker);
    }
}
