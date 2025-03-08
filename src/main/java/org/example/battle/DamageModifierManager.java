package org.example.battle;

import org.example.models.Character;
import org.example.models.classes.*;
import java.util.HashMap;
import java.util.Map;

public class DamageModifierManager {
    private static final Map<Class<? extends Character>, Map<Class<? extends Character>, Double>> damageModifiers = new HashMap<>();

    static {
        addModifier(Assassin.class, Tank.class, 0.7);
        addModifier(Tank.class, Mage.class, 0.7);
        addModifier(Mage.class, Sniper.class, 0.7);
        addModifier(Sniper.class, Bard.class, 0.7);
        addModifier(Bard.class, Assassin.class, 0.7);
    }

    private static void addModifier(Class<? extends Character> attacker, Class<? extends Character> target, double multiplier) {
        damageModifiers
                .computeIfAbsent(attacker, k -> new HashMap<>())
                .put(target, multiplier);
    }

    public static double getDamageMultiplier(Character attacker, Character target) {
        return damageModifiers.getOrDefault(attacker.getClass(), new HashMap<>())
                .getOrDefault(target.getClass(), 1.0); // По умолчанию 100% урона
    }
}
