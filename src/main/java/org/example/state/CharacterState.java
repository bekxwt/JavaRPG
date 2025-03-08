package org.example.state;

import org.example.models.Character;

public abstract class CharacterState {
    protected Character character;

    public CharacterState(Character character) {
        this.character = character;
    }

    public abstract boolean cannotAttack();
    public abstract boolean cannotCast();
}
