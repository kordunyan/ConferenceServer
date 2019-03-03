package com.aconference.util;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

    public static String clearDirectoryName(String directoryName) {
        return directoryName.replaceAll("[^\\d\\w]", StringUtils.EMPTY);
    }

    public static boolean pathExists(String path) {
        File file = new File(path);
        return file.exists() && file.isDirectory();
    }

    public static File createDirectory(String path) {
        File result = new File(path);
        if (!result.exists() && !result.mkdir()) {
            throw new IllegalStateException("Canot create directory: " + result.getAbsolutePath());
        }
        return result;
    }

    public static String concatPaths(String path, String subPath) {
        return path + File.separator + subPath;
    }

    public static void copyFile(File source, File dest) throws IOException {
        try (InputStream is = new FileInputStream(source);
             OutputStream os = new FileOutputStream(dest)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        }
    }
}
