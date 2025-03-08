package org.example.abilities.tank;

import org.example.abilities.Ability;
import org.example.effects.buff.CounterattackEffect;
import org.example.models.Character;
import org.example.utils.Utils;

public class CounterAttack extends Ability {
    private final int DURATION = 3;

    public CounterAttack() {
        super("Контратака", 14, 4);
    }

    @Override
    public void use(Character caster, Character target) {
        Utils.delayedPrint(String.format("🛡 %s использует способность Контратака!", caster.getName()));
        caster.getEffectManager().applyEffect(caster, new CounterattackEffect(DURATION));
    }
}
