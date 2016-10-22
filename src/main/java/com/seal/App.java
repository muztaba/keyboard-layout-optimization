package com.seal;

import com.seal.keyboard.Configuration;
import com.seal.keyboard.Keyboard;
import com.seal.keyboard.ObjectiveFunction;
import com.seal.util.FileUtil;
import com.seal.util.ReadFile;

import java.util.List;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) {
        new Test().testFileRead();
    }
}

class Test {
    public void testFileRead() {
        List<String> list = FileUtil.lines("qwerty.txt");
        List<String> list1 = FileUtil.lines("bijoy.txt");
        Keyboard bijoy = new Configuration().loadQwert(list)
                .loadKeyMap(list1)
                .buildKeyboard();

        ReadFile reader = new ReadFile("text.txt");
        String str = reader.readNext(10);
        ObjectiveFunction of = bijoy.getObjectiveFunction();
        System.out.println(of.keyPress(str));
    }
}