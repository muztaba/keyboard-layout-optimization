package com.seal.io;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by seal on 10/22/2016.
 */
public class ReadFile {

    private BufferedReader reader;

    private final LineIterator it;

    public ReadFile(String path) {
        try {
            this.it = FileUtils.lineIterator(new File(path), "UTF-8");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String readNextLine(int line) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; it.hasNext() && i < line; i++) {
            String str = it.nextLine();
            // TODO review for if found empty line
            if (str.isEmpty()) continue;
            str = str.replaceAll("\\s+", "");
            builder.append(str);
        }
        return builder.toString();
    }
}
