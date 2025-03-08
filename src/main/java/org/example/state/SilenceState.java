package org.example.state;

import org.example.battle.BattleLogger;
import org.example.models.Character;

public class SilenceState extends CharacterState{
    public SilenceState(Character character) {
        super(character);
    }

    @Override
    public boolean cannotAttack() {
        return false;
    }

    @Override
    public boolean cannotCast() {
        BattleLogger.log("⛔ " + character.getName() + " под эффектом молчания и не может использовать способности!");
        return true;
    }
}
