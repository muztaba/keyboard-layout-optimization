package com.seal.util;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by seal on 3/6/2017.
 */
public enum   CharSetProducer {

    CHAR_SET_PRODUCER;

    private List<String> stringList;

    CharSetProducer() {
        produceCharSet();
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

    public List<String> getCharSet() {
        return stringList;
    }
}
