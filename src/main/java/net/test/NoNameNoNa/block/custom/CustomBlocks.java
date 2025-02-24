package net.test.NoNameNoNa.block.custom;


import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.BossEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.test.NoNameNoNa.Bar.LightingBar;
import net.test.NoNameNoNa.item.ModItems;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class CustomBlocks extends Block {

    public LightingBar lightingBar;
    public static final IntegerProperty CHARGE = IntegerProperty.create("charge",0,15);

    private static final Component RAID_NAME_COMPONENT = Component.translatable("lightinglevel");
    private final ServerBossEvent raidEvent = new ServerBossEvent(RAID_NAME_COMPONENT, BossEvent.BossBarColor.RED, BossEvent.BossBarOverlay.PROGRESS);
    /*
    更细节的碰撞箱，本来应该是使用的，这样可以更好的右键交互到更准确的位置，不过考核上写的正方形，就算了

    public static final VoxelShape SHAPE_BASE = Block.box(5.0, 0.0, 5.0, 11.0, 1.0, 11.0);
    public static final VoxelShape SHAPE_BUTTON1 = Block.box(5.0, 1.0, 5.0, 6.0, 2.0, 6.0);
    public static final VoxelShape SHAPE_BUTTON2 = Block.box(5.0, 1.0, 10.0, 6.0, 2.0, 11.0);
    public static final VoxelShape SHAPE_BUTTON3 = Block.box(10.0, 1.0, 5.0, 11.0, 2.0, 6.0);
    public static final VoxelShape SHAPE_BUTTON4 = Block.box(10.0, 1.0, 10.0, 11.0, 2.0, 11.0);
    public static final VoxelShape SHAPE_BODY = Block.box(7.0, 1.0, 7.0, 9.0, 3.0, 9.0);
    public static final VoxelShape SHAPE_HEAD = Block.box(6.0, 3.0, 6.0, 10.0, 6.0, 10.0);
    public static final VoxelShape SHAPE_COMMON = Shapes.or(SHAPE_BASE, SHAPE_BUTTON1,SHAPE_BUTTON2,SHAPE_BUTTON3,SHAPE_BUTTON4,SHAPE_BODY,SHAPE_HEAD);
     */
    public static final VoxelShape SHAPE_COMMON = Block.box(5.0, 0.0, 5.0, 11.0, 6.0, 11.0);
    public static final MapCodec<CustomBlocks> CODEC = simpleCodec(CustomBlocks::new);

    public CustomBlocks(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(CHARGE, 0));
        /*
        废弃
        bossbar = new CustomBossEvent();
        bossbar.setColor(BossEvent.BossBarColor.PINK);
        bossbar.setOverlay(BossEvent.BossBarOverlay.PROGRESS);
        bossbar.setName(Component.translatable());
        bossbar.setProgress(0f);
         */
    }


    //使用调试棒不会更新血条，我没有每tick更新，正常情况也不会用调试棒QWQ

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {

        Vec3 hitVec = hitResult.getLocation().subtract(pos.getX(), pos.getY(), pos.getZ());

        if(!level.isClientSide()){
            ServerLevel serverLevel = (ServerLevel) level;

            if(lightingBar == null)
                lightingBar = new LightingBar(serverLevel,pos);

            int currentState = state.getValue(CHARGE);

            if(isUp(stack)) {
                if(currentState<=10) {
                    level.setBlockAndUpdate(pos, state.setValue(CHARGE, currentState + 5));
                    lightingBar.updateBossbar(currentState+5);
                }
                else {
                    level.setBlockAndUpdate(pos, state.setValue(CHARGE, 15));
                    lightingBar.updateBossbar(15);
                }

                if(currentState==15)return ItemInteractionResult.FAIL;

                return ItemInteractionResult.SUCCESS;
            }
            if(isDown(stack)) {
                if(currentState>=5) {
                    level.setBlockAndUpdate(pos, state.setValue(CHARGE, currentState - 5));
                    lightingBar.updateBossbar(currentState - 5);
                }
                else {
                    level.setBlockAndUpdate(pos, state.setValue(CHARGE, 0));
                    lightingBar.updateBossbar(0);
                }

                if(currentState==0)return ItemInteractionResult.FAIL;

                return ItemInteractionResult.SUCCESS;
            }
            if(isShown(stack)) {

                //没什么用了，这是从DebugStick（调试棒）借过来的
                //Holder<Block> holder = state.getBlockHolder();
                //message(player, Component.translatable("光照等级:"+String.valueOf(currentState), holder.getRegisteredName()));
                lightingBar.toggleVisibility();
                lightingBar.updateBossbar(state.getValue(CHARGE));

                return ItemInteractionResult.SUCCESS;
            }
            if(isButton(stack)) {

                //借鉴于Fabric : carpet mod中的flipincactus（仙人掌扳手）翻转楼梯

                if(hitVec.x >= 0.3125 && hitVec.x <= 0.375 && hitVec.y >= 0.0625 && hitVec.y <= 0.125 && hitVec.z >= 0.3125 && hitVec.z <= 0.375){
                    level.setBlockAndUpdate(pos, state.setValue(CHARGE, 3));

                    lightingBar.updateBossbar(3);

                    return ItemInteractionResult.SUCCESS;
                }

                if(hitVec.x >= 0.3125 && hitVec.x <= 0.375 && hitVec.y >= 0.0625 && hitVec.y <= 0.125 && hitVec.z >= 0.625 && hitVec.z <= 0.6875){
                    level.setBlockAndUpdate(pos, state.setValue(CHARGE, 6));

                    lightingBar.updateBossbar(6);

                    return ItemInteractionResult.SUCCESS;
                }

                if(hitVec.x >= 0.625 && hitVec.x <= 0.6875 && hitVec.y >= 0.0625 && hitVec.y <= 0.125 && hitVec.z >= 0.625 && hitVec.z <= 0.6875){
                    level.setBlockAndUpdate(pos, state.setValue(CHARGE, 9));

                    lightingBar.updateBossbar(9);

                    return ItemInteractionResult.SUCCESS;
                }

                if(hitVec.x >= 0.625 && hitVec.x <= 0.6875 && hitVec.y >= 0.0625 && hitVec.y <= 0.125 && hitVec.z >= 0.3125 && hitVec.z <= 0.375){
                    level.setBlockAndUpdate(pos, state.setValue(CHARGE, 12));

                    lightingBar.updateBossbar(12);

                    return ItemInteractionResult.SUCCESS;
                }
            }
        }
        return ItemInteractionResult.FAIL;
    }


    private static boolean isUp(ItemStack stack) {return stack.is(ModItems.CONTROL_UP.get());}
    private static boolean isDown(ItemStack stack) {return stack.is(ModItems.CONTROL_DOWN.get());}
    private static boolean isShown(ItemStack stack) {return stack.is(ModItems.CONTROL_SHOW.get());}
    private static boolean isButton(ItemStack stack) {return stack.is(ModItems.CONTROL_BUTTON.get());}

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(CHARGE);
    }


    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE_COMMON;
    }

    @Override
    protected MapCodec<? extends Block> codec() {
        return super.codec();
    }


    /*调试棒借过来的，也没用了

    private static void message(Player player, Component messageComponent) {
        ((ServerPlayer)player).sendSystemMessage(messageComponent, true);
    }

     */


}
