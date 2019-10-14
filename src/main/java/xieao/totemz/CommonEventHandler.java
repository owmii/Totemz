package xieao.totemz;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.stats.Stats;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xieao.lib.util.PlayerUtil;
import xieao.totemz.Item.TotemItem;

@Mod.EventBusSubscriber
public class CommonEventHandler {
    @SubscribeEvent
    public static void deathEvent(LivingDeathEvent event) {
        DamageSource damageSource = event.getSource();
        Entity killer = damageSource.getTrueSource();
        if (killer instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) killer;
            if (!PlayerUtil.isFake(player)) {
                ItemStack offHand = player.getHeldItemOffhand();
                if (offHand.getItem() == Items.TOTEM_OF_UNDYING || offHand.getItem() instanceof TotemItem) {
                    if (event.getEntityLiving() instanceof MobEntity) {
                        if (event.getEntityLiving().isNonBoss()) {
                            TotemItem.addKillPoints(offHand, Config.GENERAL.pointsPerKill.get());
                        } else {
                            TotemItem.addKillPoints(offHand, Config.GENERAL.pointsPerBossKill.get());
                        }
                        if (TotemItem.getKillPoints(offHand) >= TotemItem.getMaPoints(offHand)) {
                            ItemStack next = new ItemStack(TotemItem.getNextTotem(offHand));
                            if (!next.isEmpty()) {
                                player.setHeldItem(Hand.OFF_HAND, next);
                                player.sendMessage(new TranslationTextComponent(TextFormatting.GOLD + I18n.format("message.totemz.level.up"), "" + TextFormatting.WHITE + next.getDisplayName()));
                            }
                        }
                    }
                }
            }
        }
        if (event.getEntityLiving() instanceof PlayerEntity) {
            if (checkTotemDeathProtection((PlayerEntity) event.getEntityLiving(), event.getSource())) {
                event.setCanceled(true);
            }
        }
    }

    private static boolean checkTotemDeathProtection(PlayerEntity player, DamageSource damageSource) {
        if (damageSource.canHarmInCreative()) {
            return false;
        } else {
            ItemStack stack = null;

            for (Hand hand : Hand.values()) {
                ItemStack heldItem = player.getHeldItem(hand);
                if (heldItem.getItem() instanceof TotemItem) {
                    stack = heldItem.copy();
                    heldItem.shrink(1);
                    break;
                }
            }

            if (stack != null) {
                if (player instanceof ServerPlayerEntity) {
                    ServerPlayerEntity serverplayerentity = (ServerPlayerEntity) player;
                    serverplayerentity.addStat(Stats.ITEM_USED.get(Items.TOTEM_OF_UNDYING));
                    CriteriaTriggers.USED_TOTEM.trigger(serverplayerentity, stack);
                }
                player.clearActivePotions();
                TotemItem.onRevived(player, stack);
                player.world.setEntityState(player, (byte) 35);
            }
            return stack != null;
        }
    }
}
