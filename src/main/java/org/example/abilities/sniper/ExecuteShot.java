package org.example.abilities.sniper;

import org.example.abilities.Ability;
import org.example.models.Character;
import org.example.utils.Utils;

public class ExecuteShot extends Ability {
    public ExecuteShot() {
        super("Добивающий выстрел", 0, 0);
    }

    @Override
    public void use(Character caster, Character target) {
        double threshold = target.getStats().getMaxHealth() * 0.3;

        if (target.getHealth() <= threshold) {
            String message = String.format("🎯 %s совершает 'Добивающий выстрел'! %s мгновенно убит! 💀%n",
                    caster.getName(), target.getName());
            Utils.delayedPrint(message);
            target.takeDamage(target.getHealth());
        } else {
            String message = String.format("❌ %s попытался использовать 'Добивающий выстрел', но у %s слишком много ХП!%n",
                    caster.getName(), target.getName());
            Utils.delayedPrint(message);
        }
    }
}
