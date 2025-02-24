package net.test.NoNameNoNa.item;

import net.minecraft.world.item.*;
import net.test.NoNameNoNa.NoNameNoNa;
import net.minecraft.world.food.FoodProperties;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(NoNameNoNa.MOD_ID);
    /*

    学习时的物品
    public static final DeferredItem<Item> FIRST_FOOD = ITEMS.registerSimpleItem(
            "first_food",
            new Item.Properties().food(new FoodProperties.Builder()
                    .alwaysEdible().nutrition(5).saturationModifier(2f).build()));

    public static final DeferredItem<Item> RAW_FIRST_FOOD = ITEMS.registerSimpleItem(
            "raw_first_food",
            new Item.Properties().food(new FoodProperties.Builder()
                    .alwaysEdible().nutrition(1).saturationModifier(2f).build()));

     */

    public static final DeferredItem<Item> CONTROL_UP = ITEMS.register(
            "upcontroller",
            () -> new Item(new Item.Properties().durability(32)));

    public static final DeferredItem<Item> CONTROL_DOWN = ITEMS.register(
            "downcontroller",
            () -> new Item(new Item.Properties().durability(32)));

    public static final DeferredItem<Item> CONTROL_SHOW = ITEMS.register(
            "showcontroller",
            () -> new Item(new Item.Properties().durability(32)));

    public static final DeferredItem<Item> CONTROL_BUTTON = ITEMS.register(
            "buttoncontroller",
            () -> new Item(new Item.Properties().durability(32)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
