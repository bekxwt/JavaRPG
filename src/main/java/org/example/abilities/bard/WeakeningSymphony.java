package org.example.abilities.bard;

import org.example.abilities.Ability;
import org.example.effects.debuff.MagicResistanceDown;
import org.example.effects.debuff.Weakness;
import org.example.models.Character;
import java.util.List;
import static org.example.utils.Utils.delayedPrint;

public class WeakeningSymphony extends Ability {
    public WeakeningSymphony() {
        super("Ослабляющая симфония", 24, 4);
    }

    @Override
    public void use(Character caster, Character target) {
        List<Character> enemies = caster.getBattleManager().getEnemiesFor(caster);

        for (Character enemy : enemies) {
            enemy.applyEffect(new Weakness(5, 0.25));
            enemy.applyEffect(new MagicResistanceDown(5, 20));

            String message = String.format("🎶 %s использует 'Ослабляющую симфонию'! %s ослаблен!%n",
                    caster.getName(), enemy.getName());
            delayedPrint(message);
        }
    }
}
