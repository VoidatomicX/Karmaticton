package net.voidatomicx.karmaticton.item;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;
import net.voidatomicx.karmaticton.Karmaticton;
import net.voidatomicx.karmaticton.item.custom.KarmatictonBattleaxeItem;
import net.voidatomicx.karmaticton.item.custom.KarmatictonRapierItem;
import net.voidatomicx.karmaticton.item.custom.KarmatictonScytheItem;

import java.util.function.Function;

public class ModItems {

    public static final Item DIMOND_SCYTHE = register("dimond_scythe", Item::new, new Item.Properties().sword(ToolMaterial.DIAMOND, 3.0F, -2.4F));

    public static final Item KARMATICTON_SCYTHE = register("karmaticton_scythe", KarmatictonScytheItem::new, new Item.Properties().sword(ModToolMaterials.KARMATICTON, 3.0F, -2.4F));

    public static final Item KARMATICTON_BATTLEAXE = register("karmaticton_battleaxe", KarmatictonBattleaxeItem::new, new Item.Properties().sword(ModToolMaterials.KARMATICTON, 5.0F, -3.1F));

    public static final Item KARMATICTON_RAPIER = register("karmaticton_rapier", KarmatictonRapierItem::new, new Item.Properties().sword(ModToolMaterials.KARMATICTON, 1.0F, -1F));



    public static <T extends Item> T register(String name, Function<Item.Properties, T> itemFactory, Item.Properties settings) {
        ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(Karmaticton.MOD_ID, name));
        T item = itemFactory.apply(settings.setId(itemKey)); Registry.register(BuiltInRegistries.ITEM, itemKey, item); return item;
    }

    public static void initialize() {
    }
}