package org.example.effects.buff;

import lombok.Getter;
import org.example.effects.Effect;
import org.example.models.Character;

@Getter
public class Evasion extends Effect {
    private final double chance;

    public Evasion(int duration, double chance) {
        super(duration);
        this.chance = chance;
    }

    @Override
    public void apply(Character target) {

    }

    @Override
    public void remove(Character target) {

    }
}
