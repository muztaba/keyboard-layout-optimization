package com.seal.keyboard;

import java.awt.*;
import java.io.Serializable;

/**
 * Created by seal on 10/1/2016.
 */
public class Key implements Serializable {

    private final char latter;
    private final Point position;
    private final Hand hand;
    private final Finger finger;


    private Key(char latter, int row, int col, Hand hand, Finger finger) {
        this.latter = latter;
        position = new Point(row, col);
        this.hand = hand;
        this.finger = finger;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private char latter;
        private int x, y;
        private Hand hand;
        private Finger finger;

        public Builder setLatter(char latter) {
            this.latter = latter;
            return this;
        }

        public Builder setRow(int position) {
            this.x = position;
            return this;
        }

        public Builder setCol(int position) {
            this.y = position;
            return this;
        }

        public Builder setHand(Hand hand) {
            this.hand = hand;
            return this;
        }

        public Builder setFinger(Finger finger) {
            this.finger = finger;
            return this;
        }

        public Key build() {
            return new Key(latter, x, y, hand, finger);
        }
    }

    public char getLatter() {
        return latter;
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
                "latter=" + latter +
                ", position=" + position +
                ", hand=" + hand +
                ", finger=" + finger +
                '}';
    }
}
