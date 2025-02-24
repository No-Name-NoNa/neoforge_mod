package net.test.NoNameNoNa.entity;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.test.NoNameNoNa.NoNameNoNa;
import net.test.NoNameNoNa.entity.custom.CustomEntity;

import java.util.function.Supplier;

//本文件没用

public class ModEntity {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, NoNameNoNa.MOD_ID);

    public static final Supplier<EntityType<CustomEntity>> VOID =
            ENTITY_TYPES.register("void", ()-> EntityType.Builder.of(CustomEntity::new, MobCategory.CREATURE)
                    .sized(0f,0f).build("void"));

    public static void register(IEventBus eventBus){
        ENTITY_TYPES.register(eventBus);
    }
}
