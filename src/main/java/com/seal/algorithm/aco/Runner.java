package com.seal.algorithm.aco;

import com.seal.Configuration;
import com.seal.io.IO;
import com.seal.io.ReadFile;
import com.seal.keyboard.EvaluateService;
import com.seal.keyboard.EvaluationFactory;
import com.seal.keyboard.Init;
import com.seal.keyboard.KeyMap;
import com.seal.util.CharSetProducer;
import com.seal.util.dto.ObjectiveFunctionsValues;
import com.seal.util.dto.WinterIsHere;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by Farruck Ahmed Tusar on 30-Aug-17.
 */
public class Runner {

    private static final Logger logger = LoggerFactory.getLogger(Runner.class);


    public static void main(String[] args) {
        logger.info("ACO application started...");
        final Configuration configuration = Configuration.load();
        final EvaluateService evaluateService = EvaluationFactory.getService();
        final ReadFile readFile = new ReadFile("text.txt");

        List<Character> banglaChars = Init.loadBanglaChars(IO.streamOf("bangla-char.txt"));
        CharSetProducer charSet = CharSetProducer.load().build();

        KeyMap<Character, String> refKeymap = Init.loadKeyMap(IO.streamOf("keyboards/bijoy-ref.txt"));

        PheromoneTable pheromoneTable = PheromoneTableImpl.getBuilder()
                .setBanglaUnicodeCharList(banglaChars)
                .setCharSetList(charSet.getCharSet())
                .setInitialValue(1.0)
                .build();

        List<Ant> antList = getAnt(configuration.getInt("ant.number"),
                banglaChars,
                charSet.getCharSet(),
                pheromoneTable);

        final int iteration = configuration.getInt("aco.iteration");
        Map<String, KeyMap<Character, String>> result = new HashMap<>();
        PriorityQueue<WinterIsHere> queue = new PriorityQueue<>();
        for (int itr = 0; itr < iteration; itr++) {
            // TODO should read line number from application.properties
            String strs = readFile.readNextLine(10);

            for (int i = 0; i < antList.size(); i++) {
                Ant ant = antList.get(i);
                KeyMap<Character, String> keyMap = ant.run(strs);
                ObjectiveFunctionsValues refValues = evaluateService.evaluate(strs, refKeymap);
                ObjectiveFunctionsValues values = evaluateService.evaluate(strs, keyMap);
                double globalScore = values.globalScore(refValues);
                queue.add(new WinterIsHere(itr, keyMap, values, globalScore));
            }
            WinterIsHere seasonFinale = queue.poll();
            pheromoneTable.updatePheromoneTable(i -> i * 0.95);
            queue.clear();
        }

    }

    private static List<Ant> getAnt(int number, List<Character> banglaChars, List<String> charSet, PheromoneTable pheromoneTable) {
        return IntStream.range(0, number)
                .mapToObj(i -> new Ant(String.valueOf(i), pheromoneTable, banglaChars, charSet))
                .collect(Collectors.toList());
    }


}
