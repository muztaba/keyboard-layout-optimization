package com.seal.keyboard;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by seal on 10/1/2016.
 */
public class Keyboard implements Serializable {

    private final Map<String, Key> keyPosition;
    private final Map<String, String> keyMap;

    public Keyboard(Map<String, Key> keyPosition, Map<String, String> keyMap ) {
        this.keyPosition = keyPosition;
        this.keyMap = keyMap;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        keyPosition.forEach((k, v) ->
            builder.append(k).append(" ").append(v).append("\n"));
        builder.append("\n\n");
        keyMap.forEach((k, v) ->
                builder.append(k).append(" ").append(v).append("\n"));
        return builder.toString();
    }

    public ObjectiveFunction getObjectiveFunction() {
        return new ObjectiveFunction();
    }

    private class ObjectiveFunction implements com.seal.keyboard.ObjectiveFunction{

        @Override
        public int keyPress(String string) {
            return string.length();
        }

        @Override
        public int handAlternation(String string) {
            int count = 0;
            Hand prev = null;

            for (char c : string.toCharArray()) {
                if (!isModifier(c)) {
                    Hand _prev = keyPosition
                            .get(String.valueOf(c))
                            .getHand();

                    if (prev == null)
                        prev = _prev;
                    else if (prev == _prev)
                        count++;
                }
            }
            return count;
        }

        private boolean isModifier(char c) {
            return c == '+' || c == '-' || c == '=';
        }
    }
}
