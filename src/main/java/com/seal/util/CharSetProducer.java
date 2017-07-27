package com.seal.util;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * Created by seal on 3/6/2017.
 */
public class CharSetProducer {

    private List<String> stringList;

    private static CharSetProducer instance;

    private CharSetProducer() {
        produceCharSet();
    }

    private CharSetProducer(Predicate predicate) {
        this();
    }

    private void produceCharSet() {
        List<String> list = new ArrayList<>();
        createNext26("", list);
        createNext26("+", list);
        for (int i = 'a'; i <= 'z'; i++) {
            createNext26((char) i + "+", list);
        }
        this.stringList = Collections.unmodifiableList(list);
    }

    private void createNext26(String prefix, List<String > list) {
        int v = prefix.isEmpty() ? -1 : prefix.codePointAt(0);
        for (int i = 'a'; i <= 'z'; i++) {
            if (v != i) {
                list.add(prefix + (char) i);
            }
        }
    }

    private void filter(Predicate predicate) {

    }

    private void check() {
        if (Objects.isNull(stringList)) {
            produceCharSet();
        }
    }

    public List<String> getCharSet() {
        check();
        return stringList;
    }

    public static CharSetProducer load() {
        if (Objects.isNull(instance)) {
            instance = new CharSetProducer();
        }
        return instance;
    }

    public static CharSetProducer load(Predicate predicate) {
        return new CharSetProducer(predicate);
    }
}
