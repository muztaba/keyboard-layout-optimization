package com.seal.util;

/**
 * Created by seal on 10/17/2016.
 */
public enum Hand {
    Right,
    Left;

    public static Hand hand(String str) {
        switch (str) {
            case "r" :
                return Hand.Right;
            case "l" :
                return Hand.Left;
        }
        throw new RuntimeException("No Hand Define");
    }
}
