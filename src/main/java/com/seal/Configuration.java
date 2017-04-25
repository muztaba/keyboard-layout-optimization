package com.seal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Properties;

/**
 * Created by seal on 4/26/2017.
 */
public class Configuration {

    private static final Logger logger = LoggerFactory.getLogger(Configuration.class);

    private static final Properties properties = new Properties();

    private static volatile Configuration instance;

    private Configuration() {
        try {
            InputStream inputStream = Configuration.class
                    .getClassLoader()
                    .getResourceAsStream("application.properties");
            properties.load(inputStream);
        } catch (IOException e) {
            logger.error("application.properties file can't be found");
            throw new RuntimeException(e);
        }
    }

    private Configuration(String path) {
        Objects.requireNonNull(path, "External file path is null");
        try {
            properties.load(Files.newInputStream(Paths.get(path)));
        } catch (IOException e) {
            logger.error("External properties file not found at path [{}]", path);
            throw new RuntimeException(e);
        }
    }

    public static Configuration load(String... args) {
        if (Objects.isNull(instance)) {
            synchronized (Configuration.class) {
                if (Objects.isNull(instance)) {
                    if (args.length == 0)
                        instance = new Configuration();
                    else
                        instance = new Configuration(args[0]);
                }
            }
        }
        return instance;
    }

    public String get(String key) {
        return properties.getProperty(isNull(key), "");
    }

    public int getInt(String key) {
        return Integer.parseInt(properties.getProperty(isNull(key), "0"));
    }

    private <T> T isNull(T key) {
        return Objects.requireNonNull(key, "key should not be null");
    }

}
