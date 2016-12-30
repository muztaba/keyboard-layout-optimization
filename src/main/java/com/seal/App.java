package com.seal;

import com.seal.keyboard.KeyMap;
import com.seal.keyboard.Keyboard;
import com.seal.keyboard.KeyboardFactory;
import com.seal.keyboard.KeyMapProcessor;
import com.seal.io.ReadFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        new Test().testFileRead();
    }
}

class Test {

    private static final Logger logger = LoggerFactory.getLogger(Test.class);

    public void testFileRead() {

        Keyboard qwerty = getQwerty();
        KeyMap<Character, String> keymap = getKeymap();

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
    }

    public Keyboard getQwerty() {

        Keyboard qwerty = null;
        try {
            qwerty = KeyboardFactory.loadQwert("qwerty.txt");
        } catch (IOException e) {
            logger.error("QWERTY config not found \n {}", e);
            System.exit(1);
        }

        return qwerty;
    }

    public KeyMap<Character, String> getKeymap() {
        KeyMap<Character, String> keymap = null;
        try {
            keymap = KeyboardFactory.loadKeyMap("bijoy.txt");
        } catch (IOException e) {
            logger.error("No keymap config not found \n {}", e);
            System.exit(1);
        }
        return keymap;
    }

}