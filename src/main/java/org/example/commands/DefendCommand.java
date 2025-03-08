package org.example.commands;

import javafx.application.Platform;
import org.example.battle.DamageModifierManager;
import org.example.effects.*;
import org.example.effects.buff.BoostDamage;
import org.example.effects.buff.CounterattackEffect;
import org.example.effects.buff.Evasion;
import org.example.effects.debuff.Weakness;
import org.example.models.Character;
import org.example.models.DamageType;
import org.example.utils.Utils; // Import Utils class

public class DefendCommand implements BattleCommand {
    private Character defender;
    private int incomingDamage;
    private Character attacker;

    public DefendCommand(Character defender, Character attacker, int incomingDamage) {
        this.defender = defender;
        this.incomingDamage = incomingDamage;
        this.attacker = attacker;
    }

    @Override
    public void execute() {
        EffectManager effectManager = EffectManager.getInstance();

        boolean isWeakened = effectManager.hasEffect(defender, Weakness.class);
        boolean hasDamageBoost = effectManager.hasEffect(defender, BoostDamage.class);

        int reducedDamage = incomingDamage;
        Utils.delayedPrint(String.format("🛡 %s пытается защититься! Входящий урон: %d", defender.getName(), incomingDamage)); // Use Utils.delayedPrint

        double classMultiplier = DamageModifierManager.getDamageMultiplier(attacker, defender);
        reducedDamage *= classMultiplier;
        if (classMultiplier < 1.0) {
            Utils.delayedPrint(String.format("⚠️ %s атакует %s, но урон снижен на %d%% из-за классового взаимодействия! Новый урон: %d",
                    attacker.getName(), defender.getName(), (int) ((1 - classMultiplier) * 100), reducedDamage)); // Use Utils.delayedPrint
        }

        double dodgeChance = (defender.getStats().agility + defender.getStats().luck) * 0.005;
        if (Math.random() < dodgeChance) {
            Utils.delayedPrint(String.format("💨 %s уклоняется от атаки!", defender.getName())); // Use Utils.delayedPrint
            return;
        }

        if (defender.getEffectManager().hasEffect(defender, Evasion.class)) {
            Evasion evasion = (Evasion) defender.getEffectManager().getEffect(defender, Evasion.class);
            if (Math.random() < evasion.getChance()) {
                Utils.delayedPrint(String.format("💨 %s уклоняется от атаки благодаря эффекту!", defender.getName())); // Use Utils.delayedPrint
                return;
            }
        }

        double defenseFactor = 0;

        if (attacker.getDamageType() == DamageType.PHYSICAL) {
            defenseFactor = ((100 - (int) (defender.getStats().defense * 1.25) - (int) (defender.getStats().bonusDefense * 1.25)) / 100.0);
        } else {
            defenseFactor = (100 - defender.getStats().magicResistance - defender.getStats().bonusResistance) / 100.0;
        }

        reducedDamage *= defenseFactor;
        Utils.delayedPrint(String.format("🛡 Защита снижает урон: %.1f%%. Новый урон: %d", (1 - defenseFactor) * 100, reducedDamage)); // Use Utils.delayedPrint

        reducedDamage = Math.max(1, reducedDamage);
        int finalDamage = reducedDamage;

        Platform.runLater(() -> {
            defender.takeDamage(finalDamage);
            Utils.delayedPrint(String.format("💥 %s получает %d урона! Осталось HP: %d",
                    defender.getName(), finalDamage, defender.getHealth()));
        });

        if (defender.getEffectManager().hasEffect(defender, CounterattackEffect.class)) {
            CounterattackEffect counterEffect = (CounterattackEffect) defender.getEffectManager().getEffect(defender, CounterattackEffect.class);
            if (counterEffect != null) {
                counterEffect.tryCounterattack(defender, defender.getLastAttacker());
            }
        }
    }
}
