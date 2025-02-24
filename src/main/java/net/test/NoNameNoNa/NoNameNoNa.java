package net.test.NoNameNoNa;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.test.NoNameNoNa.block.ModBlocks;
import net.test.NoNameNoNa.entity.ModEntity;
import net.test.NoNameNoNa.item.ModItems;
import net.test.NoNameNoNa.ui.ModTabs;


@Mod(NoNameNoNa.MOD_ID)
public class NoNameNoNa
{
    public static final String MOD_ID = "testid";  //虽然名字不大好，但就这样了
    public NoNameNoNa(IEventBus modEventBus, ModContainer modContainer) {
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModEntity.register(modEventBus);
        ModTabs.CREATIVE_MODE_TABS.register(modEventBus);
        modEventBus.addListener(ModTabs::addCreative);
    }
}
