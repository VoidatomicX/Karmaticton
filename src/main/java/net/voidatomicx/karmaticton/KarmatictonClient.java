package net.voidatomicx.karmaticton;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.impl.client.rendering.EntityRendererRegistryImpl;
import net.voidatomicx.karmaticton.entity.ModEntitys;
import net.voidatomicx.karmaticton.entity.render.KarmatictonScytheRenderer;

public class KarmatictonClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistryImpl.register(ModEntitys.KarmatictonScythe, KarmatictonScytheRenderer::new);
    }
}
