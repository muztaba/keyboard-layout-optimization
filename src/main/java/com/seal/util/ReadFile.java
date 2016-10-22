package com.seal.util;

import java.io.*;

/**
 * Created by seal on 10/22/2016.
 */
public class ReadFile {

    private BufferedReader reader;

    public ReadFile(String path) {
        try {
            this.reader = new BufferedReader(new FileReader(new File(path)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String readNext(int line) {
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
