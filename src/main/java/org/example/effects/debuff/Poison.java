package org.example.effects.debuff;

import org.example.effects.Effect;
import org.example.models.Character;
import org.example.utils.Utils;

public class Poison extends Effect {
    private final int poisonDamage;

    public Poison(int duration, int poisonDamage) {
        super(duration);
        this.poisonDamage = poisonDamage;
    }

    @Override
    public void apply(Character target) {
        Utils.delayedPrint(String.format("☠️ %s отравлен! Будет получать %d урона каждый ход (%d ходов).",
                target.getName(), poisonDamage, getDuration()));
    }

    @Override
    public void update(Character target) {
        Utils.delayedPrint(String.format("☠️ %s получает %d урона от яда!", target.getName(), poisonDamage));
        target.setHealth(target.getHealth() - poisonDamage);
    }

    @Override
    public void remove(Character target) {
        Utils.delayedPrint(String.format("✅ %s больше не отравлен.", target.getName()));
    }
}
