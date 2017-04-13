package com.seal.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * Created by seal on 4/14/2017.
 */
public class IO {

    private static final Logger logger = LoggerFactory.getLogger(IO.class);

    public static Stream<String> streamOf(String path) {
         try {
             return getLinesStream(path);
         } catch (IOException e) {
             logger.warn("File Not Found at path {}", path);
             throw new RuntimeException(e);
         }
    }

    private static Stream<String> getLinesStream(String path) throws IOException {
        return Files.lines(Paths.get(path));
    }

}
