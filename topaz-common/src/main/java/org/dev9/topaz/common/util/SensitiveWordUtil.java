package org.dev9.topaz.common.util;

import java.io.*;
import java.util.ArrayList;

public class SensitiveWordUtil {
    private static ArrayList<String> wordList = new ArrayList<>();

    public static void initWords() throws Exception {
        wordList = new ArrayList<>();

        // How to read file from jar
        // 1. InputStreamReader
        // 2. getResourceAsStream
        // 3. '/'SensitiveWords.txt
        Reader reader=new InputStreamReader(SensitiveWordUtil.class.getResourceAsStream("/SensitiveWords.txt"));
        BufferedReader br = new BufferedReader(reader);

        String line;
        while ((line = br.readLine()) != null)
            wordList.add(line);
        br.close();
    }

    public static String filter(String value) {
        if (value == null) return null;

        for (String string : wordList)
                value = value.replaceAll(string, "***");
        return value;
    }

    public static Boolean isContainSensitiveWords(String value){
        if (null == value) return false;

        for (String string: wordList)
            if (value.contains(string)){
                return true;
            }

        return false;
    }
}
