package com.seal.keyboard;

import java.util.Objects;

/**
 * Created by seal on 10/23/16.
 */
public class KeyMapProcessor {

    private static final int BANGLA_CHAR_START = 0x0980;
    private static final int BANGLA_CHAR_END = 0x09FF;
    private static final int BANGLA_NUMBER_START = 0x09E6;
    private static final int BANGLA_NUMBER_END = 0x09EF;

    private String str;

    private KeyMap<Character, String> keyMap;

    private KeyMapProcessor(KeyMap<Character, String> keyMap) {
        this.keyMap = keyMap;
    }

    public static KeyMapProcessor getInstance(KeyMap<Character, String> keyMap) {
        return new KeyMapProcessor(keyMap);
    }

    public KeyMapProcessor setString(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        for (char c : string.toCharArray()) {
            if (Character.isSpaceChar(c))
                stringBuilder.append(c);
            else if (isBangla(c))
                stringBuilder.append(get(c));
        }
        this.str = stringBuilder.toString();

        return this;
    }

    private String get(int c) {
        return keyMap.getMap((char) c);
    }

    public String getKeyMap() {
        Objects.requireNonNull(str, "String is not set");
        return str;
    }

    private boolean isBangla(int codePoint) {
        return ((codePoint >= BANGLA_CHAR_START && codePoint <= BANGLA_CHAR_END) &&
                (codePoint <= BANGLA_NUMBER_START || codePoint >= BANGLA_NUMBER_END));
    }

}