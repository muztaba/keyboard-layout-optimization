package com.seal.keyboard;

import com.seal.util.Key;
import com.seal.util.StaticUtil;
import com.seal.util.dto.ObjectiveFunctionsValues;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
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

        public ObjectiveFunctionsValues evaluate(List<Key> macros) {
            if (macros == null || macros.isEmpty()) {
                logger.error("No string define");
                return ObjectiveFunctionsValues.builder().build();
            }

            // Declaration and Initialization
            long keyPress = 0, handAlternation = 0, hitDirection = 0;
            double distance = 0.0, bigStepDistance = 0.0, load = 0.0;
            // Objective Function Calculation
            load = loadCalculation(macros);
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

            // Return the Result for Given Macros
            return ObjectiveFunctionsValues.builder()
                    .setLoad(load)
                    .setKeyPress(keyPress)
                    .setHandAlternation(handAlternation)
                    .setHitDirection(hitDirection)
                    .setDistance(distance)
                    .setBigStepDistance(bigStepDistance)
                    .build();
        }

        private double loadCalculation(List<Key> macros) {
            // frequency of monograph
            Map<Key, Long> frequencyOfMonoGraph = macros.stream()
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

            // calculate optimal load distribution
            Map<Key, Double> optLoadDistribution = frequencyOfMonoGraph.entrySet()
                    .stream()
                    .collect(Collectors.toMap(Map.Entry::getKey,
                            i ->
                                    i.getValue()
                                            * StaticUtil.getRow(i.getKey().getPosition().x) / 100.0
                                            * StaticUtil.getCol(i.getKey().getPosition().y) / 100.0
                                            * 0.50
                    ));

            // return load distribution of that particular keyboard layout
            return frequencyOfMonoGraph.entrySet()
                    .stream()
                    .mapToDouble(i -> Math.pow(i.getValue() - optLoadDistribution.get(i.getKey()), 2))
                    .sum();
        }

        private long calculateKeyPress(List<Key> macro) {
            // Remove all space from string, then return the length of that string.
            return macro.stream()
                    .filter(i -> i.getLetter() != ' ')
                    .count();
        }


        private int hitDirectionCount(Key prev, Key current) {
            return StaticUtil.nextFinger(prev.getFinger()) != current.getFinger() ? 1 : 0;
        }

        private double fingerDistance(Key prev, Key current) {
            return prev.getPosition().distance(current.getPosition());
        }

        private double bigStep(Key prev, Key current) {
            return StaticUtil.getCoefficient(prev.getFinger(), current.getFinger());
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
