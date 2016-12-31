package com.seal.util;

/**
 * Created by seal on 10/18/2016.
 */
public enum Finger {
    Forefinger,
    MiddleFinger,
    Ringfinger,
    Pinkie,
    Thumb;

    public static Finger finger(String str) {
        switch (str) {
            case "f":
                return Finger.Forefinger;
            case "m" :
                return Finger.MiddleFinger;
            case "r" :
                return Finger.Ringfinger;
            case "p":
                return Finger.Pinkie;
            case "t":
                return Finger.Thumb;
        }
        throw new RuntimeException("No Finger Define");
    }
}
