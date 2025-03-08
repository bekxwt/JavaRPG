package org.example.commands;

import org.example.battle.CombatController;
import org.example.battle.DamageModifierManager;
import org.example.effects.*;
import org.example.effects.buff.BoostDamage;
import org.example.effects.debuff.Silence;
import org.example.effects.debuff.Stun;
import org.example.effects.debuff.Taunted;
import org.example.effects.debuff.Weakness;
import org.example.models.Character;
import org.example.state.NormalState;
import org.example.state.SilenceState;
import org.example.state.StunnedState;
import org.example.utils.Utils;

import java.util.Random;

public class AttackCommand implements BattleCommand {
    private Character attacker;
    private Character target;
    private EffectManager effectManager = EffectManager.getInstance();
    private static final Random random = new Random();

    public AttackCommand(Character attacker, Character target) {
        this.attacker = attacker;
        this.target = target;
    }
    @Override
    public void execute() {

        if (!attacker.isAlive() || !target.isAlive()) {
            Utils.delayedPrint("⛔ Атака невозможна: один из бойцов уже повержен!");
            return;
        }

        if (effectManager.hasEffect(attacker, Stun.class)) {
            attacker.setState(new StunnedState(attacker));
            Utils.delayedPrint(String.format("⛔ %s пропускает ход, находясь в стане!", attacker.getName()));
            return;
        } else if (effectManager.hasEffect(attacker, Silence.class)) {
            attacker.setState(new SilenceState(attacker));
        } else {
            attacker.setState(new NormalState(attacker));
        }

        Character target = findTarget(attacker, this.target);

        System.out.println("                                  ");
        Utils.delayedPrint(String.format("------ ⚔ %s 🆚 %s ------", attacker.getName(), target.getName()));
        Utils.delayedPrint(String.format("🔥 %s атакует %s!", attacker.getName(), target.getName()));

        int abilityChance = (int) attacker.getStats().getTactics() / 2;

        if (abilityChance > 15) abilityChance = 15;

        if (random.nextInt(100) < (35 + abilityChance) && !attacker.getState().cannotCast()) {
            Utils.delayedPrint(String.format("✨ %s неожиданно использует свою способность перед атакой!", attacker.getName()));
            CombatController.getInstance().useAbility(attacker, target);
        }

        int baseDamage = attacker.calculateDamage();
        Utils.delayedPrint(String.format("🛠 Базовый урон: %d", baseDamage));

        double maxWeakness = effectManager.getBestEffect(attacker, Weakness.class,
                        (a, b) -> Double.compare(a.getReductionPercentage(), b.getReductionPercentage()))
                .map(Weakness::getReductionPercentage)
                .orElse(0.0);

        double maxBoost = effectManager.getBestEffect(attacker, BoostDamage.class,
                        (a, b) -> Double.compare(a.getDamageMultiplier(), b.getDamageMultiplier()))
                .map(BoostDamage::getDamageMultiplier)
                .orElse(0.0);

        if (maxWeakness > 0) {
            baseDamage *= (1 - maxWeakness);
            Utils.delayedPrint(String.format("⚠️ %s атакует под эффектом 'Слабость'! Урон снижен на %.0f%%. Новый урон: %d",
                    attacker.getName(), maxWeakness * 100, baseDamage));
        }

        if (maxBoost > 0) {
            baseDamage *= (1 + maxBoost);
            Utils.delayedPrint(String.format("🔥 %s атакует под эффектом 'Буст урона'! Урон увеличен на %.0f%%. Новый урон: %d",
                    attacker.getName(), maxBoost * 100, baseDamage));
        }

        target.setLastAttacker(attacker);
        new DefendCommand(target, attacker, baseDamage).execute();
    }

    private Character findTarget(Character attacker, Character target) {
        if (effectManager.hasEffect(attacker, Taunted.class)) {
            Taunted tauntEffect = (Taunted) effectManager.getEffect(attacker, Taunted.class);
            return tauntEffect.getTauntTarget();
        }

        return target;
    }
}
