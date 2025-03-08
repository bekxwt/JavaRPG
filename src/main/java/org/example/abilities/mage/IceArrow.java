package org.example.abilities.mage;

import org.example.abilities.Ability;
import org.example.commands.AttackAbilityCommand;
import org.example.effects.debuff.Freeze;
import org.example.models.Character;

public class IceArrow extends Ability {
    public IceArrow() {
        super("Ледяная стрела", 23, 3);
    }

    @Override
    public void use(Character caster, Character target) {
        int damage = (int) (caster.getStats().getIntelligence() * 1.8);

        String message = String.format("❄️ %s использует 'Ледяную стрелу' против %s! Урон: %d%n",
                caster.getName(), target.getName(), damage);

        new AttackAbilityCommand(caster, target, message, damage).execute();

        target.applyEffect(new Freeze(4));
    }
}
