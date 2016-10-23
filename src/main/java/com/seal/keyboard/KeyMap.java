package com.seal.keyboard;

import java.util.Map;

/**
 * Created by seal on 10/23/16.
 */
public class KeyMap<C, M> {

    private Map<C, M> keyMap;

    public KeyMap(Map<C, M> keyMap) {
        this.keyMap = keyMap;
    }

    @SuppressWarnings("unchecked")
    public M getMap(C c) {
        if (keyMap.containsKey(c))
            return keyMap.get(c);
        return (M)"";
    }
}
