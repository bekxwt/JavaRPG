package org.example.abilities.bard;

import org.example.models.Character;
import org.example.effects.buff.BoostDamage;
import org.example.effects.debuff.Silence;
import org.example.abilities.Ability;
import java.util.List;
import static org.example.utils.Utils.delayedPrint;  // Importing the global delayedPrint method

public class BattleHymn extends Ability {
    public BattleHymn() {
        super("Боевой гимн", 20, 4);
    }

    @Override
    public void use(Character caster, Character target) {
        List<Character> allies = caster.getBattleManager().getAlliesFor(caster);
        List<Character> enemies = caster.getBattleManager().getEnemiesFor(caster);

        for (Character ally : allies) {
            ally.applyEffect(new BoostDamage(4, 0.3));
            String message = String.format("🔥 %s использует 'Боевой гимн'! %s получает усиление атаки!%n",
                    caster.getName(), ally.getName());
            delayedPrint(message);  // Using the global delayedPrint method
        }

        for (Character enemy : enemies) {
            enemy.applyEffect(new Silence(2));
            String message = String.format("🔇 %s использует 'Боевой гимн'! %s не может использовать способности!%n",
                    caster.getName(), enemy.getName());
            delayedPrint(message);  // Using the global delayedPrint method
        }
    }
}
