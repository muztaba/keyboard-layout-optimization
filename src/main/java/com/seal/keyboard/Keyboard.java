package com.seal.keyboard;

import com.seal.util.BigStepCoefficient;
import com.seal.util.Finger;
import com.seal.util.Key;
import com.seal.util.dto.ObjectiveFunctionsValues;
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

    public class ObjectiveFunction {

        private final Map<Finger, Finger> fingerMovementMap = new EnumMap<Finger, Finger>(Finger.class){{
            put(Finger.Pinkie, Finger.Ringfinger);
            put(Finger.Ringfinger, Finger.MiddleFinger);
            put(Finger.MiddleFinger, Finger.Forefinger);
            put(Finger.Forefinger, Finger.Pinkie);
        }};


        public ObjectiveFunctionsValues evaluate(List<Key> macros) {
            if (macros == null || macros.isEmpty()) {
                logger.error("No string define");
                return ObjectiveFunctionsValues.builder().build();
            }

            long keyPress, handAlternation ,hitDirection ;
            double distance, bigStepDistance;
            keyPress = handAlternation = hitDirection = 0L;
            distance = bigStepDistance = 0.0;

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

            return ObjectiveFunctionsValues.builder()
                    .setKeyPress(keyPress)
                    .setHandAlternation(handAlternation)
                    .setHitDirection(hitDirection)
                    .setDistance(distance)
                    .setBigStepDistance(bigStepDistance)
                    .build();
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
