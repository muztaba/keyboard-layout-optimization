package com.seal;

import com.seal.algorithm.KeyCombinationCreator;
import com.seal.keyboard.KeyMap;
import com.seal.keyboard.Keyboard;
import com.seal.keyboard.KeyboardFactory;
import com.seal.util.KeyMapProcessor;
import com.seal.util.ReadFile;

import java.util.List;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        new Test().testFileRead();
    }
}

class Test {
    public void testFileRead() {

        Keyboard qwerty = KeyboardFactory.loadQwert("qwerty.txt");
        KeyMap<Character, String> keymap = KeyboardFactory.loadKeyMap("bijoy.txt");

        ReadFile reader = new ReadFile("text.txt");
        String str = reader.readNextLine(10);
        String keyMapBijoy = KeyMapProcessor.getInstance(keymap)
                .setString(str)
                .getKeyMap();

        System.out.println(keyMapBijoy);

        Keyboard.Values score = qwerty.getObjectiveFunction()
                .evaluate(keyMapBijoy)
                .getValues();

        System.out.println(score);
        List<String > stringList = KeyCombinationCreator.keyCombinationCreate();
        stringList = qwerty.getFilterKeyMap()
                .filter(stringList);

        System.out.println(stringList.size());
    }

}