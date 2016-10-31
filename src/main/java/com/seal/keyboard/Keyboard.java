package com.seal.keyboard;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

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
        StringBuilder builder = new StringBuilder();
        keyPosition.forEach((k, v) ->
                builder.append(k).append(" ").append(v).append("\n"));
        return builder.toString();
    }

    public ObjectiveFunction getObjectiveFunction() {
        return new ObjectiveFunction();
    }


    private class ObjectiveFunction {

        int keyPress;
        int handAlternation;
        double distance;
        double bigStepDistance;

        public void evalute(CharSequence charSequence) {
            keyPress = keyPressCounter(charSequence);
            Key prev = null;
            for (int i = 0; i < charSequence.length(); i++) {
                char c = charSequence.charAt(i);
                if (isModifier(c)) continue;
                Key current = getKey(c);

                handAlternation += handAlternation(prev, current);
                distance += sameFingerUse(prev, current);
                bigStepDistance += bigStep(prev, current);

                prev = current;

            }
        }

        private int keyPressCounter(CharSequence charSequence) {
            return charSequence.length();
        }

        private int handAlternation(Key prev, Key current) {
            return sameHand(prev, current) ? 1 : 0;
        }

        private double sameFingerUse(Key prev, Key current) {
            double dist = 0.0;
            if (!sameHand(prev, current) &&
                    prev.getFinger() == current.getFinger()) {
                dist = prev.getPosition()
                        .distance(current
                                .getPosition());
            }
            return dist;
        }

        private double bigStep(Key prev, Key current) {
            double dist = 0.0;
            if (!sameHand(prev, current) &&
                    prev.getFinger() != current.getFinger()) {
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
            if (Objects.isNull(prev) || Objects.isNull(current))
                return false;
            return prev.getHand() != current.getHand();
        }


        private Key getKey(char c) {
            return keyPosition.get(String.valueOf(c));
        }

    }

}
