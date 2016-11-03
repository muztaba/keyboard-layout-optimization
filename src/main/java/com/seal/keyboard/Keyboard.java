package com.seal.keyboard;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by seal on 10/1/2016.
 */
public class Keyboard implements Serializable {

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


    public static class Values {

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
            keyPress = keyPressCounter(charSequence);
            Key prev = getKey(charSequence.charAt(0));  // avoiding null reference.

            for (int i = 1; i < charSequence.length(); i++) {
                char c = charSequence.charAt(i);
                if (isModifier(c)) continue;
                Key current = getKey(c);

                handAlternation += handAlternation(prev, current);
                distance += sameFingerUse(prev, current);
                bigStepDistance += bigStep(prev, current);

                prev = current;

            }

            return this;
        }


        private int keyPressCounter(CharSequence charSequence) {
            return charSequence.length();
        }


        private int handAlternation(Key prev, Key current) {
            return !sameHand(prev, current) ? 1 : 0;
        }


        private double sameFingerUse(Key prev, Key current) {
            double dist = 0.0;
            if (sameHand(prev, current) &&
                    sameFinger(prev, current)) {
                dist = prev.getPosition()
                        .distance(current
                                .getPosition());
            }
            return dist;
        }


        private double bigStep(Key prev, Key current) {
            double dist = 0.0;
            if (sameHand(prev, current) &&
                    !sameFinger(prev, current)) {
                dist = prev.getPosition()
                        .distance(current
                                .getPosition());
            }
            return dist;
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
