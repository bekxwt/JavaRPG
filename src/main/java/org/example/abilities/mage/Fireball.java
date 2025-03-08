package org.example.abilities.mage;

import org.example.abilities.Ability;
import org.example.models.Character;
import java.util.List;
import static org.example.utils.Utils.delayedPrint;

public class Fireball extends Ability {
    public Fireball() {
        super("Огненный шар", 23, 3);
    }

    @Override
    public void use(Character caster, Character target) {
        if (caster.getStats().mana < manaCost) {
            delayedPrint(caster.getName() + " не хватает маны для " + name + "!");
            return;
        }

        caster.getStats().mana -= manaCost;
        int damage = caster.getStats().intelligence * 3;
        target.takeDamage(damage);

        delayedPrint(String.format("🔥 %s использует 'Огненный Шар'! (%d магического урона всем врагам)%n",
                caster.getName(), damage));

        List<Character> enemies = caster.getBattleManager().getEnemiesFor(caster);

        for (Character enemy : enemies) {
            if (enemy.isAlive()) {
                delayedPrint(String.format("💥 %s получает %d урона от 'Огненного Шара'!%n",
                        enemy.getName(), damage));
                enemy.takeDamage(damage);
            }
        }
    }
}
