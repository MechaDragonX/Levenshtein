package com.mechadragonx.word;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class Main
{
    private static TreeSet<String> words = new TreeSet<>();

    public static void main(String[] args) throws FileNotFoundException
    {
        long startTime = System.currentTimeMillis();
        readDictionary("C:\\Users\\ragha\\OneDrive\\intellijence\\Levenshtein\\src\\com\\mechadragonx\\word\\dictionary_words_small.txt");
        long endTime = System.currentTimeMillis();
        long elapse = endTime - startTime;
        System.out.println("\n" + elapse);

        System.out.println(Lev("dog", "bog"));
    }
    private static void readDictionary(String path) throws FileNotFoundException
    {
        File file = new File(path);
        Scanner scanner = new Scanner(file);
        if(file.exists())
        {
            while(scanner.hasNextLine())
            {
                words.add(scanner.nextLine());
            }
        }
    }
    private static boolean Lev(String start, String target)
    {
        if(!words.contains(target))
        {
            return false;
        }
        char[] wordArray = start.toLowerCase().toCharArray();
        char newLetter = 'a';
        int i = 0;
        boolean looping = true;
        String test;
        for(char letter : wordArray)
        {
            while(looping)
            {
                if(newLetter == start.toCharArray()[i])
                {
                    newLetter++;
                }
                if(newLetter >= 97 && newLetter <= 122) // a = 97 and z = 122 in ASCII
                {
                    wordArray[i] = newLetter;
                    test = String.valueOf(wordArray);
                    if(test.equals(target) && words.contains(test))
                    {
                        return true;
                    }
                    else if(test.charAt(i) == target.charAt(i))
                    {
                        newLetter = 'a';
                        looping = false;
                    }
                    else
                    {
                        newLetter++;
                    }
                }
                else
                {
                    newLetter = 'a';
                    looping = false;
                }
            }
            i++;
            looping = true;
        }
        return false;
    }
}
