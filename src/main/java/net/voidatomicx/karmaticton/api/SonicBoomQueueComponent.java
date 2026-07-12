package net.voidatomicx.karmaticton.api;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.ladysnake.cca.api.v3.component.Component;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SonicBoomQueueComponent implements ServerTickingComponent {

    private final List<QueuedSonicBoom> queuedBooms = new ArrayList<>();

    public void addSonicBoom(ServerPlayer player, int delay) {
        queuedBooms.add(new QueuedSonicBoom(player, delay));
    }

    @Override
    public void serverTick() {
        Iterator<QueuedSonicBoom> iterator = queuedBooms.iterator();

        while (iterator.hasNext()) {
            QueuedSonicBoom boom = iterator.next();

            boom.timer--;

            if (boom.timer <= 0) {
                fireSonicBoom(boom.player.level(), boom.player);

                iterator.remove();
            }
        }
    }


    private void fireSonicBoom(ServerLevel level, ServerPlayer player) {

        Vec3 start = player.getEyePosition();
        Vec3 direction = player.getLookAngle().normalize();

        double maxDistance = 5.0;

        for (double d = 0; d <= maxDistance; d += 0.5) {

            Vec3 pos = start.add(direction.scale(d));

             level.sendParticles(
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


            AABB box = new AABB(
                    pos.x - 0.5,
                    pos.y - 0.5,
                    pos.z - 0.5,
                    pos.x + 0.5,
                    pos.y + 0.5,
                    pos.z + 0.5
            );


            for (LivingEntity target : level.getEntitiesOfClass(LivingEntity.class, box)) {

                if (target == player)
                    continue;

                target.hurt(
                        level.damageSources().sonicBoom(player),
                        4.0F
                );
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

    @Override
    public void readData(ValueInput valueInput) {

    }

    @Override
    public void writeData(ValueOutput valueOutput) {

    }


    private static class QueuedSonicBoom {

        private final ServerPlayer player;
        private int timer;

        public QueuedSonicBoom(ServerPlayer player, int timer) {
            this.player = player;
            this.timer = timer;
        }
    }
}