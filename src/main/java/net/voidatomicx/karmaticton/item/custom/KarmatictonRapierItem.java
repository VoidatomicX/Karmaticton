package net.voidatomicx.karmaticton.item.custom;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.voidatomicx.karmaticton.api.ModComponents;
import net.voidatomicx.karmaticton.api.SonicBoomComponent;

public class KarmatictonRapierItem extends Item {

    public KarmatictonRapierItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack itemStack, Player player, LivingEntity livingEntity, InteractionHand interactionHand) {
        if (!player.level().isClientSide() && !player.getCooldowns().isOnCooldown(itemStack)) {

            ModComponents.RAPIER
                    .get(player.level())
                    .rapierAttack((ServerPlayer) player, livingEntity);

            player.getCooldowns().addCooldown(itemStack, 50);
        }

        return InteractionResult.SUCCESS;
    }

}