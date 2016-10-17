package com.seal.keyboard;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

/**
 * Created by seal on 10/1/2016.
 */
public class Keyboard implements Serializable {

    private final Map<String, Key> keyMap;

    public Keyboard(Map<String, Key> keyMap) {
        this.keyMap = keyMap;
    }

}
