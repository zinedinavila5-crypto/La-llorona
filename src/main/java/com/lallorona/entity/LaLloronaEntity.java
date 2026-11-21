package com.lallorona.entity;

import com.lallorona.entity.ai.LaLloronaGoals;
import com.lallorona.registry.ModSounds;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

public class LaLloronaEntity extends PathfinderMob implements GeoEntity {
    public static final int STATE_FLOAT_STALK = 0;
    public static final int STATE_WALK_SLOW = 1;
    public static final int STATE_CHASE = 2;
    public static final int STATE_STARE = 3;
    public static final int STATE_HEAD_TURN = 4;
    public static final int STATE_APPEAR = 5;
    public static final int STATE_ATTACK = 6;
    public static final int STATE_WAIL = 7;
    public static final int STATE_VANISH = 8;
    public static final int STATE_LIBERATION = 9;

    private static final EntityDataAccessor<Integer> DATA_ANIM_STATE =
            SynchedEntityData.defineId(LaLloronaEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_AGGRESSION_PHASE =
            SynchedEntityData.defineId(LaLloronaEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> DATA_LIBERATED =
            SynchedEntityData.defineId(LaLloronaEntity.class, EntityDataSerializers.BOOLEAN);

    private final AnimatableInstanceCache animCache = GeckoLibUtil.createInstanceCache(this);

    private int stateTimer = 0;
    private int liberationTicks = 0;
    private int wailCooldown = 140;
    private int ambushCooldown = 220;
    private int waterSurgeCooldown = 180;
    private int ambientLamentTicker = 100;

    public LaLloronaEntity(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level);
        this.setPersistenceRequired();
        this.xpReward = 0;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return PathfinderMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 1000.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.27D)
                .add(Attributes.ATTACK_DAMAGE, 7.0D)
                .add(Attributes.FOLLOW_RANGE, 64.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_ANIM_STATE, STATE_FLOAT_STALK);
        this.entityData.define(DATA_AGGRESSION_PHASE, 1);
        this.entityData.define(DATA_LIBERATED, false);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new LaLloronaGoals.LiberationGoal(this));
        this.goalSelector.addGoal(2, new LaLloronaGoals.TelegraphedWailGoal(this));
        this.goalSelector.addGoal(3, new LaLloronaGoals.SpectralWaterSurgeGoal(this));
        this.goalSelector.addGoal(4, new LaLloronaGoals.DarkAmbushGoal(this));
        this.goalSelector.addGoal(5, new LaLloronaGoals.RelentlessChaseGoal(this));
        this.goalSelector.addGoal(6, new LaLloronaGoals.StalkAndVanishGoal(this));
    }

    // Inmortal frente a TODO combate (espadas, arcos, explosiones, fuego, pociones, caídas).
    // Solo permite limpieza interna de reinicio mediante comando /kill (GENERIC_KILL) para evitar duplicados al reiniciar el Acto I.
    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.is(DamageTypes.GENERIC_KILL)) {
            this.discard();
            return false;
        }
        if (this.isLiberated()) {
            return false;
        }
        if (!this.level().isClientSide) {
            this.setHealth(this.getMaxHealth());
            if (source.getEntity() instanceof Player player) {
                this.level().playSound(null, this.blockPosition(),
                        ModSounds.SUSURROS_HABITACION.get(), SoundSource.HOSTILE, 1.1F, 0.9F);
                ((ServerLevel) this.level()).sendParticles(
                        ParticleTypes.SOUL, this.getX(), this.getY() + 2.2, this.getZ(),
                        14, 0.5, 0.9, 0.5, 0.04);
                player.displayClientMessage(
                        Component.translatable("message.lallorona.cannot_kill"), true);
            }
        }
        return false;
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return !source.is(DamageTypes.GENERIC_KILL);
    }

    @Override
    public void die(DamageSource cause) {
        this.setHealth(this.getMaxHealth());
    }

    @Override
    public boolean removeWhenFarAway(double distance) {
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        if (stateTimer > 0) stateTimer--;
        if (wailCooldown > 0) wailCooldown--;
        if (ambushCooldown > 0) ambushCooldown--;
        if (waterSurgeCooldown > 0) waterSurgeCooldown--;

        if (this.level().isClientSide) {
            spawnAtmosphericParticles();
        } else {
            if (this.isLiberated()) {
                handleLiberationSequence();
                return;
            }
            if (--ambientLamentTicker <= 0) {
                ambientLamentTicker = 180 + this.random.nextInt(160);
                SoundEvent sound = this.isInWater()
                        ? ModSounds.LLANTO_RIO.get()
                        : ModSounds.LAMENTO_LEJANO.get();
                this.level().playSound(null, this.blockPosition(),
                        sound, SoundSource.HOSTILE, 2.2F, 0.92F + this.random.nextFloat() * 0.14F);
            }
        }
    }

    private void spawnAtmosphericParticles() {
        if (this.isLiberated()) {
            if (this.tickCount % 2 == 0) {
                this.level().addParticle(ParticleTypes.END_ROD,
                        this.getX() + (this.random.nextDouble() - 0.5) * 1.4,
                        this.getY() + this.random.nextDouble() * 3.8,
                        this.getZ() + (this.random.nextDouble() - 0.5) * 1.4,
                        0.0, 0.08, 0.0);
                this.level().addParticle(ParticleTypes.SOUL,
                        this.getX() + (this.random.nextDouble() - 0.5) * 1.0,
                        this.getY() + 1.0 + this.random.nextDouble() * 2.4,
                        this.getZ() + (this.random.nextDouble() - 0.5) * 1.0,
                        0.0, 0.05, 0.0);
            }
        } else {
            if (this.tickCount % 3 == 0) {
                this.level().addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE,
                        this.getX() + (this.random.nextDouble() - 0.5) * 0.9,
                        this.getY() + 0.25,
                        this.getZ() + (this.random.nextDouble() - 0.5) * 0.9,
                        0.0, 0.02, 0.0);
            }
            if (this.tickCount % 5 == 0) {
                this.level().addParticle(ParticleTypes.FALLING_WATER,
                        this.getX() + (this.random.nextDouble() - 0.5) * 0.8,
                        this.getY() + 0.8 + this.random.nextDouble() * 2.2,
                        this.getZ() + (this.random.nextDouble() - 0.5) * 0.8,
                        0.0, -0.04, 0.0);
            }
        }
    }

    private void handleLiberationSequence() {
        this.getNavigation().stop();
        this.setAnimState(STATE_LIBERATION, 40);
        liberationTicks++;

        if (liberationTicks == 1) {
            this.level().playSound(null, this.blockPosition(),
                    ModSounds.MUSICA_RITUAL_LIBERACION.get(), SoundSource.RECORDS, 3.0F, 1.0F);
        }

        this.setDeltaMovement(new Vec3(0.0, 0.025, 0.0));
        if (this.level() instanceof ServerLevel sl && liberationTicks % 4 == 0) {
            sl.sendParticles(ParticleTypes.END_ROD,
                    this.getX(), this.getY() + 2.0, this.getZ(),
                    10, 0.7, 1.4, 0.7, 0.04);
        }

        if (liberationTicks >= 160) {
            if (this.level() instanceof ServerLevel sl) {
                sl.sendParticles(ParticleTypes.CLOUD,
                        this.getX(), this.getY() + 2.0, this.getZ(),
                        45, 1.0, 1.8, 1.0, 0.05);
            }
            this.discard();
        }
    }

    public boolean performTelegraphedAttack(Player target) {
        this.setAnimState(STATE_ATTACK, 24);
        boolean hit = target.hurt(this.damageSources().mobAttack(this), 6.5F + this.getAggressionPhase());
        if (hit) {
            target.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 70, 0));
            target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 50, 1));
            this.level().playSound(null, this.blockPosition(),
                    ModSounds.GOLPE_REPENTINO.get(), SoundSource.HOSTILE, 1.4F, 0.9F);
        }
        return hit;
    }

    public void vanishIntoMist(Vec3 newPos) {
        if (this.level() instanceof ServerLevel sl) {
            sl.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE,
                    this.getX(), this.getY() + 1.8, this.getZ(),
                    28, 0.6, 1.5, 0.6, 0.03);
            this.level().playSound(null, this.blockPosition(),
                    ModSounds.VIENTO_BOSQUE.get(), SoundSource.HOSTILE, 1.5F, 1.1F);
        }
        this.setAnimState(STATE_VANISH, 24);
        if (newPos != null) {
            this.teleportTo(newPos.x, newPos.y, newPos.z);
            this.setAnimState(STATE_APPEAR, 22);
        }
    }

    public int getAnimState() { return this.entityData.get(DATA_ANIM_STATE); }
    public void setAnimState(int state, int lockTicks) {
        this.entityData.set(DATA_ANIM_STATE, state);
        this.stateTimer = Math.max(this.stateTimer, lockTicks);
    }
    public boolean isStateLocked() { return this.stateTimer > 0; }

    public int getAggressionPhase() { return this.entityData.get(DATA_AGGRESSION_PHASE); }
    public void setAggressionPhase(int phase) {
        this.entityData.set(DATA_AGGRESSION_PHASE, Math.max(1, Math.min(5, phase)));
    }

    public boolean isLiberated() { return this.entityData.get(DATA_LIBERATED); }

    public boolean canWail() { return wailCooldown <= 0; }
    public void resetWailCooldown() { this.wailCooldown = 220 - (getAggressionPhase() * 20); }

    public boolean canAmbush() { return ambushCooldown <= 0; }
    public void resetAmbushCooldown() { this.ambushCooldown = 280 - (getAggressionPhase() * 25); }

    public boolean canWaterSurge() { return waterSurgeCooldown <= 0; }
    public void resetWaterSurgeCooldown() { this.waterSurgeCooldown = 240 - (getAggressionPhase() * 20); }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("AggressionPhase", this.getAggressionPhase());
        tag.putBoolean("Liberated", this.isLiberated());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("AggressionPhase")) this.setAggressionPhase(tag.getInt("AggressionPhase"));
        if (tag.contains("Liberated")) this.entityData.set(DATA_LIBERATED, tag.getBoolean("Liberated"));
    }

    private static final RawAnimation ANIM_APPEAR = RawAnimation.begin().thenPlay("animation.lallorona.appear");
    private static final RawAnimation ANIM_FLOAT = RawAnimation.begin().thenLoop("animation.lallorona.float");
    private static final RawAnimation ANIM_WALK = RawAnimation.begin().thenLoop("animation.lallorona.walk");
    private static final RawAnimation ANIM_CHASE = RawAnimation.begin().thenLoop("animation.lallorona.chase");
    private static final RawAnimation ANIM_HEAD_TURN = RawAnimation.begin().thenPlay("animation.lallorona.head_turn");
    private static final RawAnimation ANIM_STARE = RawAnimation.begin().thenLoop("animation.lallorona.stare");
    private static final RawAnimation ANIM_ATTACK = RawAnimation.begin().thenPlay("animation.lallorona.attack");
    private static final RawAnimation ANIM_WAIL = RawAnimation.begin().thenPlay("animation.lallorona.wail");
    private static final RawAnimation ANIM_VANISH = RawAnimation.begin().thenPlay("animation.lallorona.vanish");
    private static final RawAnimation ANIM_LIBERATION = RawAnimation.begin().thenPlayAndHold("animation.lallorona.liberation");

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "main_controller", 5, event -> {
            int state = this.getAnimState();
            return switch (state) {
                case STATE_LIBERATION -> event.setAndContinue(ANIM_LIBERATION);
                case STATE_WAIL -> event.setAndContinue(ANIM_WAIL);
                case STATE_ATTACK -> event.setAndContinue(ANIM_ATTACK);
                case STATE_APPEAR -> event.setAndContinue(ANIM_APPEAR);
                case STATE_VANISH -> event.setAndContinue(ANIM_VANISH);
                case STATE_HEAD_TURN -> event.setAndContinue(ANIM_HEAD_TURN);
                case STATE_STARE -> event.setAndContinue(ANIM_STARE);
                case STATE_CHASE -> event.setAndContinue(ANIM_CHASE);
                case STATE_WALK_SLOW -> event.setAndContinue(ANIM_WALK);
                default -> event.isMoving()
                        ? event.setAndContinue(ANIM_WALK)
                        : event.setAndContinue(ANIM_FLOAT);
            };
        }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.animCache;
    }

    @Override
    public boolean canCollideWith(Entity other) {
        return !this.isLiberated() && super.canCollideWith(other);
    }
}
