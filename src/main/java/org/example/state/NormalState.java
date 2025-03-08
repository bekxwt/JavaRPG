package org.example.state;

import org.example.models.Character;

public class NormalState extends CharacterState {
    public NormalState(Character character) {
        super(character);
    }

    @Override
    public boolean cannotAttack() {
        return false;
    }

    @Override
    public boolean cannotCast() {
        return false;
    }
}
