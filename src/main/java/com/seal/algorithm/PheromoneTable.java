package com.seal.algorithm;

import java.util.function.Function;

/**
 * Created by seal on 5/11/2017.
 */
public interface PheromoneTable {
    double[] getPheromoneArray(char c);

    void evaporate(char c, int index, double evaporateRate);

    void updatePheromoneTable(Function<Double, Double> updateFunction);
}
