package org.example.effects.debuff;

import org.example.effects.Effect;
import org.example.models.Character;
import org.example.utils.Utils;

public class Taunted extends Effect {
    private Character tauntTarget;

    public Taunted(Character target, int duration) {
        super(duration);
        this.tauntTarget = target;
    }

    public Character getTauntTarget() {
        return tauntTarget;
    }

    @Override
    public void apply(Character character) {
        Utils.delayedPrint(String.format("🎯 %s под эффектом 'Таунт'! Теперь он вынужден атаковать %s! (%d ходов)",
                character.getName(), tauntTarget.getName(), getDuration()));
    }

    @Override
    public void remove(Character character) {
        Utils.delayedPrint(String.format("🔓 %s больше не под воздействием 'Таунт' и может атаковать любого.",
                character.getName()));
    }
}
