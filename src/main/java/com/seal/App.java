package com.seal;

import com.seal.algorithm.PheromoneTable;
import com.seal.algorithm.PheromoneTableImpl;
import com.seal.io.IO;
import com.seal.io.ReadFile;
import com.seal.keyboard.*;
import com.seal.util.CharSetProducer;
import com.seal.util.Key;
import com.seal.util.dto.ObjectiveFunctionsValues;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Hello world!
 */
public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        new Test().synchronousOperation();
    }
}

class Test {

    private static final Logger logger = LoggerFactory.getLogger(Test.class);

    public void synchronousOperation() {
        logger.info("Starting up application");
        Map<Character, Key> qwerty = Init.loadQwert(IO.streamOf("qwerty.txt"));
        List<String> keyboards = IO.listOfFilesName("keyboards");
        String str = readFile();

        String ref = refKeyboardName(keyboards);
        KeyMap<Character, String> refKeymap = Init.loadKeyMap(IO.streamOf(ref));
        ObjectiveFunctionsValues refValues = objectiveFunction(qwerty, refKeymap, str);
        logger.info("Reference Keyboard [{}], Values [{}]", ref, refValues);
        keyboards.removeIf(i -> i.contains("-ref"));

        for (String fileName : keyboards) {
            logger.info("File name [{}]", fileName.split("\\\\")[1]);
            KeyMap<Character, String> keymap = Init.loadKeyMap(IO.streamOf(fileName));
            ObjectiveFunctionsValues values = objectiveFunction(qwerty, keymap, str);
            double globalScore = values.globalScore(refValues);
            logger.info("Values = [{}]",values);
            logger.info("GlobalScore [{}]", globalScore);
        }

    }

    public void algorithemTest() {
        List<Character> banglaUnicodeChars = Init.loadQwert(IO.streamOf("qwerty.txt"))
                .keySet()
                .stream()
                .collect(Collectors.toList());
        List<String> charSequence = CharSetProducer.CHAR_SET_PRODUCER.getCharSet();
        PheromoneTable pheromoneTable = PheromoneTableImpl.getBuilder()
                .setBanglaUnicodeCharList(banglaUnicodeChars)
                .setCharSetList(charSequence)
                .setInitialValue(1.0)
                .build();

    }

    public String refKeyboardName(List<String> fileName) {
        Optional<String> ref = fileName.stream()
                .filter(i -> i.contains("-ref"))
                .findFirst();

        if (ref.isPresent()) return ref.get();
        else throw new RuntimeException("No Reference Keyboard Found");
    }

    public ObjectiveFunctionsValues objectiveFunction(Map<Character, Key> qwerty, KeyMap<Character, String> keymap, String str) {
        List<Key> macro = KeyMapProcessor.load(keymap, qwerty)
                .setString(str)
                .getKeyMap();

        fileWrite(macro);

        Keyboard keyboard = Keyboard.getInstance(qwerty);
        return keyboard.evaluate(macro);
    }

    public String readFile() {
        ReadFile reader = new ReadFile("text.txt"); // need to remove , otherwise read same line repeatedly
        return reader.readNextLine(10);
    }

    void fileWrite(List<Key> macro) {
        try {
            PrintWriter out = new PrintWriter(Files.newBufferedWriter(Paths.get("outputOne.txt")));
            macro.stream()
                    .mapToInt(Key::getLetter)
                    .mapToObj(i -> String.valueOf((char) i))
                    .forEach(out::print);
            out.close();
        } catch (Exception e) {

        }
    }

}