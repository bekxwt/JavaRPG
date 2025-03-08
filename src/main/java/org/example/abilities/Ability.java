package org.example.abilities;

import org.example.models.Character;
import org.example.utils.Utils;

public abstract class Ability {
    protected String name;
    protected int manaCost;
    private int baseCooldown;
    private int remainingCooldown = 0;

    public Ability(String name, int manaCost, int cooldownTurns) {
        this.name = name;
        this.manaCost = manaCost;
        this.baseCooldown = cooldownTurns;
    }

    public abstract void use(Character caster, Character target);

    public boolean isAvailable() {
        return remainingCooldown == 0;
    }

    public void reduceCooldown() {
        if (remainingCooldown > 0) {
            remainingCooldown--;
        }
    }

    public void useAbility(Character caster, Character target) {
        if (isAvailable()) {
            use(caster, target);
            remainingCooldown = baseCooldown;
        } else {
            Utils.delayedPrint(String.format("⏳ %s не может использовать '%s'! Осталось %d ходов до восстановления.%n",
                    caster.getName(), name, remainingCooldown));
        }
    }

    public void decreaseCooldown(int decrease) {
        this.baseCooldown -= decrease;
        if (this.baseCooldown <= 0) {
            remainingCooldown = 0;
        }
    }
}
