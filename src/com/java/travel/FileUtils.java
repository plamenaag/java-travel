package com.java.travel;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
    public static List<String> readFile(String fileName) {

        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get(fileName));
        } catch (IOException ex) {
            lines = new ArrayList<>();
        }
        return lines;
    }

    public static void writeFile(String content, String fileName) throws IOException{
            new File(fileName).createNewFile();
            byte[] strToBytes = content.getBytes();
            Path path = Paths.get(fileName);
            Files.write(path, strToBytes);
    }

}
