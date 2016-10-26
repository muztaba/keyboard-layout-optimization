package com.seal.util;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by seal on 10/22/2016.
 */
public class ReadFile {

    private BufferedReader reader;

    public ReadFile(String path) {
        try {
            this.reader = Files.newBufferedReader(Paths.get(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String readNextLine(int line) {
        StringBuilder builder = new StringBuilder();
        try {
            for (int i = 0; i < line; i++) {
                String str = reader.readLine();
                if (str == null) break;
                builder.append(str);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return builder.toString();
    }
}
