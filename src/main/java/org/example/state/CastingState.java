package org.example.state;

import org.example.battle.BattleLogger;
import org.example.models.Character;

public class CastingState extends CharacterState{
    public CastingState(Character character) {
        super(character);
    }

    @Override
    public boolean cannotAttack() {
        BattleLogger.log("⏳ " + character.getName() + " сосредоточен на касте и не может атаковать!");
        return true;
    }

    @Override
    public boolean cannotCast() {
        return false;
    }
}
