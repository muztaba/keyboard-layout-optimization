package com.seal.keyboard;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by seal on 10/1/2016.
 */
public class Keyboard {

    private static final Logger logger = LoggerFactory.getLogger(Keyboard.class);

    private final Map<Character, Key> keyPosition;

    public Keyboard(Map<Character, Key> keyPosition) {
        this.keyPosition = keyPosition;
    }

    @Override
    public String toString() {
        return keyPosition.entrySet()
                .stream()
                .map(Map.Entry::toString)
                .collect(Collectors.joining("\n"));
    }

    public ObjectiveFunction getObjectiveFunction() {
        return new ObjectiveFunction();
    }

    public FilterKeyMap getFilterKeyMap() {
        return new FilterKeyMap();
    }

    private boolean isModifier(char c) {
        return c == '+' || c == '-' || c == '=';
    }

    private boolean sameHand(Key prev, Key current) {
        return prev.getHand() == current.getHand();
    }

    private boolean sameFinger(Key prev, Key current) {
        return prev.getFinger() == current.getFinger();
    }

    private Key getKey(char c) {
        return keyPosition.get(c);
    }

    public static class Values implements Serializable {

        final int keyPress;
        final int handAlternation;
        final double distance;
        final double bigStepDistance;
        final int hitDirection;

        private Values(int keyPress, int handAlternation, double distance, double bigStepDistance, int hitDirection) {
            this.keyPress = keyPress;
            this.handAlternation = handAlternation;
            this.distance = distance;
            this.bigStepDistance = bigStepDistance;
            this.hitDirection = hitDirection;
        }

        @Override
        public String toString() {
            return "Values{" +
                    "keyPress=" + keyPress +
                    ", handAlternation=" + handAlternation +
                    ", distance=" + distance +
                    ", bigStepDistance=" + bigStepDistance +
                    ", hitDirection=" + hitDirection +
                    '}';
        }
    }

    public class ObjectiveFunction {

        private final Map<Finger, Finger> fingerMovementMap = new EnumMap<>(Finger.class);
        int keyPress;
        int handAlternation;
        double distance;
        double bigStepDistance;
        int hitDirection;

        private ObjectiveFunction() {
            fingerMovementMap.put(Finger.Pinkie, Finger.Ringfinger);
            fingerMovementMap.put(Finger.Ringfinger, Finger.MiddleFinger);
            fingerMovementMap.put(Finger.MiddleFinger, Finger.Forefinger);
            fingerMovementMap.put(Finger.Forefinger, Finger.Pinkie);
        }

        public ObjectiveFunction evaluate(String charSequence) {
            if (charSequence == null || charSequence.isEmpty()) {
                logger.error("No key map define");
                return this;
            }

            keyPress = charSequence.length();
            Key prev = getKey(charSequence.charAt(0));
            for (int i = 1; i < charSequence.length(); i++) {
                char c = charSequence.charAt(i);
                if (isModifier(c))
                    continue;

                Key current = getKey(c);
                if (!sameHand(prev, current)) {
                    handAlternation++;
                } else if (sameFinger(prev, current)) {
                    distance += fingerDistance(prev, current);
                } else {
                    bigStepDistance += fingerDistance(prev, current);
                    hitDirection += hitDirectionCount(prev, current);
                }

                prev = current;
            }

            return this;
        }

        private int hitDirectionCount(Key prev, Key current) {
            return fingerMovementMap.get(prev.getFinger()) != current.getFinger() ? 1 : 0;
        }

        private double fingerDistance(Key prev, Key current) {
            return prev.getPosition().distance(current.getPosition());
        }

        public Values getValues() {
            return new Values(keyPress, handAlternation, distance, bigStepDistance, hitDirection);
        }

    }

    public class FilterKeyMap {
        private final Predicate<String> filterPredicate = i -> {
            if (i.length() == 1) return true;
            if (i.length() == 2) return true;

            Key first = getKey(i.charAt(0)),
                    second = getKey(i.charAt(2));
            if (sameHand(first, second))
                return false;
            if (sameFinger(first, second))
                return false;

            return true;
        };

        public List<String> filter(List<String> list) {
            return list.stream()
                    .filter(i -> i.length() <= 3)
                    .filter(filterPredicate)
                    .collect(Collectors.toList());

        }
    }

}
