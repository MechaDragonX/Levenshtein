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
        readDictionary(".\\data\\dictionary_words_small.txt");
        print("dog", "cat", Lev("dog", "cat"));
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
        if(start.length() != target.length())
        {
            return 0;
        }
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

        for(int i = start.length() - 1; i >= 0; i--)
        {
            while(looping)
            {
                if(wordArray[i] == target.charAt(i))
                {
                    looping = false;
                }
                if(newLetter >= 97 && newLetter <= 122) // a = 97 and z = 122 in ASCII
                {
                    wordArray[i] = newLetter;
                    test = String.valueOf(wordArray);
                    if(test.equals(target) && words.contains(test))
                    {
                        editCount++;
                        return editCount;
                    }
                    else if(test.charAt(i) == target.charAt(i))
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
            looping = true;
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
        else
        {
            return false;
        }
    }
    private static void print(String start, String target, int editCount)
    {
        if(editCount == 0)
        {
            System.out.println("No path found!");
        }
        else
        {
            System.out.println(target + " is " + editCount + " words away from " + start);
        }
    }
}
