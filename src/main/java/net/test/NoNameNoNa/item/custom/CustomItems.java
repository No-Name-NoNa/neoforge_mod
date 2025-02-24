package net.test.NoNameNoNa.item.custom;

import java.util.Map;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.test.NoNameNoNa.block.ModBlocks;

//本文件也没用QWQ，学习的时候留下来的

public class CustomItems extends Item {

    private static final Map<Block,Block> CUSTOMITEM_MAP =
            Map.of(
                    Blocks.STONE,Blocks.STONE_BRICKS,
                    Blocks.END_STONE,Blocks.END_STONE_BRICKS
                    //Blocks.BEDROCK, ModBlocks.GRAY_BLOCK.get()
            );

    public CustomItems(Properties properties){
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Block clickedBlock = level.getBlockState(context.getClickedPos()).getBlock();

        if(CUSTOMITEM_MAP.containsKey(clickedBlock)) {
            if(!level.isClientSide()) {
                level.setBlockAndUpdate(context.getClickedPos(),CUSTOMITEM_MAP.get(clickedBlock).defaultBlockState());

                context.getItemInHand().hurtAndBreak(1,((ServerLevel)level),context.getPlayer(),
                        item -> context.getPlayer().onEquippedItemBroken(item, EquipmentSlot.MAINHAND));

                level.playSound(null,context.getClickedPos(), SoundEvents.GRINDSTONE_USE, SoundSource.BLOCKS);
            }
        }
        return InteractionResult.SUCCESS;
    }



}
