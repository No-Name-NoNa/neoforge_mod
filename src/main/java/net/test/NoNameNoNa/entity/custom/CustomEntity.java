package net.test.NoNameNoNa.entity.custom;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.test.NoNameNoNa.entity.ModEntity;
import org.jetbrains.annotations.Nullable;

//本文件没用，一开始是想通过生成一个实体来控制血条的显示，感觉不太好就废弃了

public class CustomEntity extends Animal {

    public CustomEntity(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder creativeAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH,15d);
    }

    @Override
    protected void registerGoals() {
    }

    @Override
    public boolean isFood(ItemStack stack){
        return false;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return ModEntity.VOID.get().create(serverLevel);
    }
}
