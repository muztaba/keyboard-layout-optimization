package com.seal;

import com.seal.io.IO;
import com.seal.keyboard.Init;
import com.seal.keyboard.KeyMap;
import com.seal.keyboard.KeyMapProcessor;
import com.seal.util.Key;
import com.seal.util.StaticUtil;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.List;
import java.util.Map;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        List<Key> macro = KeyMapProcessor.load(keymap, qwerty)
                .setString(str)
                .getKeyMap();

        macro.stream()
                .mapToInt(Key::getLetter)
                .mapToObj(i -> String.valueOf((char) i))
                .forEach(System.out::print);

    }
    Map<Character, Key> qwerty = Init.loadQwert(IO.streamOf("qwerty.txt"));
    KeyMap<Character, String> keymap = Init.loadKeyMap(IO.streamOf("keyboards/jatiya.txt"));

    public static final String str = "উপদ্বীপে";


    public void testKeyMapProcessor()
    {


    }
}
