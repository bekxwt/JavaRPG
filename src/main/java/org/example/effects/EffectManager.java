package org.example.effects;

import org.example.models.Character;
import org.example.utils.Utils;

import java.util.*;

public class EffectManager {
    private static EffectManager instance;
    private Map<Character, List<Effect>> activeEffects = new HashMap<>();

    private EffectManager() {}

    public static EffectManager getInstance() {
        if (instance == null) {
            instance = new EffectManager();
        }
        return instance;
    }

    public void applyEffect(Character target, Effect effect) {
        activeEffects.computeIfAbsent(target, k -> new ArrayList<>()).add(effect);
        effect.apply(target);
        Utils.delayedPrint("✨ " + target.getName() + " получает эффект: " + effect.getClass().getSimpleName());
    }

    public boolean hasEffect(Character target, Class<? extends Effect> effectClass) {
        return activeEffects.getOrDefault(target, new ArrayList<>())
                .stream()
                .anyMatch(effect -> effect.getClass().equals(effectClass));
    }

    public Effect getEffect(Character target, Class<? extends Effect> effectClass) {
        return activeEffects.getOrDefault(target, new ArrayList<>())
                .stream()
                .filter(effect -> effect.getClass().equals(effectClass))
                .findFirst()
                .orElse(null);
    }

    public void decreaseEffectDurations() {
        List<Character> toRemove = new ArrayList<>();

        for (Map.Entry<Character, List<Effect>> entry : activeEffects.entrySet()) {
            Character target = entry.getKey();
            List<Effect> effects = entry.getValue();

            effects.removeIf(effect -> {
                effect.update(target);
                effect.decreaseDuration();
                if (effect.getDuration() <= 0) {
                    effect.remove(target);
                    Utils.delayedPrint("❌ " + target.getName() + " теряет эффект: " + effect.getClass().getSimpleName());
                    return true;
                }
                return false;
            });

            if (effects.isEmpty()) {
                toRemove.add(target);
            }
        }

        for (Character character : toRemove) {
            activeEffects.remove(character);
        }
    }

    public <T extends Effect> Optional<T> getBestEffect(Character target, Class<T> effectClass, Comparator<T> comparator) {
        return activeEffects.getOrDefault(target, Collections.emptyList())
                .stream()
                .filter(effectClass::isInstance)
                .map(effectClass::cast)
                .max(comparator);
    }
}
