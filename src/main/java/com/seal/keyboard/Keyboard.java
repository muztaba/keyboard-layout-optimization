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

    private class ObjectiveFunction implements com.seal.keyboard.ObjectiveFunction {

        @Override
        public int keyPress(String string) {
            return string.length();
        }

        @Override
        public int handAlternation(String string) {
            int count = 0;
            Hand prev = null;
            for (char c : string.toCharArray()) {
                if (isModifier(c))
                    continue;
                try {
                    boolean t = sameHand(c, prev);
                    count += t ? 1 : 0;
                    prev = t ? prev : getKey(c).getHand();
                } catch (NullPointerException e) {
                    logger.warn("Can't find any keymap of {} letter", c);
                }
            }

            return count;
        }

        private boolean sameHand(char c, Hand prev) {
            return (getKey(c).getHand() == prev);
        }

        private Key getKey(char c) {
            return keyPosition.get(String.valueOf(c));
        }

        @Override
        public double sameFingerUse(String str) {
            double count = 0.0;
            Key prevKey = null;

            for (char c : str.toCharArray()) {
                boolean t = sameHand(c, (Objects.isNull(prevKey)) ? null : prevKey.getHand());

                if (t) count += prevKey.getPosition()
                        .distance(getKey(c)
                                .getPosition());
                else
                    prevKey = getKey(c);
            }
            return count;
        }

        private boolean isModifier(char c) {
            return c == '+' || c == '-' || c == '=';
        }
    }
}
