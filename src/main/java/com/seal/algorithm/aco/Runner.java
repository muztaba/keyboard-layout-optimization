package com.seal.algorithm.aco;

import com.seal.Configuration;
import com.seal.io.IO;
import com.seal.keyboard.EvaluateService;
import com.seal.keyboard.EvaluationFactory;
import com.seal.keyboard.Init;
import com.seal.keyboard.KeyMap;
import com.seal.util.CharSetProducer;
import com.seal.util.dto.ObjectiveFunctionsValues;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by Farruck Ahmed Tusar on 30-Aug-17.
 */
public class Runner {

    private static final Logger logger = LoggerFactory.getLogger(Runner.class);


    public static void main(String[] args) {
        logger.info("ACO application started...");
        Configuration configuration = Configuration.load();
        EvaluateService evaluateService = EvaluationFactory.getService();

        List<Character> banglaChars = Init.loadBanglaChars(IO.streamOf("bangla-char.txt"));
        CharSetProducer charSet = CharSetProducer.load().build();

        PheromoneTable pheromoneTable = PheromoneTableImpl.getBuilder()
                .setBanglaUnicodeCharList(banglaChars)
                .setCharSetList(charSet.getCharSet())
                .setInitialValue(1.0)
                .build();

        List<Ant> antList = getAnt(configuration.getInt("ant.number"),
                banglaChars,
                charSet.getCharSet(),
                pheromoneTable);

        int iteration = configuration.getInt("aco.iteration");
        Map<String, KeyMap<Character, String>> result = new HashMap<>();
        for (int itr = 0; itr < iteration; itr++) {
            // TODO read string from file
            String strs = "";

            antList.forEach(ant -> {
                KeyMap<Character, String> keyMap = ant.run(strs);
                ObjectiveFunctionsValues values = evaluateService.evaluate(strs, keyMap);
            });

        }

    }

    private static List<Ant> getAnt(int number, List<Character> banglaChars, List<String> charSet, PheromoneTable pheromoneTable) {
        return IntStream.range(0, number)
                .mapToObj(i -> new Ant(String.valueOf(i), pheromoneTable, banglaChars, charSet))
                .collect(Collectors.toList());
    }
}
