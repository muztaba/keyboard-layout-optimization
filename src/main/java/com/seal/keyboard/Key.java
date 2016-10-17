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


    public Key(char latter, int row, int col, Hand hand) {
        this.latter = latter;
        position = new Point(row, col);
        this.hand = hand;
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
}
