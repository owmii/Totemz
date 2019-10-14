package xieao.totemz.Item;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xieao.lib.item.IItemBase;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class IItems {
    public static final List<Item> ITEMS = new ArrayList<>();
    public static final Item TOTEM_OF_UNDYING_II;
    public static final Item TOTEM_OF_UNDYING_III;
    public static final Item TOTEM_OF_UNDYING_IV;
    public static final Item TOTEM_OF_UNDYING_V;

    static {
        TOTEM_OF_UNDYING_II = register("totem_of_undying_2", new TotemItem(new Item.Properties().group(ItemGroups.MAIN)));
        TOTEM_OF_UNDYING_III = register("totem_of_undying_3", new TotemItem(new Item.Properties().group(ItemGroups.MAIN)));
        TOTEM_OF_UNDYING_IV = register("totem_of_undying_4", new TotemItem(new Item.Properties().group(ItemGroups.MAIN)));
        TOTEM_OF_UNDYING_V = register("totem_of_undying_5", new TotemItem(new Item.Properties().group(ItemGroups.MAIN)));
    }

    static <T extends Item & IItemBase> T register(String name, T item) {
        item.setRegistryName(name);
        ITEMS.add(item);
        return item;
    }

    @SubscribeEvent
    public static void onRegistry(RegistryEvent.Register<Item> event) {
        ITEMS.forEach(item -> event.getRegistry().register(item));
    }
}
