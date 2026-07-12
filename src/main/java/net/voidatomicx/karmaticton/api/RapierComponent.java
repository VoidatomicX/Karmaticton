package net.voidatomicx.karmaticton.api;

import it.unimi.dsi.fastutil.Pair;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;

import java.util.ArrayList;
import java.util.List;

public class RapierComponent implements ServerTickingComponent {

    private final List<Attack> queuedAttacks = new ArrayList<>();

    public void rapierAttack(ServerPlayer player, Entity target) { // FUTURE VOID THIS IS WHAT U NEED TO CALL
        queuedAttacks.add(new Attack(player,target,10)); // 10 is the number of attacks
    }

    @Override
    public void serverTick() {
        queuedAttacks.removeIf((attack -> attack.count <= 0));
        for (Attack attack : queuedAttacks) {
            if (attack.count % 5 == 0)
                attack(attack.player, attack.target);
            attack.decrement();
        }
    }

    private void attack(ServerPlayer player, Entity target) {
        var level = player.level();

        player.swing(player.getUsedItemHand());
        level.sendParticles(
                ParticleTypes.SWEEP_ATTACK,
                target.getX(),
                target.getEyePosition(0.8f).y,
                target.getZ(),
                1,
                0,
                0,
                0,
                0
        );
        level.playSound(
                null,
                player.blockPosition(),
                SoundEvents.PLAYER_ATTACK_SWEEP,
                SoundSource.PLAYERS,
                1.0F,
                1F
        );

        target.hurt(player.damageSources().playerAttack(player),8f);
    }

    @Override
    public void readData(ValueInput valueInput) {

    }

    @Override
    public void writeData(ValueOutput valueOutput) {

    }

    private static class Attack {
        public final ServerPlayer player;
        public final Entity target;
        public int count;

        public Attack(ServerPlayer player, Entity target, int count) {
            this.player = player;
            this.target = target;
            this.count = count*5;
        }

        public Attack decrement() {
            count--;
            return this;
        }
    }
}
