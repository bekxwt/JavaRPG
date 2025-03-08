package org.example.models.classes;

import org.example.abilities.Ability;
import org.example.models.Character;
import org.example.models.DamageType;
import org.example.models.Stats;

public class Mage extends Character {
    public Mage(String name, Stats stats, Ability ability) {
        super(name, stats, ability, DamageType.MAGIC);
    }

    @Override
    public int calculateDamage() {
        return stats.strength + stats.intelligence + stats.tactics + (int) (Math.random()*(stats.luck * 2));
    }
}
