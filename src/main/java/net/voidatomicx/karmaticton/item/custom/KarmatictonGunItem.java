package net.voidatomicx.karmaticton.item.custom;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;

public class KarmatictonGunItem extends Item {

    public KarmatictonGunItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        player.swing(hand, true);

        if (!level.isClientSide()) {

            Vec3 start = player.getEyePosition();
            Vec3 look = player.getLookAngle();

            // Maximum range
            Vec3 maxEnd = start.add(look.scale(64));


            // Block ray trace
            BlockHitResult blockHit = level.clip(new ClipContext(
                    start,
                    maxEnd,
                    ClipContext.Block.COLLIDER,
                    ClipContext.Fluid.NONE,
                    player
            ));


            // Stop at block or max range
            Vec3 end = blockHit.getType() == HitResult.Type.BLOCK
                    ? blockHit.getLocation()
                    : maxEnd;


            // Find closest entity near the shot path
            EntityHitResult entityHit = null;

            double closest = Double.MAX_VALUE;


            for (LivingEntity target : level.getEntitiesOfClass(
                    LivingEntity.class,
                    new AABB(start, end).inflate(5.0D),
                    entity -> entity != player && entity.isAlive()
            )) {

                // Make sure entity is in front of player
                Vec3 direction = target.position().subtract(start).normalize();

                double dot = look.dot(direction);

                if (dot > 0.85) {

                    double distance = start.distanceTo(target.position());

                    if (distance < closest) {
                        closest = distance;
                        entityHit = new EntityHitResult(target);
                    }
                }
            }


            // Damage entity
            if (entityHit != null) {

                Entity target = entityHit.getEntity();

                target.hurt(
                        level.damageSources().playerAttack(player),
                        8.0F
                );

                // Stop particles at entity
                end = entityHit.getLocation();
            }


            ServerLevel serverLevel = (ServerLevel) level;


            // Show range
            double range = start.distanceTo(end);

            player.displayClientMessage(
                    Component.literal(
                            "Shot range: " + String.format("%.1f", range) + " blocks"
                    ),
                    true
            );


            // Bullet trail
            for (double d = 0; d <= range; d += 0.5) {

                Vec3 pos = start.add(look.scale(d));

                serverLevel.sendParticles(
                        ParticleTypes.CRIT,
                        pos.x,
                        pos.y,
                        pos.z,
                        1,
                        0,
                        0,
                        0,
                        0
                );
            }


            // Shot end marker
            serverLevel.sendParticles(
                    ParticleTypes.END_ROD,
                    end.x,
                    end.y,
                    end.z,
                    10,
                    0.1,
                    0.1,
                    0.1,
                    0.02
            );


            // Block impact
            if (blockHit.getType() == HitResult.Type.BLOCK) {

                serverLevel.sendParticles(
                        ParticleTypes.SMOKE,
                        end.x,
                        end.y,
                        end.z,
                        10,
                        0.1,
                        0.1,
                        0.1,
                        0.02
                );
            }


            // Cooldown
            player.getCooldowns().addCooldown(stack, 5);
        }

        return InteractionResult.SUCCESS;
    }
}