package net.voidatomicx.karmaticton.api;

import net.minecraft.resources.Identifier;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistryV3;
import org.ladysnake.cca.api.v3.world.WorldComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.world.WorldComponentInitializer;

public class ModComponents implements WorldComponentInitializer {

    public static final ComponentKey<RapierComponent> RAPIER =
            ComponentRegistryV3.INSTANCE.getOrCreate(
                    Identifier.fromNamespaceAndPath("karmaticton", "rapier"),
                    RapierComponent.class
            );

    public static final ComponentKey<SonicBoomComponent> SONIC_BOOM =
            ComponentRegistryV3.INSTANCE.getOrCreate(
                    Identifier.fromNamespaceAndPath("karmaticton", "sonic_boom"),
                    SonicBoomComponent.class
            );

    public static final ComponentKey<SonicBoomQueueComponent> SONIC_BOOM_QUEUE =
            ComponentRegistryV3.INSTANCE.getOrCreate(
                    Identifier.fromNamespaceAndPath("karmaticton", "sonic_boom_queue"),
                    SonicBoomQueueComponent.class
            );

    @Override
    public void registerWorldComponentFactories(WorldComponentFactoryRegistry registry) {
        registry.register(RAPIER, world -> new RapierComponent());
        registry.register(SONIC_BOOM_QUEUE, world -> new SonicBoomQueueComponent());
    }
}