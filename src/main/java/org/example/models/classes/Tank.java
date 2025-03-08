package org.example.models.classes;

import org.example.abilities.Ability;
import org.example.models.Character;
import org.example.models.DamageType;
import org.example.models.Stats;

public class Tank extends Character {
    public Tank(String name, Stats stats, Ability ability) {
        super(name, stats, ability, DamageType.PHYSICAL);
    }

    @Override
    public int calculateDamage() {
        return stats.strength * 2 + (int) (Math.random()*(stats.luck * 2));
    }
}
