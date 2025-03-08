package org.example.effects.debuff;

import lombok.Getter;
import org.example.effects.Effect;
import org.example.models.Character;
import org.example.utils.Utils;

@Getter
public class Weakness extends Effect {
    private double reductionPercentage = 0;

    public Weakness(int duration, double reductionPercentage) {
        super(duration);
        this.reductionPercentage = reductionPercentage;
    }

    @Override
    public void apply(Character target) {
        Utils.delayedPrint(String.format("🛡️ %s получает эффект слабость на %.0f%% (%d ходов)",
                target.getName(), reductionPercentage * 100, getDuration()));
    }

    @Override
    public void remove(Character target) {
        Utils.delayedPrint(String.format("🛡️ %s возвращает урон в норму!", target.getName()));
    }
}
