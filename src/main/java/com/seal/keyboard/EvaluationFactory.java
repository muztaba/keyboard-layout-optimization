package com.seal.keyboard;

import com.seal.io.IO;
import com.seal.util.Key;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Objects;

public class EvaluationFactory {

    private static final Logger logger = LoggerFactory.getLogger(EvaluationFactory.class);

    private static EvaluateService evaluateService;

    private static Map<Character, Key> qwerty;
    private static Keyboard keyboard;

    public static EvaluateService getService() {
        init();
        EvaluateService service = null;
        if (Objects.isNull(evaluateService)) {
            synchronized (EvaluateService.class) {
                if (Objects.isNull(evaluateService)) {
                    evaluateService = new EvaluateServiceImpl(qwerty, keyboard);
                }
                service = evaluateService;
            }
        }
        return service;
    }

    private static void init() {
        if (Objects.isNull(qwerty)) {
            qwerty = Init.loadQwert(IO.streamOf("qwerty.txt"));
        }
        if (Objects.isNull(keyboard)) {
            keyboard = Keyboard.getInstance(qwerty);
        }
    }

}
