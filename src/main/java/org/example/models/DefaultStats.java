package org.example.models;

import org.example.models.classes.*;

import java.util.HashMap;
import java.util.Map;

public class DefaultStats {
    private static final Map<Class<? extends Character>, Stats> defaultStatsMap = new HashMap<>();

    static {
        defaultStatsMap.put(Tank.class, new Stats(12, 5, 4, 6, 12, 15, 4, 0));
        defaultStatsMap.put(Assassin.class, new Stats(7, 14, 10, 4, 4, 0, 7, 0));
        defaultStatsMap.put(Sniper.class, new Stats(6, 10, 7, 6, 6, 7, 9, 0));
        defaultStatsMap.put(Mage.class, new Stats(2, 4, 7, 15, 7, 5, 5, 0));
        defaultStatsMap.put(Bard.class, new Stats(2, 3, 8, 12, 8, 6, 6, 0));
    }

    public static Stats getDefaultStats(Class<? extends Character> characterClass) {
        return defaultStatsMap.getOrDefault(characterClass, new Stats(5, 5, 5, 5, 5, 5, 5, 5));
    }
}
