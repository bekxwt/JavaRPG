package org.example.abilities.tank;

import org.example.abilities.Ability;
import org.example.effects.EffectManager;
import org.example.effects.debuff.Taunted;
import org.example.models.Character;
import org.example.utils.Utils;

public class Taunt extends Ability {
    private static final int DURATION = 4;

    public Taunt() {
        super("Провокация", 12, 3);
    }

    @Override
    public void use(Character caster, Character target) {
        if (caster.getStats().mana < manaCost) {
            Utils.delayedPrint(caster.getName() + " не хватает маны для " + name + "!");
            return;
        }

        caster.getStats().mana -= manaCost;
        Utils.delayedPrint(String.format("🛡 %s использует 'Таунт'! Все враги вынуждены атаковать его!", caster.getName()));

        EffectManager effectManager = EffectManager.getInstance();
        if (target.isAlive()) {
            effectManager.applyEffect(target, new Taunted(caster, DURATION));
        }
    }
}
