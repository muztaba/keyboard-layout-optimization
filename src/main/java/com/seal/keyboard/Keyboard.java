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
            int count = 0;
            String[] strs = Util.split(string, " ");
            for (String str : strs) {
                for (char c : str.toCharArray()) {
                    String ch = String.valueOf(c);
                    if (keyMap.containsKey(ch))
                        count += keyMap.get(ch).length();
                }
            }
            return count;
        }
    }

    private static class Util {
        public static String[] split(String str, String regex) {
            return str.split(regex);
        }
    }
}
