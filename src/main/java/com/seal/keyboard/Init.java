package com.seal.keyboard;

import com.seal.util.Key;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by seal on 10/19/2016.
 */
public class Init {

    private static final Logger logger = LoggerFactory.getLogger(Init.class);

    private static final Predicate<String[]> check = i -> {
        if (i.length == 2) {
            return true;
        } else {
            logger.warn("Bad keymap of {}", i[0]);
            return false;
        }
    };

    public static Map<Character, Key> loadQwert(Stream<String> stream) {
        Map<Character, Key> keyPosition = stream
                .map(i -> i.split(" "))
                .map(Key::build)
                .collect(Collectors.toMap(Key::getLetter, Function.identity()));
        return keyPosition;
    }

    public static KeyMap<Character, String> loadKeyMap(Stream<String> stream) {
        Map<Character, String> map = stream
                .map(i -> i.split(" "))
                .filter(check)
                .collect(Collectors.toMap(i -> i[0].charAt(0), i -> i[1]));
        return new KeyMap<>(map);
    }

    public static List<Character> loadBanglaChars(Stream<String> stream) {
        return stream.map(String ::trim)
                .map(i -> i.charAt(0))
                .collect(Collectors.toList());
    }

}
