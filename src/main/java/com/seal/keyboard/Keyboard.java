package com.seal.keyboard;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by seal on 10/1/2016.
 */
public class Keyboard {

    private static final Logger logger = LoggerFactory.getLogger(Keyboard.class);

    private final Map<String, Key> keyPosition;

    public Keyboard(Map<String, Key> keyPosition) {
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

    public static class Values implements Serializable {

        final int keyPress;
        final int handAlternation;
        final double distance;
        final double bigStepDistance;

        private Values(int keyPress, int handAlternation, double distance, double bigStepDistance) {
            this.keyPress = keyPress;
            this.handAlternation = handAlternation;
            this.distance = distance;
            this.bigStepDistance = bigStepDistance;
        }

        @Override
        public String toString() {
            return "Values{" +
                    "keyPress=" + keyPress +
                    ", handAlternation=" + handAlternation +
                    ", distance=" + distance +
                    ", bigStepDistance=" + bigStepDistance +
                    '}';
        }
    }

    public class ObjectiveFunction {

        int keyPress;
        int handAlternation;
        double distance;
        double bigStepDistance;

        public ObjectiveFunction evaluate(CharSequence charSequence) {
            keyPress = charSequence.length();
            Key prev = getKey(charSequence.charAt(0));
            for (int i = 1; i < charSequence.length(); i++) {
                char c = charSequence.charAt(i);
                if (isModifier(c))
                    continue;

                Key current = getKey(c);
                if (!sameHand(prev, current)) {
                    handAlternation++;
                } else {
                    if (sameFinger(prev, current)) {
                        distance += fingerDistance(prev, current);
                    } else {
                        bigStepDistance += fingerDistance(prev, current);
                    }
                }

                prev = current;
            }

            return this;
        }

        private double fingerDistance(Key prev, Key current) {
            return prev.getPosition().distance(current.getPosition());
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
            return keyPosition.get(String.valueOf(c));
        }

        public Values getValues() {
            return new Values(keyPress, handAlternation, distance, bigStepDistance);
        }

    }

}
