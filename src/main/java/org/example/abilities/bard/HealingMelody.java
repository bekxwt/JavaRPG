package org.example.abilities.bard;

import org.example.abilities.Ability;
import org.example.models.Character;
import static org.example.utils.Utils.delayedPrint;  // Importing the global delayedPrint method

public class HealingMelody extends Ability {
    public HealingMelody() {
        super("Исцеляющая мелодия", 25, 3);
    }

    @Override
    public void use(Character caster, Character target) {
        Character lowestHpAlly = caster.getBattleManager().findWeakestAlly(caster);
        if (lowestHpAlly == null) return;

        int healAmount = (int) (35 + (int) ((double) 50 * ((double) caster.getStats().getIntelligence() / 40)));
        int realHeal = lowestHpAlly.heal(healAmount);

        String message = String.format("🎵 %s играет 'Исцеляющую мелодию'! %s восстановил %d HP! 💚%n",
                caster.getName(), lowestHpAlly.getName(), realHeal);
        delayedPrint(message);
    }
}
