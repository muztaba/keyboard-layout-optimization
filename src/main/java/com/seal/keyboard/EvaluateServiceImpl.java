package com.seal.keyboard;

import com.seal.util.Key;
import com.seal.util.dto.ObjectiveFunctionsValues;

import java.util.List;
import java.util.Map;

class EvaluateServiceImpl implements EvaluateService {

    private final Map<Character, Key> qwerty;
    private final Keyboard keyboard;

    EvaluateServiceImpl(Map<Character, Key> qwerty, Keyboard keyboard) {
        this.qwerty = qwerty;
        this.keyboard = keyboard;
    }

    @Override
    public ObjectiveFunctionsValues evaluate(String string, KeyMap<Character, String> keyMap) {
        List<Key> macro = KeyMapProcessor.load(keyMap, qwerty)
                .setString(string)
                .getKeyMap();
        return keyboard.evaluate(macro);
    }
}
