package net.test.NoNameNoNa.ui;

import net.test.NoNameNoNa.NoNameNoNa;
import net.test.NoNameNoNa.block.ModBlocks;
import net.test.NoNameNoNa.item.ModItems;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, NoNameNoNa.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TEST_TAB = CREATIVE_MODE_TABS.register("testid", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.testid")) //The language key for the title of your CreativeModeTab
            .withTabsBefore(CreativeModeTabs.BUILDING_BLOCKS)
            .icon(() -> ModItems.CONTROL_SHOW.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(ModItems.CONTROL_UP.get());
                output.accept(ModItems.CONTROL_DOWN.get());
                output.accept(ModBlocks.LIGHT_BLOCK.get());
                output.accept(ModItems.CONTROL_SHOW.get());
                output.accept(ModItems.CONTROL_BUTTON.get());
            }).build());
    public static void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            event.accept(ModBlocks.LIGHT_BLOCK);
        }

        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(ModItems.CONTROL_UP);
            event.accept(ModItems.CONTROL_DOWN);
            event.accept(ModItems.CONTROL_SHOW);
            event.accept(ModItems.CONTROL_BUTTON);
        }
    }
}
