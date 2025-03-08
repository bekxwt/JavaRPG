package org.example.abilities.tank;

import org.example.abilities.Ability;
import org.example.effects.EffectManager;
import org.example.effects.buff.IronDefenseEffect;
import org.example.models.Character;
import org.example.utils.Utils;

public class IronDefense extends Ability {
    private static final int DURATION = 4;

    public IronDefense() {
        super("Железная защита", 15, 4);
    }

    @Override
    public void use(Character caster, Character target) {
        if (caster.getStats().mana < manaCost) {
            Utils.delayedPrint(caster.getName() + " не хватает маны для " + name + "!");
            return;
        }

        Utils.delayedPrint(String.format("🛡 %s использует способность Железная защита!", caster.getName()));
        caster.getStats().mana -= manaCost;
        EffectManager effectManager = EffectManager.getInstance();

        if (caster.isAlive()) {
            effectManager.applyEffect(caster, new IronDefenseEffect(DURATION));
        }
    }
}
