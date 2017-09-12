package com.seal.util.dto;

import com.seal.keyboard.KeyMap;

/**
 * Created by Farruck Ahmed Tusar on 12-Sep-17.
 */
public class WinterIsHere implements Comparable<WinterIsHere> {

    private final int iteration;
    private final KeyMap<Character, String > keyMap;
    private final ObjectiveFunctionsValues objectiveFunctionsValues;
    private final double globalScore;

    public WinterIsHere(int iteration, KeyMap<Character, String> keyMap, ObjectiveFunctionsValues objectiveFunctionsValues, double globalScore) {
        this.iteration = iteration;
        this.keyMap = keyMap;
        this.objectiveFunctionsValues = objectiveFunctionsValues;
        this.globalScore = globalScore;
    }

    @Override
    public int compareTo(WinterIsHere o) {
        // TODO set global score as first param
        return Double.compare(this.getGlobalScore(), o.getGlobalScore());
    }

    public int getIteration() {
        return iteration;
    }

    public KeyMap<Character, String> getKeyMap() {
        return keyMap;
    }

    public ObjectiveFunctionsValues getObjectiveFunctionsValues() {
        return objectiveFunctionsValues;
    }

    public double getGlobalScore() {
        return globalScore;
    }

    @Override
    public String toString() {
        return String .valueOf(getGlobalScore());
    }
}
