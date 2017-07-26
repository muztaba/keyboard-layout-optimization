package com.seal.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dsi on 7/25/17.
 */
public class CollectionUtil {

    public <E> List<E> copyOf(List<E> src) {
        List<E> dest = new ArrayList<>(src.size());
        src.forEach(i -> dest.add(i));
        return dest;
    }
}
