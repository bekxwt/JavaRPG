package org.example.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Stats {
    public int strength;
    public int agility;
    public int tactics;
    public int intelligence;
    public int endurance;
    public int defense;
    public int initiation;
    public int luck;
    public int maxHealth;
    public int mana;
    public int maxMana;
    public int magicResistance = 0;
    public int bonusResistance = 0;
    public int bonusDefense = 0;

    public Stats(int strength, int agility, int tactics, int intelligence, int endurance, int defense, int initiation, int luck) {
        this.strength = strength;
        this.agility = agility;
        this.tactics = tactics;
        this.intelligence = intelligence;
        this.endurance = endurance;
        this.defense = defense;
        this.initiation = initiation;
        this.luck = luck;
        this.maxHealth = 300 + (endurance * 15) + ( strength * 4);
        this.mana = 30 + (int) (intelligence * 1.5);
        this.maxMana = this.mana;
        this.magicResistance = magicResistance + (int) (intelligence / 1.5);
    }

    public static Stats combine(Stats base, Stats additional) {
        return new Stats(
                base.strength + additional.strength,
                base.agility + additional.agility,
                base.tactics + additional.tactics,
                base.intelligence + additional.intelligence,
                base.endurance + additional.endurance,
                base.defense + additional.defense,
                base.initiation + additional.initiation,
                base.luck + additional.luck
        );
    }

    public void decreaseAgility(int value) {
        agility = agility - value;
    }

    public void decreaseInitiation(int value) {
        initiation = initiation - value;
    }

    public void decreaseTactics(int value) {
        tactics = tactics - value;
    }

    public void decreaseStrength(int value) {
        strength = strength - value;
    }

    public void increaseAgility(int value) {
        agility += value;
    }

    public void increaseInitiation(int value) {
        initiation += value;
    }

    public void increaseTactics(int value) {
        tactics += value;
    }

    public void increaseStrength(int value) {
        strength += value;
    }

    public void decreaseMagicResistance(int value) {
        magicResistance = magicResistance - value;
    }

    public void increaseMagicResistance(int value) {
        magicResistance += value;
    }
}
