package org.example.abilities.assassin;

import org.example.abilities.Ability;
import org.example.models.Character;
import org.example.effects.buff.Evasion;
import static org.example.utils.Utils.delayedPrint;  // Importing the global delayedPrint method

public class ShadowDodge extends Ability {
    public ShadowDodge() {
        super("Теневое уклонение", 15, 4);
    }

    @Override
    public void use(Character caster, Character target) {
        String message = String.format("🌫️ %s использует 'Тень'! Уклонение повышено на 35%% на 3 хода%n", caster.getName());
        delayedPrint(message);  // Using the global delayedPrint method

        caster.applyEffect(new Evasion(4, 0.35));
    }
}
