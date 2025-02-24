package net.test.NoNameNoNa.block;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.BlockItem;
import net.test.NoNameNoNa.NoNameNoNa;
import net.test.NoNameNoNa.block.custom.CustomBlocks;
import net.test.NoNameNoNa.item.ModItems;

import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(NoNameNoNa.MOD_ID);

    /* 学习时的残留
    public static final DeferredBlock<Block> GRAY_BLOCK = registerBlock("gray_block",
            ()-> new Block(BlockBehaviour.Properties.of().
                    strength(2f).requiresCorrectToolForDrops().sound(SoundType.AMETHYST)));

     */

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block){
        DeferredBlock<T> toReturn = BLOCKS.register(name,block);
        registerBlockItem(name,toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block){
        ModItems.ITEMS.register(name, ()-> new BlockItem(block.get(),new Item.Properties()));
    }

    public static final DeferredBlock<Block> LIGHT_BLOCK = registerBlock("lightingblock",
            () -> new CustomBlocks(BlockBehaviour.Properties.of().lightLevel(state -> state.getValue(CustomBlocks.CHARGE)).strength(2f).requiresCorrectToolForDrops()));

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
