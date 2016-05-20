package com.concurrentminds.winter.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

public class ReportGeneratorServiceImpl implements ReportGeneratorService {

    private final static Logger logger = LogManager.getLogger(ReportGeneratorServiceImpl.class);

    @Override
    public void generateReport(String path, String fileName, Class clazz) {
        Path repoDir = Paths.get(path);
        if (Files.exists(repoDir)) {
            path = removeLastSlash(path);
            File file = new File(path + "\\" + fileName + ".txt");
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(getContent(clazz, Strings.EMPTY));
                writer.flush();
            } catch (IOException e) {
                logger.error("Exception while creating class report.", e);
            }
        } else {
            logger.error("Report directory not exists = " + repoDir.getFileName());
        }
    }

    String removeLastSlash(String path) {
        if (path.lastIndexOf("\\") == path.length() - 1) {
            path = path.substring(0, path.length() - 1);
        }
        return path;
    }


    private String getContent(Class clazz, String content) {
        StringBuilder result = new StringBuilder(content);
        result.append("\nPackage: \n");
        result.append(clazz.getPackage().getName());
        result.append("\nAnnotations: \n");
        Stream.of(clazz.getAnnotations()).forEach(e -> result.append(e).append("\n"));
        result.append("\nClass: \n");
        result.append(clazz.getName());
        result.append("\nFields: \n");
        Stream.of(clazz.getDeclaredFields()).forEach(e -> result.append(e).append("\n"));
        result.append("\nConstructors: \n");
        Stream.of(clazz.getConstructors()).forEach(e -> result.append(e).append("\n"));
        result.append("\nMethods: \n");
        Stream.of(clazz.getDeclaredMethods()).forEach(e -> result.append(e).append("\n"));
        if (clazz.getClasses() != null && clazz.getClasses().length > 0) {
            Stream.of(clazz.getClasses()).forEach(e -> {
                result.append("\nInner classes {\n");
                result.append(getContent(e, result.toString()));
                result.append("\n}\n");
            });
        }
        return result.toString();
    }


}
