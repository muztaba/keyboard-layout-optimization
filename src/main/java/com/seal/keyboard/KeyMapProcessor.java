package com.seal.keyboard;

import com.seal.util.Bangla;
import com.seal.util.Key;
import com.seal.util.SpacialKeyFactory;
import com.seal.util.StaticUtil;

import java.util.*;

/**
 * Created by seal on 10/23/16.
 */
public class KeyMapProcessor {


    private List<Key> macroList;

    private final KeyMap<Character, String> keyMap;
    private final Map<Character, Key> keyPosition;

    private KeyMapProcessor(KeyMap<Character, String> keyMap, Map<Character, Key> keyPosition) {
        this.keyMap = keyMap;
        this.keyPosition = keyPosition;
    }

    public static KeyMapProcessor load(KeyMap<Character, String> keyMap, Map<Character, Key> keyPosition) {
        return new KeyMapProcessor(keyMap, keyPosition);
    }

    public KeyMapProcessor setString(String string) {
        macroList = new ArrayList<>(string.length());

        for (char c : string.toCharArray()) {
            if (Character.isSpaceChar(c)) {
                Key prevKey = macroList.get(macroList.size() - 1);
                Key spaceKey = SpacialKeyFactory.getKey(prevKey.getHand(), SpacialKeyFactory.SpacialKey.Space);
                macroList.add(spaceKey);

            } else if (StaticUtil.isBangla(c)) {
                String keymap = get(c);
                macroList.addAll(getMacro(keymap));
            }
        }

        return this;
    }

    private List<Key> getMacro(String str) {
        List<Key> keyList = null;
        if (str.length() == 1) {
            keyList = new ArrayList<>(1);
            keyList.add(getKeyPosition(str.charAt(0)));
        } else {
            keyList = new ArrayList<>(str.length());
            for (int i = 0; i < str.length();) {
                char c = str.charAt(i);
                if (isModifier(c)) {
                    Key nextKey = getKeyPosition(str.charAt(i + 1));
                    Key spacialKey = SpacialKeyFactory.getKey(nextKey.getHand(), SpacialKeyFactory.SpacialKey.Shift);

                    keyList.add(spacialKey);
                    keyList.add(nextKey);
                    i += 2;
                } else {
                    keyList.add(getKeyPosition(str.charAt(i)));
                    i++;
                }
            }

        }

        return keyList;
    }

    private String get(int c) {
        return keyMap.getMap((char) c);
    }

    private Key getKeyPosition(char c) {
        return keyPosition.get(c);
    }

    public List<Key> getKeyMap() {
        Objects.requireNonNull(macroList, "String is not set");
        return Collections.unmodifiableList(this.macroList);
    }

    private boolean isModifier(char c) {
        return c == '+';
    }
}
