package com.seal.keyboard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by seal on 10/19/2016.
 */
public class Configuration {

    public Keyboard loadQwert(List<String> list) {
        Map<String, Key> keyMap = new HashMap<>();

        for (String str : list) {
            String[] strs = str.split(" ");
            if (!keyMap.containsKey(strs[0])) {
                Key key = Key.builder()
                        .setLatter('\0')
                        .setRow(Integer.parseInt(strs[1]))
                        .setCol(Integer.parseInt(strs[2]))
                        .setHand(hand(strs[3]))
                        .setFinger(finger(strs[4]))
                        .build();
                keyMap.put(strs[0], key);

            }
        }
        return new Keyboard(keyMap);
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
