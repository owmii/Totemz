package xieao.totemz.Item;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import xieao.lib.item.ItemBase;
import xieao.totemz.Config;

import javax.annotation.Nullable;
import java.util.List;

public class TotemItem extends ItemBase {
    public TotemItem(Properties properties) {
        super(properties);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip(stack, tooltip);
    }

    public static void tooltip(ItemStack stack, List<ITextComponent> tooltip) {
        if (getMaPoints(stack) > 0) {
            tooltip.add(new StringTextComponent(TextFormatting.GRAY + I18n.format("tooltip.totemz.next.lvl") + TextFormatting.GOLD + getNetLevel(stack)));
            tooltip.add(new StringTextComponent(TextFormatting.GRAY + I18n.format("tooltip.totemz.kill.points") + TextFormatting.DARK_GRAY +
                    +getKillPoints(stack) + "/" + getMaPoints(stack)));
        }
    }

    public static void onRevived(PlayerEntity player, ItemStack stack) {
        int regLvl = 2;
        int regDur = 1200;
        int absLvl = 2;
        int absDur = 400;
        int resLvl = 0;
        int resDur = 0;
        if (stack.getItem() == IItems.TOTEM_OF_UNDYING_II) {
        } else if (stack.getItem() == IItems.TOTEM_OF_UNDYING_III) {
            regLvl = 3;
            absLvl = 3;
            regDur = 3000;
            absDur = 800;
            resLvl = 1;
            resDur = 200;
        } else if (stack.getItem() == IItems.TOTEM_OF_UNDYING_IV) {
            regLvl = 3;
            absLvl = 4;
            regDur = 3000;
            absDur = 1000;
            resLvl = 2;
            resDur = 400;
        } else if (stack.getItem() == IItems.TOTEM_OF_UNDYING_V) {
            regLvl = 3;
            absLvl = 5;
            regDur = 4000;
            absDur = 2000;
            resLvl = 2;
            resDur = 500;
        }
        player.setHealth(4.0F);
        player.addPotionEffect(new EffectInstance(Effects.REGENERATION, regDur, regLvl));
        player.addPotionEffect(new EffectInstance(Effects.ABSORPTION, absDur, absLvl));
        if (resDur > 0) {
            player.addPotionEffect(new EffectInstance(Effects.RESISTANCE, resDur, resLvl));
        }
    }

    public static Item getPrevTotem(ItemStack stack) {
        if (stack.getItem() == IItems.TOTEM_OF_UNDYING_II) {
            return Items.TOTEM_OF_UNDYING;
        } else if (stack.getItem() == IItems.TOTEM_OF_UNDYING_III) {
            return IItems.TOTEM_OF_UNDYING_II;
        } else if (stack.getItem() == IItems.TOTEM_OF_UNDYING_IV) {
            return IItems.TOTEM_OF_UNDYING_III;
        } else if (stack.getItem() == IItems.TOTEM_OF_UNDYING_V) {
            return IItems.TOTEM_OF_UNDYING_IV;
        }
        return Items.AIR;
    }

    public static Item getNextTotem(ItemStack stack) {
        if (stack.getItem() == Items.TOTEM_OF_UNDYING) {
            return IItems.TOTEM_OF_UNDYING_II;
        } else if (stack.getItem() == IItems.TOTEM_OF_UNDYING_II) {
            return IItems.TOTEM_OF_UNDYING_III;
        } else if (stack.getItem() == IItems.TOTEM_OF_UNDYING_III) {
            return IItems.TOTEM_OF_UNDYING_IV;
        } else if (stack.getItem() == IItems.TOTEM_OF_UNDYING_IV) {
            return IItems.TOTEM_OF_UNDYING_V;
        }
        return Items.AIR;
    }

    public static int getMaPoints(ItemStack stack) {
        if (stack.getItem() == Items.TOTEM_OF_UNDYING) {
            return Config.GENERAL.maxPoint1.get();
        } else if (stack.getItem() == IItems.TOTEM_OF_UNDYING_II) {
            return Config.GENERAL.maxPoint2.get();
        } else if (stack.getItem() == IItems.TOTEM_OF_UNDYING_III) {
            return Config.GENERAL.maxPoint3.get();
        } else if (stack.getItem() == IItems.TOTEM_OF_UNDYING_IV) {
            return Config.GENERAL.maxPoint4.get();
        }
        return 0;
    }

    public static String getNetLevel(ItemStack stack) {
        if (stack.getItem() == Items.TOTEM_OF_UNDYING) {
            return "II";
        } else if (stack.getItem() == IItems.TOTEM_OF_UNDYING_II) {
            return "III";
        } else if (stack.getItem() == IItems.TOTEM_OF_UNDYING_III) {
            return "IV";
        } else if (stack.getItem() == IItems.TOTEM_OF_UNDYING_IV) {
            return "V";
        }
        return "";
    }

    public static int getKillPoints(ItemStack stack) {
        CompoundNBT nbt = stack.getChildTag("TotemzTag");
        if (nbt != null) {
            return nbt.getInt("TotemzKillCounts");
        }
        return 0;
    }

    public static int addKillPoints(ItemStack stack, int i) {
        return setKillPoints(stack, i + getKillPoints(stack));
    }

    public static int setKillPoints(ItemStack stack, int i) {
        CompoundNBT nbt = stack.getOrCreateChildTag("TotemzTag");
        nbt.putInt("TotemzKillCounts", i);
        return i;
    }
}
