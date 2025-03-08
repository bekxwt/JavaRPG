package org.example.effects.buff;

import lombok.Getter;
import org.example.effects.Effect;
import org.example.models.Character;
import org.example.utils.Utils;  // Import the Utils class

@Getter
public class BoostDamage extends Effect {
    private final double damageMultiplier;

    public BoostDamage(int duration, double damageMultiplier) {
        super(duration);
        this.damageMultiplier = damageMultiplier;
    }

    @Override
    public void apply(Character target) {
        Utils.delayedPrint(String.format("🔥 %s получает эффект 'Буст урона' (+%.0f%%) на %d ходов!",
                target.getName(), (damageMultiplier) * 100, getDuration()));  // Use Utils.delayedPrint()
    }

    @Override
    public void remove(Character target) {
        Utils.delayedPrint(String.format("🛑 Эффект 'Буст урона' на %s исчез! Урон возвращён к норме.",
                target.getName()));  // Use Utils.delayedPrint()
    }
}
