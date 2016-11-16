package com.seal.keyboard;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.Serializable;

/**
 * Created by seal on 10/1/2016.
 */
public class Key implements Serializable {

    private final char letter;
    private final Point position;
    private final Hand hand;
    private final Finger finger;


    private Key(char letter, int row, int col, Hand hand, Finger finger) {
        this.letter = letter;
        position = new Position(row, col);
        this.hand = hand;
        this.finger = finger;
    }

    private static Builder builder() {
        return new Builder();
    }

    public static Key build(String[] str) {
        return Key.builder()
                .setLatter(str[0].charAt(0))
                .setRow(Integer.parseInt(str[1]))
                .setCol(Integer.parseInt(str[2]))
                .setHand(Hand.hand(str[3]))
                .setFinger(Finger.finger(str[4]))
                .build();
    }

    static class Position extends Point {
        private Position(int x, int y) {
            super(x, y);
        }

        @Override
        public double distance(Point2D pt) {
            // Manhattan Distance
            return Math.abs(getX() - pt.getX()) + Math.abs(getY() - pt.getY());
        }
    }

    private static class Builder {
        private char latter;
        private int x, y;
        private Hand hand;
        private Finger finger;

        private Builder setLatter(char latter) {
            this.latter = latter;
            return this;
        }

        private Builder setRow(int position) {
            this.x = position;
            return this;
        }

        private Builder setCol(int position) {
            this.y = position;
            return this;
        }

        private Builder setHand(Hand hand) {
            this.hand = hand;
            return this;
        }

        private Builder setFinger(Finger finger) {
            this.finger = finger;
            return this;
        }

        private Key build() {
            return new Key(latter, x, y, hand, finger);
        }
    }

    public char getLetter() {
        return letter;
    }

    public Point getPosition() {
        return position;
    }

    public Hand getHand() {
        return hand;
    }

    public Finger getFinger() {
        return finger;
    }

    @Override
    public String toString() {
        return "Key{" +
                "letter=" + letter +
                ", position=" + position +
                ", hand=" + hand +
                ", finger=" + finger +
                '}';
    }
}
