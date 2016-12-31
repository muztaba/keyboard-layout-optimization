package com.seal.util;

/**
 * Created by seal on 12/31/16.
 */
public enum Bangla {
     BANGLA_CHAR_START(0x0980),
     BANGLA_CHAR_END(0x09FF),
     BANGLA_NUMBER_START(0x09E6),
     BANGLA_NUMBER_END(0x09EF);

    private final int codePoint;

    private Bangla(final int codePoint) {
        this.codePoint = codePoint;
    }

    public int getCodePoint(){
        return codePoint;
    }
}
