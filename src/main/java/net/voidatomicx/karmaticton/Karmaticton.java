package net.voidatomicx.karmaticton;

import net.fabricmc.api.ModInitializer;

import net.minecraft.resources.Identifier;

import net.voidatomicx.karmaticton.item.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Karmaticton implements ModInitializer {
	public static final String MOD_ID = "karmaticton";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.initialize();
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
