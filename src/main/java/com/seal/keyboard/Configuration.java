package com.seal.keyboard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by seal on 10/19/2016.
 */
public class Configuration {
    private Map<String, Key> keyPosition;
    private Map<String, String> keyMap;


    public Configuration loadQwert(List<String> list) {
        keyPosition = new HashMap<>();

        for (String str : list) {
            String[] strs = str.split(" ");
            if (!keyPosition.containsKey(strs[0])) {
                Key key = Key.builder()
                        .setLatter('\0')
                        .setRow(Integer.parseInt(strs[1]))
                        .setCol(Integer.parseInt(strs[2]))
                        .setHand(hand(strs[3]))
                        .setFinger(finger(strs[4]))
                        .build();
                keyPosition.put(strs[0], key);

            }
        }
        return this;
    }

    public Configuration loadKeyMap(List<String> list) {
        keyMap = new HashMap<>();

        for (String str : list) {
            String[] strs = str.split(" ");
            if (keyMap.containsValue(strs[0]))
                throw new RuntimeException("Already keymap exit for" + strs[0] + " is " + strs[1]);
            keyMap.put(strs[0], strs[1]);
        }
        return this;
    }

    public Keyboard buildKeyboard() {
        Objects.requireNonNull(keyPosition, "KeyPosition is null");
        Objects.requireNonNull(keyMap, "keymap is null");
        return new Keyboard(keyPosition, keyMap);
    }

    private Hand hand(String str) {
        switch (str) {
            case "r" :
                return Hand.Right;
            case "l" :
                return Hand.Left;
        }
        throw new RuntimeException("No Hand Define");
    }

    private Finger finger(String str) {
        switch (str) {
            case "f":
                return Finger.Forfinger;
            case "m" :
                return Finger.Middleinger;
            case "r" :
                return Finger.Ringfinger;
            case "p":
                return Finger.Pinkie;
        }
        throw new RuntimeException("No Finger Define");
    }

}
