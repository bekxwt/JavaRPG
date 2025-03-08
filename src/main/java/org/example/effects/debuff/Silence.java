package org.example.effects.debuff;

import org.example.effects.Effect;
import org.example.models.Character;
import org.example.state.NormalState;
import org.example.state.SilenceState;

public class Silence extends Effect {
    public Silence(int duration) {
        super(duration);
    }

    @Override
    public void apply(Character target) {
        target.setState(new SilenceState(target));
    }

    @Override
    public void remove(Character target) {
        target.setState(new NormalState(target));
    }
}
