package com.seal.io;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by seal on 10/22/2016.
 */
public class ReadFile {

    private static final Logger logger = LoggerFactory.getLogger(ReadFile.class);

    private BufferedReader reader;

    private final LineIterator it;
    private final Set<Character> banglaChars;

    public ReadFile(String path, List<Character> banglaChars) {
        try {
            this.it = FileUtils.lineIterator(new File(path), "UTF-8");
        } catch (IOException e) {
            logger.error("IO exception when read file path [{}], {}", path, e);
            throw new RuntimeException(e);
        }
        this.banglaChars = new HashSet<>(banglaChars);
    }

    public String readNextLine(int line) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; it.hasNext() && i < line; i++) {
            String str = it.nextLine();
            if (str.isEmpty()) continue;
            str = getOnlyBanglaChar(str);
            builder.append(str);
        }
        return builder.toString();
    }

    private String  getOnlyBanglaChar(String string) {
        return string.chars()
                .filter(i -> banglaChars.contains((char) i) || Character.isSpaceChar((char) i))
                .collect(
                StringWriter::new,
                StringWriter::write,
                (swl, swr) -> swl.write(swr.toString())).toString();
    }
}
