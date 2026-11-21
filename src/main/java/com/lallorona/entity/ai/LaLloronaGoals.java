package com.lallorona.entity.ai;

import com.lallorona.entity.LaLloronaEntity;
import com.lallorona.registry.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class LaLloronaGoals {

    public static class LiberationGoal extends Goal {
        private final LaLloronaEntity mob;
        public LiberationGoal(LaLloronaEntity mob) {
            this.mob = mob;
            this.setFlags(EnumSet.allOf(Flag.class));
        }
        @Override public boolean canUse() { return mob.isLiberated(); }
        @Override public void tick() {
            mob.getNavigation().stop();
            mob.setAnimState(LaLloronaEntity.STATE_LIBERATION, 20);
        }
    }

    public static class StalkAndVanishGoal extends Goal {
        private final LaLloronaEntity mob;
        private Player target;
        private int stareTicks = 0;

        public StalkAndVanishGoal(LaLloronaEntity mob) {
            this.mob = mob;
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            if (mob.isLiberated()) return false;
            this.target = mob.level().getNearestPlayer(mob, 52.0D);
            return this.target != null && !this.target.isCreative() && !this.target.isSpectator();
        }

        @Override
        public void tick() {
            if (target == null) return;
            double dist = mob.distanceTo(target);
            mob.getLookControl().setLookAt(target, 18.0F, 18.0F);

            if (mob.getAggressionPhase() <= 2) {
                if (dist < 10.0D) {
                    Vec3 away = mob.position().subtract(target.position()).normalize().scale(22.0D);
                    Vec3 dest = target.position().add(away.x, 0, away.z);
                    mob.vanishIntoMist(dest);
                    stareTicks = 0;
                    return;
                }
                if (dist <= 26.0D) {
                    mob.getNavigation().stop();
                    stareTicks++;
                    if (stareTicks < 18) {
                        mob.setAnimState(LaLloronaEntity.STATE_HEAD_TURN, 10);
                    } else {
                        mob.setAnimState(LaLloronaEntity.STATE_STARE, 10);
                    }
                } else {
                    mob.setAnimState(LaLloronaEntity.STATE_WALK_SLOW, 10);
                    mob.getNavigation().moveTo(target, 0.72D);
                }
            } else {
                mob.setAnimState(LaLloronaEntity.STATE_WALK_SLOW, 10);
                mob.getNavigation().moveTo(target, 0.9D);
            }
        }
    }

    public static class RelentlessChaseGoal extends Goal {
        private final LaLloronaEntity mob;
        private Player target;
        private int meleeCooldown = 0;

        public RelentlessChaseGoal(LaLloronaEntity mob) {
            this.mob = mob;
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            if (mob.isLiberated() || mob.getAggressionPhase() < 3) return false;
            this.target = mob.level().getNearestPlayer(mob, 30.0D);
            return this.target != null && !this.target.isCreative() && !this.target.isSpectator();
        }

        @Override
        public void start() {
            mob.level().playSound(null, mob.blockPosition(),
                    ModSounds.MUSICA_PERSECUCION.get(), SoundSource.HOSTILE, 1.8F, 1.0F);
        }

        @Override
        public void tick() {
            if (target == null) return;
            if (meleeCooldown > 0) meleeCooldown--;

            mob.getLookControl().setLookAt(target, 30.0F, 30.0F);
            double speed = 1.15D + (mob.getAggressionPhase() * 0.08D);
            mob.getNavigation().moveTo(target, speed);
            if (!mob.isStateLocked()) {
                mob.setAnimState(LaLloronaEntity.STATE_CHASE, 8);
            }

            if (mob.distanceTo(target) <= 3.1D && meleeCooldown <= 0) {
                mob.performTelegraphedAttack(target);
                meleeCooldown = 36;
            }
        }
    }

    public static class TelegraphedWailGoal extends Goal {
        private final LaLloronaEntity mob;
        private Player target;
        private int windup = 0;

        public TelegraphedWailGoal(LaLloronaEntity mob) {
            this.mob = mob;
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            if (mob.isLiberated() || mob.getAggressionPhase() < 2 || !mob.canWail()) return false;
            this.target = mob.level().getNearestPlayer(mob, 15.0D);
            return this.target != null && !this.target.isCreative() && mob.hasLineOfSight(this.target);
        }

        @Override
        public void start() {
            this.windup = 25;
            mob.getNavigation().stop();
            mob.setAnimState(LaLloronaEntity.STATE_WAIL, 42);
            mob.level().playSound(null, mob.blockPosition(),
                    ModSounds.SUSURROS_HABITACION.get(), SoundSource.HOSTILE, 1.6F, 1.35F);
            if (target != null) {
                target.displayClientMessage(Component.translatable("warning.lallorona.wail"), true);
            }
        }

        @Override
        public boolean canContinueToUse() {
            return windup > 0 && target != null && target.isAlive() && !mob.isLiberated();
        }

        @Override
        public void tick() {
            mob.getNavigation().stop();
            if (target != null) mob.getLookControl().setLookAt(target, 30.0F, 30.0F);
            windup--;

            if (mob.level() instanceof ServerLevel sl) {
                sl.sendParticles(ParticleTypes.SOUL,
                        mob.getX(), mob.getY() + 3.2, mob.getZ(),
                        5, 0.5, 0.4, 0.5, 0.06);
            }

            if (windup == 0 && target != null) {
                mob.level().playSound(null, mob.blockPosition(),
                        ModSounds.GRITO_SOBRENATURAL.get(), SoundSource.HOSTILE, 2.5F, 1.0F);
                if (mob.distanceTo(target) <= 14.0D && mob.hasLineOfSight(target)) {
                    target.hurt(mob.damageSources().magic(), 5.0F);
                    target.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 110, 0));
                    target.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 90, 0));
                }
                mob.resetWailCooldown();
            }
        }
    }

    public static class SpectralWaterSurgeGoal extends Goal {
        private final LaLloronaEntity mob;
        private Player target;
        private int chargeTicks = 0;

        public SpectralWaterSurgeGoal(LaLloronaEntity mob) {
            this.mob = mob;
            this.setFlags(EnumSet.of(Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            if (mob.isLiberated() || mob.getAggressionPhase() < 3 || !mob.canWaterSurge()) return false;
            this.target = mob.level().getNearestPlayer(mob, 18.0D);
            if (this.target == null || this.target.isCreative()) return false;
            return this.target.isInWaterRainOrBubble() || mob.isInWater();
        }

        @Override
        public void start() {
            this.chargeTicks = 22;
            mob.setAnimState(LaLloronaEntity.STATE_STARE, 28);
            mob.level().playSound(null, target.blockPosition(),
                    ModSounds.SONIDO_AGUA.get(), SoundSource.HOSTILE, 1.8F, 0.8F);
            target.displayClientMessage(Component.translatable("warning.lallorona.water_surge"), true);
        }

        @Override
        public boolean canContinueToUse() {
            return chargeTicks > 0 && target != null && !mob.isLiberated();
        }

        @Override
        public void tick() {
            chargeTicks--;
            if (mob.level() instanceof ServerLevel sl && target != null) {
                sl.sendParticles(ParticleTypes.SPLASH,
                        target.getX(), target.getY() + 0.5, target.getZ(),
                        12, 0.9, 0.4, 0.9, 0.15);
            }
            if (chargeTicks == 0 && target != null) {
                if (target.isInWaterRainOrBubble() || mob.distanceTo(target) < 12.0D) {
                    Vec3 pull = mob.position().subtract(target.position()).normalize().scale(0.65);
                    target.push(pull.x, 0.18, pull.z);
                    target.hurtMarked = true;
                    target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 80, 1));
                    target.hurt(mob.damageSources().drown(), 4.0F);
                }
                mob.resetWaterSurgeCooldown();
            }
        }
    }

    public static class DarkAmbushGoal extends Goal {
        private final LaLloronaEntity mob;
        private Player target;
        private int telegraphTicks = 0;

        public DarkAmbushGoal(LaLloronaEntity mob) {
            this.mob = mob;
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            if (mob.isLiberated() || mob.getAggressionPhase() < 2 || !mob.canAmbush()) return false;
            this.target = mob.level().getNearestPlayer(mob, 32.0D);
            if (this.target == null || this.target.isCreative() || mob.distanceTo(this.target) < 9.0D) return false;
            BlockPos pPos = this.target.blockPosition();
            int blockLight = mob.level().getBrightness(LightLayer.BLOCK, pPos);
            return blockLight <= 5;
        }

        @Override
        public void start() {
            this.telegraphTicks = 24;
            mob.level().playSound(null, target.blockPosition(),
                    ModSounds.PUERTA_CHIRRIDO.get(), SoundSource.HOSTILE, 1.3F, 0.85F);
            target.displayClientMessage(Component.translatable("warning.lallorona.ambush"), true);
        }

        @Override
        public boolean canContinueToUse() {
            return telegraphTicks > 0 && target != null && !mob.isLiberated();
        }

        @Override
        public void tick() {
            telegraphTicks--;
            if (telegraphTicks == 0 && target != null) {
                Vec3 look = target.getLookAngle();
                Vec3 front = target.position().add(look.x * 6.5, 0, look.z * 6.5);
                BlockPos bp = BlockPos.containing(front.x, target.getY(), front.z);
                if (mob.level().isEmptyBlock(bp) && mob.level().isEmptyBlock(bp.above(2))) {
                    mob.vanishIntoMist(new Vec3(bp.getX() + 0.5, bp.getY(), bp.getZ() + 0.5));
                    mob.level().playSound(null, bp,
                            ModSounds.GOLPE_REPENTINO.get(), SoundSource.HOSTILE, 1.7F, 0.9F);
                    target.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 45, 0));
                }
                mob.resetAmbushCooldown();
            }
        }
    }
}
