package org.example.effects.debuff;

import lombok.Getter;
import org.example.effects.Effect;
import org.example.models.Character;
import org.example.utils.Utils;  // Import the Utils class

@Getter
public class HealingReduction extends Effect {
    private final double reductionMultiplier;

    public HealingReduction(int duration, double reductionMultiplier) {
        super(duration);
        this.reductionMultiplier = reductionMultiplier;
    }

    @Override
    public void apply(Character target) {
        target.setHealingMultiplier(reductionMultiplier);
        Utils.delayedPrint(String.format("🚫 %s под эффектом 'Антихил'! Лечение снижено на %.0f%% (%d ходов).",
                target.getName(), (1 - reductionMultiplier) * 100, getDuration()));
    }

    @Override
    public void remove(Character target) {
        target.setHealingMultiplier(1.0);
        Utils.delayedPrint(String.format("✅ %s больше не под эффектом 'Антихил'.", target.getName()));
    }
}
