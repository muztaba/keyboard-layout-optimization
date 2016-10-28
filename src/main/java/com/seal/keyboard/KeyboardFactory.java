package com.seal.keyboard;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by seal on 10/19/2016.
 */
public class KeyboardFactory {

    private static final Logger logger = LoggerFactory.getLogger(KeyboardFactory.class);

    private Map<String, Key> keyPosition;

    public static Keyboard loadQwert(List<String> list) {
        Map<String, Key> keyPosition = new HashMap<>();
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

        return new Keyboard(keyPosition);
    }

    public static KeyMap<Character, String> loadKeyMap(List<String > list) {
        Map<Character, String> map = list.stream()
                .map(i -> i.split(" "))
                .filter(check )
                .collect(Collectors.toMap(i -> i[0].charAt(0), i -> i[1]));

        return new KeyMap<>(map);
    }

    private static Predicate<String[]> check = i -> {
        if (i.length == 2) {
            return true;
        } else {
            logger.warn("Bad keymap of {}", i[0]);
            return false;
        }
    };

    private static Hand hand(String str) {
        switch (str) {
            case "r" :
                return Hand.Right;
            case "l" :
                return Hand.Left;
        }
        throw new RuntimeException("No Hand Define");
    }

    private static Finger finger(String str) {
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
