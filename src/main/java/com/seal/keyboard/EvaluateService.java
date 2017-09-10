package com.seal.keyboard;

import com.seal.util.dto.ObjectiveFunctionsValues;

public interface EvaluateService {

    ObjectiveFunctionsValues evaluate(String string, KeyMap<Character, String> keyMap);
}
