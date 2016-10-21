package com.seal;

import com.seal.keyboard.Configuration;
import com.seal.keyboard.Keyboard;
import com.seal.util.FileUtil;

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
        System.out.println(bijoy);
    }
}