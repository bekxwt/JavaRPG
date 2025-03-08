package org.example.effects.buff;

import org.example.effects.Effect;
import org.example.models.Character;
import org.example.utils.Utils;  // Import the Utils class

public class IronDefenseEffect extends Effect {
    private final int magicResistanceIncrease = 30;
    private final int defenseBoost = 25;

    public IronDefenseEffect(int duration) {
        super(duration);
    }

    @Override
    public void apply(Character target) {
        target.getStats().setBonusResistance(magicResistanceIncrease);
        target.getStats().setBonusDefense(defenseBoost);
        Utils.delayedPrint(String.format("🛡 %s активирует железную защиту! +30%% магического сопротивления, +25 защиты!", target.getName()));  // Use Utils.delayedPrint()
    }

    @Override
    public void remove(Character target) {
        target.getStats().setBonusResistance(0);
        target.getStats().setBonusDefense(0);
        Utils.delayedPrint(String.format("🔚 Эффект Iron Defense у %s закончился.", target.getName()));  // Use Utils.delayedPrint()
    }
}
