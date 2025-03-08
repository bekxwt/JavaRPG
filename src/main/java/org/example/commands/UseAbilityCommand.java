package org.example.commands;

import org.example.models.Character;
import org.example.abilities.Ability;
import org.example.utils.Utils;  // Import the Utils class

public class UseAbilityCommand implements BattleCommand {
    private Character caster;
    private Character target;

    public UseAbilityCommand(Character caster, Character target) {
        this.caster = caster;
        this.target = target;
    }

    @Override
    public void execute() {
        if (caster.getAbility() == null) {
            Utils.delayedPrint(caster.getName() + " не имеет способности!");  // Use Utils.delayedPrint()
            return;
        }

        Utils.delayedPrint(String.format("✨ %s использует способность на %s!", caster.getName(), target.getName()));  // Use Utils.delayedPrint()
        caster.getAbility().useAbility(caster, target);
    }
}
