package org.example.effects.buff;

import org.example.battle.CombatController;
import org.example.effects.Effect;
import org.example.models.Character;
import org.example.utils.Utils;  // Import the Utils class

import java.util.Random;

public class CounterattackEffect extends Effect {
    private static final Random random = new Random();
    private CombatController combatController = CombatController.getInstance();
    private static final double COUNTERATTACK_CHANCE = 0.5;

    public CounterattackEffect(int duration) {
        super(duration);
    }

    @Override
    public void apply(Character target) {
        Utils.delayedPrint(String.format("🛡 %s активирует Контратаку! (Длительность: %d ходов)", target.getName(), duration));  // Use Utils.delayedPrint()
    }

    @Override
    public void remove(Character target) {
        Utils.delayedPrint(String.format("⏳ Контратака %s истекла.", target.getName()));  // Use Utils.delayedPrint()
    }

    public void tryCounterattack(Character target, Character attacker) {
        if (random.nextDouble() < COUNTERATTACK_CHANCE) {
            Utils.delayedPrint(String.format("⚡ %s контратакует %s!", target.getName(), attacker.getName()));  // Use Utils.delayedPrint()
            combatController.attack(target, attacker);
        }
    }
}
