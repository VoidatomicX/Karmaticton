package net.voidatomicx.karmaticton.item;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ToolMaterial;

public class ModToolMaterials {

    public static final ToolMaterial KARMATICTON = new ToolMaterial(
            BlockTags.INCORRECT_FOR_NETHERITE_TOOL, // Mining level
            4500,                                   // Durability
            15.0F,                                  // Mining speed
            8.0F,                                   // Attack damage bonus
            36,                                     // Enchantability
            ItemTags.NETHERITE_TOOL_MATERIALS       // Repair tag
    );

    private ModToolMaterials() {}
}