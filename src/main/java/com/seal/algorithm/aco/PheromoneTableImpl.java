package com.seal.algorithm.aco;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Created by seal on 5/10/2017.
 */
public class PheromoneTableImpl implements PheromoneTable {

    private Map<Character, double[]> pheromoneTable;

    private PheromoneTableImpl() {
    }

    @Override
    public double[] getPheromoneArray(char c) {
        double[] array = pheromoneTable.get(c);
        double[] newArray = new double[array.length];
        System.arraycopy(array, 0, newArray, 0, array.length);
        return newArray;
    }

    @Override
    public void evaporate(char c, int index, double evaporateRate) {
        pheromoneTable.compute(c, (k, v) -> {
            v[index] = v[index] * evaporateRate;
            return v;
        });
    }

    @Override
    public void updatePheromoneTable(Function<Double, Double> updateFunction) {
        for (double[] array : pheromoneTable.values()) {
            for (int i = 0; i < array.length; i++) {
                array[i] = updateFunction.apply(array[i]);
            }
        }
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public static class Builder {
        private List<Character> banglaUnicodeChar;
        private List<String> charSet;
        private double initialValue;

        public Builder setBanglaUnicodeCharList(List<Character> list) {
            this.banglaUnicodeChar = list;
            return this;
        }

        public Builder setCharSetList(List<String> list) {
            this.charSet = list;
            return this;
        }

        public Builder setInitialValue(double value) {
            this.initialValue = value;
            return this;
        }

        public PheromoneTableImpl build() {
            PheromoneTableImpl object = new PheromoneTableImpl();
            int charSetLen = charSet.size();
            int unicodeCharsLen = banglaUnicodeChar.size();
            object.pheromoneTable = new LinkedHashMap<>(unicodeCharsLen);
            banglaUnicodeChar.forEach(c -> {
                double[] array = new double[charSetLen];
                Arrays.fill(array, initialValue);
                object.pheromoneTable.putIfAbsent(c, array);
            });
            return object;
        }
    }
}
