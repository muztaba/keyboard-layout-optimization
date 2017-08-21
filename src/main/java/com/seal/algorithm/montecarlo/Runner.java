package com.seal.algorithm.montecarlo;

import com.seal.Configuration;
import com.seal.io.IO;
import com.seal.io.ReadFile;
import com.seal.keyboard.Init;
import com.seal.keyboard.KeyMap;
import com.seal.keyboard.KeyMapProcessor;
import com.seal.keyboard.Keyboard;
import com.seal.util.CharSetProducer;
import com.seal.util.Key;
import com.seal.util.dto.ObjectiveFunctionsValues;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * Created by Farruck Ahmed Tusar on 20-Aug-17.
 */
public class Runner {

    private static final Logger logger = LoggerFactory.getLogger(Configuration.class);

    public static void main(String[] args) {
        logger.info("Monte Carlo Application Start ...");
        MonteCarlo monteCarlo = new MonteCarlo();
        List<Character> banglaChars = Init.loadBanglaChars(IO.streamOf("bangla-char.txt"));
        logger.info("Bangla Chars load, size [{}]", banglaChars.size());
        CharSetProducer charSet = CharSetProducer.load().build();
        logger.info("Data Char Set size [{}]", charSet.getCharSet().size());


        ReadFile readFile = new ReadFile("text.txt");

        logger.info("Loading QWETY ...");
        Map<Character, Key> qwerty = Init.loadQwert(IO.streamOf("qwerty.txt"));
        String string = readFile.readNextLine(10);
        for (int i = 0; i < 100; i++) {
            Map<Character, String> keymap = monteCarlo.run(banglaChars, charSet.getCharSet());
            KeyMap<Character, String> keyMap1 = new KeyMap<>(keymap);
            List<Key> macro = KeyMapProcessor.load(keyMap1, qwerty)
                    .setString(string)
                    .getKeyMap();
            Keyboard keyboard = Keyboard.getInstance(qwerty);
            ObjectiveFunctionsValues values = keyboard.evaluate(macro);
            logger.info("Objective Function values = [{}]", values);
        }
    }
}
