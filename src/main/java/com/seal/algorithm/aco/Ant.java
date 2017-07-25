package com.seal.algorithm.aco;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.DoubleStream;

/**
 * Created by seal on 5/10/2017.
 */
public class Ant {

    private PheromoneTable pheromoneTable;

    public void run(String string) {
        Set<Character> usedBanglaChar = new HashSet<>();
        Set<String> usedKeyMap = new HashSet<>();

        for (char c : string.toCharArray()) {
            if (!usedBanglaChar.contains(c)) {

            }
        }

    }

    private int selectKeyMap(char c, Set<String> usedKeyMap) {
        double[] array = pheromoneTable.getPheromoneArray(c);
        int newArrayLen = array.length - usedKeyMap.size();
        double[] newArray = new double[newArrayLen];

        double sum = DoubleStream.of(newArray)
                .sum();

        return 0; // should return index
    }

}
