package org.example.effects.debuff;

import org.example.effects.Effect;
import org.example.models.Character;
import org.example.state.NormalState;
import org.example.state.StunnedState;

public class Stun extends Effect {
    public Stun(int duration) {
        super(duration);
    }

    @Override
    public void apply(Character target) {
        target.setState(new StunnedState(target));
    }

    @Override
    public void remove(Character target) {
        target.setState(new NormalState(target));
    }
}
