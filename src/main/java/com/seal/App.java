package com.seal;

import com.seal.keyboard.*;
import com.seal.util.FileUtil;
import com.seal.util.KeyMapProcessor;
import com.seal.util.ReadFile;

import java.util.HashMap;
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

        KeyMap<Character, String> keyMap = loadKeyMap(list1);

        ReadFile reader = new ReadFile("text.txt");
        String str = reader.readNext(10);
        String keyMapBijoy = KeyMapProcessor.getInstance(keyMap)
                .setString(str)
                .getKeyMap();
        System.out.println(keyMapBijoy);
    }

    public KeyMap<Character, String> loadKeyMap(List<String > list) {
        HashMap<Character, String > keyMap = new HashMap<>();

        for (String str : list) {
            String[] strs = str.split(" ");
            if (keyMap.containsValue(strs[0]))
                throw new RuntimeException("Already keymap exit for" + strs[0] + " is " + strs[1]);
            keyMap.put(strs[0].charAt(0), strs[1]);
        }

        KeyMap<Character, String> keyMap1 = new KeyMap<>(keyMap);
        return        keyMap1;
    }
}