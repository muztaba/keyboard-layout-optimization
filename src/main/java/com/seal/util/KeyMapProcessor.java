package com.seal.util;

import com.seal.keyboard.KeyMap;

import java.util.Objects;

/**
 * Created by seal on 10/23/16.
 */
public class KeyMapProcessor {

    private static final int BANGLA_START = 0x0980;
    private static final int BANGLA_END = 0x09FF;
    private static final int NUMBER_START = 0x09E6;
    private static final int NUMBER_END = 0x09EF;

    private StringBuilder builder;

    private KeyMap<Character, String> keyMap;

    private KeyMapProcessor(KeyMap<Character, String> keyMap) {
        this.keyMap = keyMap;
    }

    public static KeyMapProcessor getInstance(KeyMap<Character, String> keyMap) {
        return new KeyMapProcessor(keyMap);
    }

    public KeyMapProcessor setString(String string) {
        this.builder = new StringBuilder();
        string.chars()
                .filter(i -> !Character.isSpaceChar(i))
                .filter(this::isBangla)
                .forEach(i -> {
                    builder.append(keyMap.getMap((char) i));
                });

        /*for (int i = 0; i < string.length(); i++) {
            int codePoint = string.codePointAt(i);
            if (!Character.isSpaceChar(codePoint) && isBangla(codePoint))
                builder.append(keyMap.getMap(string.charAt(i)));
        }*/

        return this;
    }

    public String getKeyMap() {
        Objects.requireNonNull(builder, "String is not set");
        return builder.toString();
    }

    private boolean isBangla(int codePoint) {
        return (codePoint >= BANGLA_START && codePoint <= BANGLA_END) &&
                (codePoint <= NUMBER_START || codePoint >= NUMBER_END);
    }

}
