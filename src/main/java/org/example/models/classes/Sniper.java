package org.example.models.classes;

import org.example.abilities.Ability;
import org.example.models.Character;
import org.example.models.DamageType;
import org.example.models.Stats;

public class Sniper extends Character {
    public Sniper(String name, Stats stats, Ability ability) {
        super(name, stats, ability, DamageType.PHYSICAL);
    }

    @Override
    public int calculateDamage() {
        return (int) (stats.strength * 0.3 + stats.agility * 1.75 + stats.tactics * 1.6 + (int) (Math.random()*(stats.luck * 2)));
    }
}
