package net.voidatomicx.karmaticton.item.custom;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
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

public class KarmatictonRapierItem extends Item {

    public KarmatictonRapierItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!level.isClientSide()) {
            fireSonicBoom(level, player);
            player.getCooldowns().addCooldown(stack, 10);
        }

        player.swing(hand, true);
        return InteractionResult.SUCCESS;
    }

    private void fireSonicBoom(Level level, Player player) {

        Vec3 start = player.getEyePosition();
        Vec3 direction = player.getLookAngle().normalize();

        double maxDistance = 5.0;

        for (double d = 0; d <= maxDistance; d += 0.5) {

            Vec3 pos = start.add(direction.scale(d));

            ((ServerLevel) level).sendParticles(
                    ParticleTypes.SONIC_BOOM,
                    pos.x,
                    pos.y,
                    pos.z,
                    1,
                    0,
                    0,
                    0,
                    0
            );

            AABB box = new AABB(pos.x - 0.5, pos.y - 0.5, pos.z - 0.5,
                    pos.x + 0.5, pos.y + 0.5, pos.z + 0.5);

            for (LivingEntity target : level.getEntitiesOfClass(LivingEntity.class, box)) {
                if (target == player)
                    continue;

                target.hurt(level.damageSources().sonicBoom(player), 4.0F);

            }
        }

        level.playSound(
                null,
                player.blockPosition(),
                SoundEvents.WARDEN_SONIC_BOOM,
                SoundSource.PLAYERS,
                2.0F,
                1.8F
        );
    }
}