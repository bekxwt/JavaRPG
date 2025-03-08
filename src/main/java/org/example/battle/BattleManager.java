package org.example.battle;

import lombok.Setter;
import org.example.effects.EffectManager;
import org.example.models.Character;
import org.example.state.NormalState;
import javafx.application.Platform;
import org.example.utils.Utils;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class BattleManager {

    private List<Character> team1;
    private List<Character> team2;
    private List<Character> turnOrder = new ArrayList<>();
    private CombatController combatController = CombatController.getInstance();
    private Random random = new Random();
    private EffectManager effectManager = EffectManager.getInstance();
    @Setter
    private Consumer<String> battleLogger;
    @Setter
    private Consumer<String> winnerCallback;


    public BattleManager(List<Character> team1, List<Character> team2) {
        this.team1 = team1;
        this.team2 = team2;

        team1.forEach(c -> c.setBattleManager(this));
        team2.forEach(c -> c.setBattleManager(this));
    }

    public void startBattle() {
        new Thread(() -> {
            System.out.println("🔥 Битва 2x2 начинается! 🔥");

            turnOrder.addAll(team1);
            turnOrder.addAll(team2);
            turnOrder.sort(Comparator.comparingInt(c -> -c.getStats().getInitiation()));

            int count = 1;

            while (isBattleOngoing()) {
                Utils.delayedPrint("\n------- Ход " + count + " -------");
                Utils.delayedPrint("Порядок ходов: " + getTurnOrderString());

                for (Character fighter : turnOrder) {
                    if (!isBattleOngoing()) break;
                    if (!fighter.isAlive()) continue;

                    fighter.reduceCooldowns();
                    processFighterTurn(fighter);
                }

                endTurn();
                nextTurn();
                count++;
            }
            announceWinner();
        }).start();
    }

    private void processFighterTurn(Character fighter) {
        List<Character> enemyTeam = getEnemiesFor(fighter);
        Character target = findRandomOpponent(enemyTeam);

        if (target != null) {
            combatController.attack(fighter, target);
        }
    }

    private String getTurnOrderString() {
        return turnOrder.stream()
                .map(Character::getName)
                .collect(Collectors.joining(" → "));
    }

    public List<Character> getEnemiesFor(Character caster) {
        return team1.contains(caster) ? team2 : team1;
    }

    private boolean isBattleOngoing() {
        return team1.stream().anyMatch(Character::isAlive) && team2.stream().anyMatch(Character::isAlive);
    }

    private Character findRandomOpponent(List<Character> enemyTeam) {
        List<Character> aliveEnemies = enemyTeam.stream().filter(Character::isAlive).toList();
        return aliveEnemies.isEmpty() ? null : aliveEnemies.get(random.nextInt(aliveEnemies.size()));
    }

    private void nextTurn() {
        turnOrder.forEach(fighter -> fighter.setState(new NormalState(fighter)));
    }

    private void announceWinner() {
        String winner = team1.stream().anyMatch(Character::isAlive)
                ? "🎉 Победила команда 1! 🎉"
                : "🎉 Победила команда 2! 🎉";

        if (battleLogger != null) {
            battleLogger.accept(winner);
        }

        if (winnerCallback != null) {
            winnerCallback.accept(winner);
        }
    }

    public void endTurn() {
        effectManager.decreaseEffectDurations();
    }

    public Character findWeakestAlly(Character caster) {
        List<Character> team = team1.contains(caster) ? team1 : team2;
        return team.stream().filter(Character::isAlive).min(Comparator.comparingInt(Character::getHealth)).orElse(null);
    }

    public List<Character> getAlliesFor(Character character) {
        return team1.contains(character) ? team1 : team2;
    }
}