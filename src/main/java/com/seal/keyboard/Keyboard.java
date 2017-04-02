package com.seal.keyboard;

import com.seal.util.BigStepCoefficient;
import com.seal.util.Finger;
import com.seal.util.Key;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
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

    private boolean sameHand(Key prev, Key current) {
        return prev.getHand() == current.getHand();
    }

    private boolean sameFinger(Key prev, Key current) {
        return prev.getFinger() == current.getFinger();
    }

    public static class Values implements Serializable {

        final long keyPress;
        final long handAlternation;
        final double distance;
        final double bigStepDistance;
        final long hitDirection;

        private Values(long keyPress, long handAlternation, double distance, double bigStepDistance, long hitDirection) {
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

        private final Map<Finger, Finger> fingerMovementMap = new EnumMap<Finger, Finger>(Finger.class){{
            put(Finger.Pinkie, Finger.Ringfinger);
            put(Finger.Ringfinger, Finger.MiddleFinger);
            put(Finger.MiddleFinger, Finger.Forefinger);
            put(Finger.Forefinger, Finger.Pinkie);
        }};
        long keyPress;
        long handAlternation;
        double distance;
        double bigStepDistance;
        long hitDirection;

        public ObjectiveFunction evaluate(List<Key> macros) {
            if (macros == null || macros.isEmpty()) {
                logger.error("No string define");
                return this;
            }

            keyPress = calculateKeyPress(macros);
            Key prev = macros.get(0);
            for (int i = 1; i < macros.size(); i++) {
                Key current = macros.get(i);
                if (!sameHand(prev, current)) {
                    handAlternation++;
                } else if (sameFinger(prev, current)) {
                    distance += fingerDistance(prev, current);
                } else {
                    bigStepDistance += bigStep(prev, current);
                    hitDirection += hitDirectionCount(prev, current);
                }

                prev = current;
            }

            return this;
        }

        private void loadCalculation(Key key) {

        }

        private long calculateKeyPress(List<Key> macro) {
            // Remove all space from string, then return the length of that string.
            return  macro.stream()
                    .filter(i -> i.getLetter() != ' ')
                    .count();
        }



        private int hitDirectionCount(Key prev, Key current) {
            return fingerMovementMap.get(prev.getFinger()) != current.getFinger() ? 1 : 0;
        }

        private double fingerDistance(Key prev, Key current) {
            return prev.getPosition().distance(current.getPosition());
        }

        private double bigStep(Key prev, Key current) {
            return BigStepCoefficient.getCoefficient(prev.getFinger(), current.getFinger());
        }

        public Values getValues() {
            return new Values(keyPress, handAlternation, distance, bigStepDistance, hitDirection);
        }

    }

    public class FilterKeyMap {
        /*private final Predicate<String> filterPredicate = i -> {
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

        }*/
    }

}
