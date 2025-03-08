package org.example.abilities.assassin;

import org.example.abilities.Ability;
import org.example.effects.debuff.HealingReduction;
import org.example.models.Character;
import org.example.effects.debuff.Poison;
import static org.example.utils.Utils.delayedPrint;  // Importing the global delayedPrint method

public class PoisonBlades extends Ability {
    public PoisonBlades() {
        super("Ядовитые клинки", 17, 4);
    }

    @Override
    public void use(Character caster, Character target) {
        String message = String.format("🗡️ %s использует 'Ядовитые клинки' против %s!%n", caster.getName(), target.getName());
        delayedPrint(message);  // Using the global delayedPrint method

        int damage = (int) (caster.getStats().getAgility() * 0.85 + caster.getStats().getStrength() * 0.65);

        target.applyEffect(new Poison(3, damage));
        target.applyEffect(new HealingReduction(4, 0.5));
    }
}
