package com.seal.algorithm.aco;

import com.seal.Configuration;
import com.seal.io.IO;
import com.seal.io.ReadFile;
import com.seal.keyboard.EvaluateService;
import com.seal.keyboard.EvaluationFactory;
import com.seal.keyboard.Init;
import com.seal.keyboard.KeyMap;
import com.seal.util.CharSetProducer;
import com.seal.util.StaticUtil;
import com.seal.util.dto.ObjectiveFunctionsValues;
import com.seal.util.dto.WinterIsHere;
import org.apache.commons.lang3.CharSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by Farruck Ahmed Tusar on 30-Aug-17.
 */
public class Runner {

    private static final Logger logger = LoggerFactory.getLogger(Runner.class);


    public static void main(String[] args) {
        logger.info("ACO application started...");
        final Configuration configuration = Configuration.load();
        final EvaluateService evaluateService = EvaluationFactory.getService();

        List<Character> banglaChars = Init.loadBanglaChars(IO.streamOf("bangla-char.txt"));
        CharSetProducer charSet = CharSetProducer.load().build();

        final ReadFile readFile = new ReadFile("text.txt", banglaChars);


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
        PriorityQueue<WinterIsHere> queue2 = new PriorityQueue<>();
        for (int itr = 0; itr < iteration; itr++) {
            // TODO should read line number from application.properties
            String strs = readFile.readNextLine(2);

            for (Ant ant : antList) {
                KeyMap<Character, String> keyMap = ant.run(strs);
                ObjectiveFunctionsValues refValues = evaluateService.evaluate(strs, refKeymap);
                ObjectiveFunctionsValues values = evaluateService.evaluate(strs, keyMap);
                double globalScore = values.globalScore(refValues);
                queue.add(new WinterIsHere(itr, keyMap, values, globalScore));
            }

            pheromoneTable.evaporate(i -> i * 0.95); // evaporate pheromone matrix
            List<WinterIsHere> listOfBestResult = bestAntResult(queue, configuration.getInt("aco.p"));
            for (int i = 0; i < listOfBestResult.size(); i++) {
                double updateVal = StaticUtil.pheromoneUpdate[i];
                KeyMap<Character, String > obj = listOfBestResult.get(i).getKeyMap();
                banglaChars.forEach(ch -> {
                    int index = getCharSetIndex(obj.getMap(ch), charSet);
                    pheromoneTable.updatePheromoneTable(ch , index, updateVal * 0.2);
                });
            }
            queue.clear();
            queue2.addAll(listOfBestResult);
        }
        WinterIsHere antResult = queue2.poll();
        logger.info("Final Episode \n {}", WinterIsHere.toString(antResult));
        IO.writeFile("keyboards/ant.txt", WinterIsHere.toString(antResult));
    }

    private static List<WinterIsHere> bestAntResult(PriorityQueue<WinterIsHere> queue, int n) {
        return Stream.generate(queue::poll)
                .limit(n)
                .collect(Collectors.toList());
    }

    private static int getCharSetIndex(String keymap, CharSetProducer charSet) {
        return charSet.index(keymap);
    }

    private static List<Ant> getAnt(int number, List<Character> banglaChars, List<String> charSet, PheromoneTable pheromoneTable) {
        return IntStream.range(0, number)
                .mapToObj(i -> new Ant(String.valueOf(i), pheromoneTable, banglaChars, charSet))
                .collect(Collectors.toList());
    }


}
