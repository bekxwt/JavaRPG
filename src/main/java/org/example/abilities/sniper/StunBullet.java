package org.example.abilities.sniper;

import org.example.abilities.Ability;
import org.example.commands.AttackAbilityCommand;
import org.example.effects.debuff.Stun;
import org.example.models.Character;
import org.example.utils.Utils;

public class StunBullet extends Ability {
    private final int DURATION = 2;

    public StunBullet() {
        super("Станящая пуля", 16, 4);
    }

    @Override
    public void use(Character caster, Character target) {
        int damage = (int) (caster.getStats().getTactics() * 1.3);

        String message = String.format("💫 %s использует способность оглушение против %s! Урон: %d %n", caster.getName(), target.getName(), damage);
        Utils.delayedPrint(message);

        new AttackAbilityCommand(caster, target, message, damage).execute();

        target.applyEffect(new Stun(DURATION));
    }
}
