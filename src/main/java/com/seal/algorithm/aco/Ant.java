package com.seal.algorithm.aco;

import com.seal.keyboard.KeyMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.DoubleStream;

/**
 * Created by seal on 5/10/2017.
 */
public class Ant {

    private static final Logger logger = LoggerFactory.getLogger(Ant.class);

    private final PheromoneTable pheromoneTable;
    private final List<Character> banglaChars;
    private final List<String> charSet;

    public Ant(PheromoneTable pheromoneTable, List<Character> banglaChars, List<String> charSet) {
        this.pheromoneTable = pheromoneTable;
        this.banglaChars = banglaChars;
        this.charSet = charSet;
    }

    public KeyMap<Character, String> run(String string) {
        Set<Character> usedBanglaChar = new HashSet<>(banglaChars.size());
        Set<Integer> usedColumnIndex = new HashSet<>(charSet.size());
        Map<Character, String> keyMap = new HashMap<>(banglaChars.size());

        for (char c : string.toCharArray()) {
            if (!usedBanglaChar.contains(c)) {
                int index = selectKeyMap(c, usedColumnIndex);
                keyMap.put(c, charSet.get(index));
                pheromoneTable.evaporate(c, index, .75 /*Should be from config file*/);
                usedBanglaChar.add(c);
            }
        }
        return new KeyMap<>(keyMap);
    }

    private int selectKeyMap(char c, Set<Integer> usedColumnIndex) {
        double[] array = pheromoneTable.getPheromoneArray(c);
        int newArrayLen = array.length - usedColumnIndex.size();
        double[] newArray = new double[newArrayLen];
        List<Integer> index = new ArrayList<>(newArrayLen);
        int j = 0;
        for (int i = 0; i < array.length; i++) {
            if (!usedColumnIndex.contains(i)) {
                index.add(i);
                newArray[j++] = array[i];
            }
        }

        double sum = DoubleStream.of(newArray)
                .sum();

        PriorityQueue<Node> queue = new PriorityQueue<>(newArrayLen);
        for (int i = 0; i < newArrayLen; i++) {
            double probability = newArray[i] / sum;
            queue.add(new Node(index.get(i), probability));
        }
        return queue.poll().index; // should return index
    }

    private static class Node implements Comparable<Node>{
        final int index;
        final double probability;

        public Node(int index, double probability) {
            this.index = index;
            this.probability = probability;
        }

        @Override
        public int compareTo(Node o) {
            return Double.compare(o.probability, this.probability);
        }
    }

}
