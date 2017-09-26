package com.seal.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
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

    public static List<String> listOfFilesName(String directory) {
        try {
            return Files.list(Paths.get(directory))
                    .map(String::valueOf)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Stream<String> getLinesStream(String path) throws IOException {
        return Files.lines(Paths.get(path));
    }

    public static void writeFile(String path, String content) {
        try {
            Files.write(Paths.get(path), content.getBytes());
        } catch (IOException e ) {
            logger.error("Error when write to file [{}], [{}]", path, e);
        }
    }

}
