package net.voidatomicx.karmaticton.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.voidatomicx.karmaticton.Karmaticton;
import net.voidatomicx.karmaticton.entity.custom.KarmatictonScytheEntity;

public class ModEntitys {
    public static final EntityType<KarmatictonScytheEntity> KarmatictonScythe = register(
            "karmaticton_scythe_entity",
            EntityType.Builder.<KarmatictonScytheEntity>of(KarmatictonScytheEntity::new, MobCategory.MISC)
                    .sized(0.75f, 1.75f)
    );

    private static <T extends Entity> EntityType<T> register(String name, EntityType.Builder<T> builder) {
        ResourceKey<EntityType<?>> key = ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(Karmaticton.MOD_ID, name));
        return Registry.register(BuiltInRegistries.ENTITY_TYPE, key, builder.build(key));
    }

    public static void registerModEntityTypes() {
        Karmaticton.LOGGER.info("Registering EntityTypes for " + Karmaticton.MOD_ID);
    }

    public static void registerAttributes() {
    }
}

