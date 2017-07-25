package com.seal.algorithm.montecarlo;

import com.seal.util.CollectionUtil;

import java.util.*;

/**
 * Created by seal on 7/25/17.
 */
public class MonteCarlo {

    public Map<Character, String> run(List<Character> banglaCharList, List<String> charSet) {
        Random random = new Random();
        List<String> chars = CollectionUtil.copyOf(charSet);
        Map<Character, String> result = new HashMap<>();

        for (char c : banglaCharList) {
            int index = random.nextInt(chars.size());
            result.put(c, chars.get(index));
            chars.remove(index);
        }
        return result;
    }
}
