package org.example.models.classes;

import org.example.abilities.Ability;
import org.example.models.Character;
import org.example.models.DamageType;
import org.example.models.Stats;

public class Assassin extends Character {
    public Assassin(String name, Stats stats, Ability ability) {
        super(name, stats, ability, DamageType.PHYSICAL);
    }

    @Override
    public int calculateDamage() {
        return (int) (stats.strength * 0.5) + (int) (stats.agility * 1.6) + stats.tactics + (int) (Math.random()*(stats.luck * 2));
    }
}
