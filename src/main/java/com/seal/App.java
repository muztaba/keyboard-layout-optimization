package com.seal;

import com.seal.keyboard.*;
import com.seal.util.FileUtil;
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
        List<String> list = FileUtil.lines("qwerty.txt");
        List<String> list1 = FileUtil.lines("bijoy.txt");

        Keyboard qwerty = KeyboardFactory.loadQwert(list);
        KeyMap<Character, String> keymap = KeyboardFactory.loadKeyMap(list1);

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

}