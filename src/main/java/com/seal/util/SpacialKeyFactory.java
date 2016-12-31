package com.seal.util;

/**
 * Created by seal on 12/31/16.
 */
public class SpacialKeyFactory {
    final static private  Key leftShift = Key.build(new String[]{"+", "6", "4", "l", "p"});
    final static private  Key rightShift = Key.build(new String[]{"+", "6", "4", "r", "p"});

    final static private  Key leftSpace = Key.build(new String[]{" ", "0", "3", "l", "t"});
    final static private  Key rightSpace = Key.build(new String[]{" ", "0", "3", "r", "t"});

    public static Key getKey(Hand hand, SpacialKey key) {
        if (key == SpacialKey.Shift) {
            if (hand == Hand.Left) return leftShift; else return rightShift;
        } else if (key == SpacialKey.Space) {
            if (hand == Hand.Left) return leftSpace; else return rightSpace;
        }

        return null;
    }

    public enum SpacialKey {
        Space,
        Shift
    }
}
