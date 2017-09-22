package com.seal.algorithm.aco;

import com.seal.keyboard.KeyMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

/**
 * Created by seal on 5/10/2017.
 */
public class Ant {

    private static final Logger logger = LoggerFactory.getLogger(Ant.class);
    private static final Random rand = new Random();

    public final String antId;
    private final PheromoneTable pheromoneTable;
    private final List<Character> banglaChars;
    private final List<String> charSet;

    public Ant(String antId, PheromoneTable pheromoneTable, List<Character> banglaChars, List<String> charSet) {
        this.antId = antId;
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
                pheromoneTable.evaporate(c, index, .98);
                usedBanglaChar.add(c);
                usedColumnIndex.add(index);
            }
        }
        // remaining bangla char set randomly assigned
        if (usedBanglaChar.size() != banglaChars.size()) {
            assignRemainCharsSet(keyMap, usedColumnIndex, usedBanglaChar);
        }

        return new KeyMap<>(keyMap);
    }

    private void assignRemainCharsSet(Map<Character, String> keymap,
                                      Set<Integer> usedColumnIndex,
                                      Set<Character> usedBanglaChar) {
        final List<Integer> unassignedIndexes = getUnassignedIndexes(usedColumnIndex);
        banglaChars.stream()
                .filter(ch -> !usedBanglaChar.contains(ch))
                .forEach(ch -> {
                    int index = unassignedIndexes.get(rand.nextInt(unassignedIndexes.size()));
                    keymap.put(ch, charSet.get(index));
                    unassignedIndexes.remove(Integer.valueOf(index));
                });
    }

    private List<Integer> getUnassignedIndexes(Set<Integer> usedColumnIndex) {
        return IntStream.range(0, charSet.size())
                .filter(i -> !usedColumnIndex.contains(i))
                .boxed()
                .collect(Collectors.toList());
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

    private static class Node implements Comparable<Node> {
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
