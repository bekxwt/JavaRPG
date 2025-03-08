package org.example.battle;

import org.example.commands.AttackAbilityCommand;
import org.example.models.Character;
import org.example.commands.AttackCommand;
import org.example.commands.UseAbilityCommand;

public class CombatController {
    private static CombatController instance;

    private CombatController() {}

    public static CombatController getInstance() {
        if (instance == null) {
            instance = new CombatController();
        }
        return instance;
    }

    public void attack(Character attacker, Character target) {
        new AttackCommand(attacker, target).execute();
    }

    public void useAbility(Character caster, Character target) {
        new UseAbilityCommand(caster, target).execute();
    }

    public void attackAbility(Character caster, Character target, String message, int damage) {
        new AttackAbilityCommand(caster, target, message, damage).execute();
    }
}