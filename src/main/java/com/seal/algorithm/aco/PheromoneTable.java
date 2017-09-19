package com.seal.algorithm.aco;

import java.util.function.Function;

/**
 * Created by seal on 5/11/2017.
 */
public interface PheromoneTable {
    double[] getPheromoneArray(char c);

    void evaporate(char c, int index, double evaporateRate);

    void evaporate(Function<Double, Double> updateFunction);

    void updatePheromoneTable(char ch, int index, double updateVal);
}
