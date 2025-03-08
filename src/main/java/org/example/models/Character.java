package org.example.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import lombok.Getter;
import lombok.Setter;
import org.example.abilities.Ability;
import org.example.battle.BattleManager;
import org.example.effects.Effect;
import org.example.effects.EffectManager;
import org.example.effects.debuff.HealingReduction;
import org.example.state.CharacterState;
import org.example.state.NormalState;

@Getter
@Setter
public abstract class Character {
    protected String name;
    protected Stats stats;
    protected Ability ability;
    protected EffectManager effectManager = EffectManager.getInstance();
    protected BattleManager battleManager;
    protected Character lastAttacker = null;
    protected CharacterState state;
    private final DamageType damageType;
    private double healingMultiplier = 1;

    // Health management fields
    private int health;
    private final IntegerProperty healthProperty = new SimpleIntegerProperty();

    public Character(String name, Stats playerStats, Ability ability, DamageType damageType) {
        this.name = name;
        Stats baseStats = DefaultStats.getDefaultStats(this.getClass());
        this.stats = Stats.combine(baseStats, playerStats);
        this.ability = ability;
        this.health = stats.maxHealth;
        this.healthProperty.set(health);
        this.state = new NormalState(this);
        this.damageType = damageType;
        this.updateCooldowns();
    }

    public boolean isAlive() {
        return health > 0;
    }

    public void takeDamage(int damage) {
        setHealth(getHealth() - damage);
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
        this.healthProperty.set(health);
    }

    public int getMaxHealth() {
        return stats.maxHealth;
    }

    public void applyEffect(Effect effect) {
        effectManager.applyEffect(this, effect);
    }

    public abstract int calculateDamage();

    public int heal(int heal) {
        if (!isAlive()) return 0;

        if (effectManager.hasEffect(this, HealingReduction.class)) {
            HealingReduction reductionEffect = (HealingReduction) effectManager.getEffect(this, HealingReduction.class);
            heal *= reductionEffect.getReductionMultiplier();
            System.out.printf("🚫 %s под эффектом 'Снижение лечения'! Лечение снижено до %d%n", getName(), heal);
        }

        setHealth(getHealth() + heal);
        return heal;
    }

    public void reduceCooldowns() {
        if (ability != null) {
            ability.reduceCooldown();
        }
    }

    public void updateCooldowns() {
        int initiative = this.getStats().getInitiation();

        if (initiative > 24) {
            this.getAbility().decreaseCooldown(2);
        } else if (initiative > 12) {
            this.getAbility().decreaseCooldown(1);
        }
    }

    public IntegerProperty healthProperty() {
        return healthProperty;
    }
}