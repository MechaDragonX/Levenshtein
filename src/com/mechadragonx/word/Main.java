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
        readDictionary("C:\\Users\\ragha\\OneDrive\\intellijence\\Levenshtein\\src\\com\\mechadragonx\\word\\dictionary_words_small.txt");
        System.out.println(Lev("dog", "cat"));
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
    private static int Lev(String start, String target)
    {
        if(!words.contains(target) && !words.contains(start))
        {
            return 0;
        }
        if (editDistanceOfOne(start, target))
        {
            return 1;
        }

        char[] wordArray = start.toLowerCase().toCharArray();
        char newLetter = 'a';
        boolean looping = true;
        String test;
        int editCount = 0 ;

        for(int i = 0; i < start.length(); i++)
        {
            looping = true;
            while(looping)
            {
                if(newLetter == wordArray[i])
                {
                    newLetter++;
                }
                if(newLetter >= 97 && newLetter <= 122) // a = 97 and z = 122 in ASCII
                {
                    wordArray[i] = newLetter;
                    test = String.valueOf(wordArray);
                    if(test.equals(target) && words.contains(test))
                    {
                        return editCount;
                    }
                    else if((test.charAt(i) == target.charAt(i)) && words.contains(test))
                    {
                        newLetter = 'a';
                        editCount++;
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
        }
        return editCount;
    }
    private static boolean editDistanceOfOne(String start, String target) {
        int differences = 0;
        for(int i = 0; i < start.length(); i++)
        {
            if(!(start.charAt(i) == target.charAt(i)))
            {
                differences++;
            }
        }
        if(differences == 1)
        {
            return true;
        }
        return false;
    }
}
