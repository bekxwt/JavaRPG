package org.example.effects.debuff;

import org.example.effects.Effect;
import org.example.models.Character;
import org.example.utils.Utils;  // Import the Utils class

public class Freeze extends Effect {
    private static final int REDUCTION_VALUE = 8;

    public Freeze(int duration) {
        super(duration);
    }

    @Override
    public void apply(Character target) {
        Utils.delayedPrint(String.format("❄️ %s заморожен! (Ловкость, Инициация, Тактика, Сила -%d)",
                target.getName(), REDUCTION_VALUE));

        target.getStats().decreaseAgility(REDUCTION_VALUE);
        target.getStats().decreaseInitiation(REDUCTION_VALUE);
        target.getStats().decreaseTactics(REDUCTION_VALUE);
        target.getStats().decreaseStrength(REDUCTION_VALUE);
    }

    @Override
    public void remove(Character target) {
        Utils.delayedPrint(String.format("🔥 %s освобождается от заморозки! (Характеристики возвращены)", target.getName()));

        target.getStats().increaseAgility(REDUCTION_VALUE);
        target.getStats().increaseInitiation(REDUCTION_VALUE);
        target.getStats().increaseTactics(REDUCTION_VALUE);
        target.getStats().increaseStrength(REDUCTION_VALUE);
    }
}
