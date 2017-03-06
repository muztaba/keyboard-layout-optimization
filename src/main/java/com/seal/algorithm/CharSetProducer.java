package com.seal.algorithm;


import java.util.List;

/**
 * Created by seal on 3/6/2017.
 */
public enum   CharSetProducer {

    CHAR_SET_PRODUCER;

    private List<String> stringList;

    CharSetProducer() {
        produceCharSet();
    }

    private void produceCharSet() {


    }

    public List<String> getCharSet() {
        return stringList;
    }
}
