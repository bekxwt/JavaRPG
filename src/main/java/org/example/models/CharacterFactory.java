package org.example.models;

import org.example.abilities.Ability;
import org.example.models.classes.*;

public class CharacterFactory {
    public static Character createCharacter(String type, String name, Stats playerStats, Ability ability) {
        return switch (type.toLowerCase()) {
            case "tank" -> new Tank(name, playerStats, ability);
            case "mage" -> new Mage(name, playerStats, ability);
            case "assassin" -> new Assassin(name, playerStats, ability);
            case "sniper" -> new Sniper(name, playerStats, ability);
            case "bard" -> new Bard(name, playerStats, ability);
            default -> throw new IllegalArgumentException("Неизвестный класс персонажа: " + type);
        };
    }
}
