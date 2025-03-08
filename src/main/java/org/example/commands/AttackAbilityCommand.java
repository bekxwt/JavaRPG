package org.example.commands;

import org.example.models.Character;
import org.example.utils.Utils;

public class AttackAbilityCommand implements BattleCommand {
    private final Character attacker;
    private final Character defender;
    private final String message;
    private final int damage;

    public AttackAbilityCommand(Character caster, Character target, String message, int damage) {
        this.attacker = caster;
        this.defender = target;
        this.message = message;
        this.damage = damage;
    }

    @Override
    public void execute() {
        if (!attacker.isAlive() || !defender.isAlive()) {
            Utils.delayedPrint("⛔ Атака невозможна: один из бойцов уже повержен!");
            return;
        }

        Utils.delayedPrint(String.format(message));

        defender.setLastAttacker(attacker);
        new DefendCommand(defender, attacker, damage).execute();
    }
}
