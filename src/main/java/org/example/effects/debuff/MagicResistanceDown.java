package org.example.effects.debuff;

import org.example.effects.Effect;
import org.example.models.Character;
import org.example.utils.Utils;  // Import the Utils class

public class MagicResistanceDown extends Effect {
    private final int resistanceReduction;

    public MagicResistanceDown(int duration, int resistanceReduction) {
        super(duration);
        this.resistanceReduction = resistanceReduction;
    }

    @Override
    public void apply(Character target) {
        target.getStats().decreaseMagicResistance(resistanceReduction);
        Utils.delayedPrint(String.format("🔵 %s получает эффект 'Снижение магического сопротивления' (-%d) на %d ходов!",
                target.getName(), resistanceReduction, getDuration()));
    }

    @Override
    public void remove(Character target) {
        target.getStats().increaseMagicResistance(resistanceReduction);
        Utils.delayedPrint(String.format("🛡️ Эффект 'Снижение магического сопротивления' на %s исчез! Маг. сопротивление восстановлено.",
                target.getName()));
    }
}
