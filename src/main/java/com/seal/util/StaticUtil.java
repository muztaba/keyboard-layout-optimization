package com.seal.util;

import java.util.EnumMap;
import java.util.Map;

/**
 * Created by seal on 4/2/2017.
 */
public class StaticUtil {

    private static final Map<Finger, Finger> fingerMovementMap = new EnumMap<Finger, Finger>(Finger.class){{
        put(Finger.Pinkie, Finger.Ringfinger);
        put(Finger.Ringfinger, Finger.MiddleFinger);
        put(Finger.MiddleFinger, Finger.Forefinger);
        put(Finger.Forefinger, Finger.Pinkie);
    }};

    private static final Map<Finger, Integer> map = new EnumMap<Finger, Integer>(Finger.class) {{
        put(Finger.Thumb, 0);
        put(Finger.Forefinger, 1);
        put(Finger.MiddleFinger, 2);
        put(Finger.Ringfinger, 3);
        put(Finger.Pinkie, 4);
    }};

    private static final double[][] coefficient = {
            {0, 0, 0, 0, 0},
            {0, 0, 5, 8, 6},
            {0, 5, 0, 9, 7},
            {0, 8, 9, 0, 10},
            {0, 6, 7, 10, 0}
    };

    public static double getCoefficient(Finger u, Finger v) {
        int i = map.get(u);
        int j = map.get(v);
        return coefficient[i][j];
    }

    public static Finger nextFinger(Finger finger) {
        return fingerMovementMap.get(finger);
    }
}
