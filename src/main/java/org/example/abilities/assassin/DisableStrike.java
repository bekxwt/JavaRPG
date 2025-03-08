package org.example.abilities.assassin;

import org.example.abilities.Ability;
import org.example.models.Character;
import org.example.effects.debuff.Silence;
import org.example.effects.debuff.Weakness;
import org.example.utils.Utils;

public class DisableStrike extends Ability {
    public DisableStrike() {
        super("Отключение", 15, 4);
    }

    @Override
    public void use(Character caster, Character target) {
        String message = String.format("⚔️ %s использует 'Отключение' против %s!%n", caster.getName(), target.getName());
        Utils.delayedPrint(message);

        target.applyEffect(new Silence(4));
        target.applyEffect(new Weakness(4, 0.25));
    }
}
