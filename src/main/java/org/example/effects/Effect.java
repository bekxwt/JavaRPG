package org.example.effects;

import org.example.models.Character;

public abstract class Effect {
    protected int duration;

    public Effect(int duration) {
        this.duration = duration;
    }

    public abstract void apply(Character target);

    public abstract void remove(Character target);

    public void update(Character target) {
        
    }

    public int getDuration() {
        return duration;
    }

    public void decreaseDuration() {
        duration--;
    }
}