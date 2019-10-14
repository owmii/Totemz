package xieao.totemz.Item;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import xieao.totemz.Totemz;

public class ItemGroups {
    public static final ItemGroup MAIN = new ItemGroup(Totemz.MOD_ID) {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(IItems.TOTEM_OF_UNDYING_V);
        }
    };
}
