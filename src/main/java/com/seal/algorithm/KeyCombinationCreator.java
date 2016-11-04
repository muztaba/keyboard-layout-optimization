package com.seal.algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by seal on 11/4/2016.
 */
public class KeyCombinationCreator {

    private static List<String > keyCombinationList;

    public static List<String > keyCombinationCreate() {
        keyCombinationList = new ArrayList<>();
        createNext26("");
        createNext26("+");
        for (int i = 'a'; i <= 'z'; i++) {
            createNext26((char) i + "+");
        }
        return keyCombinationList;
    }

    private static void createNext26(String prefix) {
        int v = prefix.isEmpty() ? -1 : prefix.codePointAt(0);
        for (int i = 'a'; i <= 'z'; i++) {
            if (v != i) {
                keyCombinationList.add(prefix + (char) i);
            }
        }
    }
}
