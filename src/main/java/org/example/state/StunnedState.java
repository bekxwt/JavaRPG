package org.example.state;

import org.example.battle.BattleLogger;
import org.example.models.Character;

public class StunnedState extends CharacterState {
    public StunnedState(Character character) {
        super(character);
    }

    @Override
    public boolean cannotAttack() {
        BattleLogger.log("⛔ " + character.getName() + " оглушен и не может атаковать!");
        return true;
    }

    @Override
    public boolean cannotCast() {
        return true;
    }
}
