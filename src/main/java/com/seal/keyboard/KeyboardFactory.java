package com.seal.keyboard;

import com.seal.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by seal on 10/19/2016.
 */
public class KeyboardFactory {

    private static final Logger logger = LoggerFactory.getLogger(KeyboardFactory.class);
    private static final Predicate<String[]> check = i -> {
        if (i.length == 2) {
            return true;
        } else {
            logger.warn("Bad keymap of {}", i[0]);
            return false;
        }
    };

    public static Keyboard loadQwert(String path) {
        Map<Character, Key> keyPosition = FileUtil.lines(path)
                .stream()
                .map(i -> i.split(" "))
                .map(Key::build)
                .collect(Collectors.toMap(Key::getLetter, Function.identity()));

        return new Keyboard(keyPosition);
    }

    public static KeyMap<Character, String> loadKeyMap(String path) {
        Map<Character, String> map = FileUtil.lines(path)
                .stream()
                .map(i -> i.split(" "))
                .filter(check)
                .collect(Collectors.toMap(i -> i[0].charAt(0), i -> i[1]));

        return new KeyMap<>(map);
    }

}
