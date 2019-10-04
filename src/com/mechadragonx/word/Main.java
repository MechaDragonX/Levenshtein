package com.mechadragonx.word;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.TreeSet;

public class Main
{
    private static TreeSet<String> words = new TreeSet<>();

    public static void main(String[] args) throws FileNotFoundException
    {
        readDictionary(".\\data\\dang-tent.txt");
        String start = "dang";
        String target = "tent";
        print(start, target, vlad(start, target));
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
    private static int vlad(String start, String target)
    {
        if(start.length() != target.length())
        {
            return -1;
        }
        if(!words.contains(start) || !words.contains(target))
        {
            return -1;
        }

        int differences = 0;
        HashSet<String> previous;
        HashSet<String> current = new HashSet<>();
        int editCount = 0;

        previous = findDistance1(start, differences);
        previous.add(start);
        editCount++;

        while(true)
        {
            previous.addAll(current);
            current.clear();
            differences = 0;
            for(String value : previous)
            {
                current.addAll(findDistance1(value, differences));
            }
            current.removeAll(previous);
            editCount++;
            if(current.isEmpty())
            {
                return -1;
            }
            for(String node : current)
            {
                if(node.equals(target))
                {
                    return editCount;
                }
            }
        }
    }
    private static HashSet<String> findDistance1(String node, int differences)
    {
        HashSet<String> set = new HashSet<>();
        for(String word : words)
        {
            if(word.length() == node.length())
            {
                for(int i = 0; i < node.length(); i++)
                {
                    if(node.charAt(i) != word.charAt(i))
                    {
                        differences++;
                    }
                }
                if(differences == 1)
                {
                    set.add(word);
                }
            }
            differences = 0;
        }
        return set;
    }

    private static int newLev(String start, String target)
    {
        if(start.length() != target.length())
        {
            return 0;
        }
        if(!words.contains(target) && !words.contains(start))
        {
            return 0;
        }

        TreeSet<String> distance1 = new TreeSet<>(); // All words 1 edit distance away from start
        String one = start; // Word to compare
        int editCount = 0;
        int differences = 0; // Differences between start and word in dictionary
        int occurences = 0; // Amount of times letters in target are present in distance1
        int k = 0; // Index variable when looping through distance1 a second time
        String next = ""; // Next word to compare

        while(true)
        {
            for(String word : words)
            {
                if(word.length() == one.length())
                {
                    for(int i = 0; i < one.length(); i++)
                    {
                        if(one.charAt(i) != word.charAt(i))
                        {
                            differences++;
                        }
                    }
                    if(differences == 1)
                    {
                        distance1.add(word);
                    }
                }
                differences = 0;
            }

            for(String string : distance1)
            {
                for(int j = 0; j < string.length(); j++)
                {
                    if (string.charAt(j) == target.charAt(j))
                    {
                        ++occurences;
                    }
                }
            }
            if(occurences == 0)
            {
                return 0;
            }
            else
            {
                for(String value : distance1)
                {
                    for(char letter : value.toCharArray())
                    {
                        if((letter == target.charAt(k)) && (one.charAt(k) != letter))
                        {
                            next = value;
                            editCount++;
                            break;
                        }
                        k++;
                    }
                    if(!next.isEmpty())
                    {
                        break;
                    }
                    k = 0;
                }
            }

            if(next.equals(target))
            {
                return editCount;
            }
            else
            {
                distance1.clear();
                one = next;
                next = "";
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
        if(editCount == -1)
        {
            System.out.println("No path found!");
        }
        else if(editCount == 0)
        {
            System.out.println("They are the same word.");
        }
        else
        {
            System.out.println(target + " is " + editCount + " words away from " + start);
        }
    }
}
