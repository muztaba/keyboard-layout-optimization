package com.seal.util;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by seal on 3/6/2017.
 */
public class CharSetProducer {

    private List<String> stringList;

    private final Filter filter;

    private CharSetProducer(Filter filter) {
        this.filter = filter;
        produceCharSet();
    }

    private void init() {
        List<String> list = produceCharSet();
        if (Objects.nonNull(filter)) {
            this.stringList = list.stream()
                    .filter(i -> filter.accept(i))
                    .collect(Collectors.collectingAndThen(
                            Collectors.toList(), Collections::unmodifiableList
                    ));
        } else {
            this.stringList = list;
        }
    }


    private List<String> produceCharSet() {
        List<String> list = new ArrayList<>();
        createNext26("", list);
        createNext26("+", list);
        for (int i = 'a'; i <= 'z'; i++) {
            createNext26((char) i + "+", list);
        }
        return list;
    }

    private void createNext26(String prefix, List<String> list) {
        int v = prefix.isEmpty() ? -1 : prefix.codePointAt(0);
        for (int i = 'a'; i <= 'z'; i++) {
            if (v != i) {
                list.add(prefix + (char) i);
            }
        }
    }

    public List<String> getCharSet() {
        return stringList;
    }

    public static class Builder {
        private Filter filter;

        public Builder setFilter(Filter filter) {
            this.filter = filter;
            return this;
        }

        public CharSetProducer build() {
            CharSetProducer charSetProducer = new CharSetProducer(filter);
            charSetProducer.init();
            return charSetProducer;
        }
    }

    public static Builder load() {
        return new Builder();
    }
}
