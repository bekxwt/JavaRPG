package org.example.abilities.sniper;

import org.example.abilities.Ability;
import org.example.commands.AttackAbilityCommand;
import org.example.models.Character;

import org.example.abilities.Ability;
import org.example.commands.AttackAbilityCommand;
import org.example.models.Character;
import org.example.utils.Utils;

public class CriticalShot extends Ability {
    public CriticalShot() {
        super("Критический выстрел", 14, 3);
    }

    @Override
    public void use(Character caster, Character target) {
        int baseDamage = caster.calculateDamage();
        int critDamage = (int) (baseDamage * 2);

        String message = String.format("💥 %s использует 'Критический выстрел'! %s получает %d критического урона!",
                caster.getName(), target.getName(), critDamage);

        Utils.delayedPrint(message);
        new AttackAbilityCommand(caster, target, message, critDamage).execute();
    }
}

