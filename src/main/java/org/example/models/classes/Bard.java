package org.example.models.classes;

import org.example.abilities.Ability;
import org.example.models.Character;
import org.example.models.DamageType;
import org.example.models.Stats;

public class Bard extends Character {
    public Bard(String name, Stats stats, Ability ability) {
        super(name, stats, ability, DamageType.MAGIC);
    }

    @Override
    public int calculateDamage() {
        return (int) ((double) stats.strength / 2 + stats.intelligence + stats.tactics + Math.random()*(stats.luck * 2));
    }
}
