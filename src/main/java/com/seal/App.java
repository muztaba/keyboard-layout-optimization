package com.seal;

import com.seal.io.IO;
import com.seal.io.ReadFile;
import com.seal.keyboard.Init;
import com.seal.keyboard.KeyMap;
import com.seal.keyboard.KeyMapProcessor;
import com.seal.keyboard.Keyboard;
import com.seal.util.Key;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        new Test().synchronousOperation();
    }
}

class Test {

    private static final Logger logger = LoggerFactory.getLogger(Test.class);

    /**
     * Objective functions value should be minimize or maximized accordingly Problem statement
     * [not done yet]
     */

    public void synchronousOperation() {
        Map<Character, Key> qwerty = Init.loadQwert(IO.streamOf("qwerty.txt"));
        List<String> keyboards = IO.listOfFilesName("keyboards");
        String str = readFile();


        for (String fileName : keyboards) {
            System.out.println(fileName.split("\\\\")[1]);
            KeyMap<Character, String> keymap = Init.loadKeyMap(IO.streamOf(fileName));
            objectiveFunction(qwerty, keymap, str);
            System.out.println("\n>>>>>>>>>>>>>>>>>>>>>");
        }

    }

    public void objectiveFunction(Map<Character, Key> qwerty, KeyMap<Character, String> keymap, String str) {
        KeyMapProcessor keyMapProcessor = new KeyMapProcessor(keymap, qwerty);
        List<Key> macro = keyMapProcessor.setString(str)
                .getKeyMap();

        fileWrite(macro);

        Keyboard keyboard = new Keyboard(qwerty);
        System.out.println(keyboard.getObjectiveFunction()
                .evaluate(macro));
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