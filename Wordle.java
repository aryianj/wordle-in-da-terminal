import java.util.Scanner;
import java.nio.file.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

class Wordle { // wordle main driver class
    static String userGuess = "_____";
    static String magicWord = WordList.getRandomWord();
    static String word = magicWord;
    static int counter = 0;

    public static void main(String[]args){
        System.out.println("This is Wordle!");
        System.out.println("Your mystery word is a random 5-letter English word.");
        System.out.println("Can you guess it in 6 tries?");
        
        Scanner readUserInput = new Scanner(System.in);
        boolean isDone = false;

        while (isDone == false){
            counter++;
            System.out.print("Guess " + counter + ": ");
            String userInput = readUserInput.nextLine();
            userInput = userInput.toLowerCase();
            if (!magicWord.equals(userInput) && counter < 6){
                checkWordConventions(userInput, magicWord);
            }
            else if (magicWord.equals(userInput)){
                System.out.println("Congratulations! You figured it out!");
                readUserInput.close();
                isDone = true;
            }
            else if (!magicWord.equals(userInput) && counter == 6){
                System.out.println("Sorry! Maybe next time!");
                System.out.println("The word was " + magicWord);
                readUserInput.close();
                isDone = true;
            }
        }
    }

    static void checkWordConventions(String input, String magicWord){ // checks if the word follows the rules
        String userGuessPrint = "";
        String[] userGuessed = {"_","_","_","_","_"};
        
        for (int i = 0; i < userGuess.length(); i++){ // puts the correct letters into userGuessed so they always print
            userGuessed[i] = userGuess.charAt(i) + "";
        }

        if (input.length() == 5){
            String word2 = word; // makes sure that impermanent changes dont stick
            for (char ch : input.toCharArray()){
                if (!Character.isLetter(ch)){
                    counter--;
                    System.out.println("Your guess must only be alphabetic characters.");
                    break;
                }
            }
            for (int i = 0; i < input.length(); i++){
                if (userGuess.charAt(i) == '_'){ // makes sure a position isnt overriden
                    if (input.charAt(i) == word2.charAt(i)){
                        userGuess = userGuess.substring(0, i) + input.charAt(i) + userGuess.substring(i+1); // places letter in userGuess
                        userGuessed[i] = input.charAt(i) + "";  // adds to guessed so it can be printed
                        word2 = word2.substring(0, i) + "_" + word2.substring(i+1); // removes char from string so brackets only occur for letters left
                        word =  word.substring(0, i) + "_" + word.substring(i+1); 
                    }
                    else {
                        if (word2.contains(input.charAt(i) + "")){
                            if (userGuess.charAt(i) == '_'){
                                userGuessed[i] = "[" + input.charAt(i) + "]";  
                                word2 =  word2.substring(0, word2.indexOf(input.charAt(i))) + "_" + word2.substring(word2.indexOf(input.charAt(i))+1);
                            }
                        }
                    }
                }
            }
        for (String x : userGuessed){
            userGuessPrint += x;
        }

        System.out.println(userGuessPrint); 
        }

        else {
            System.out.println("Your guess must be exactly 5-letters.");
            System.out.println(userGuess);
            counter--;
        }
    }
}

class WordList{ // reads the words and writes the 5-leter words to an array!
    static ArrayList<String> wordsGet = new ArrayList<String>();
    static ArrayList<String> fiveLetterWords = new ArrayList<String>();

    public static String getRandomWord(){
        String file = "words.txt";
        Path path = Paths.get(file); 
       
        try {
            wordsGet.addAll(Files.readAllLines(path)); //reads lines and stores in wordsGet
        } 
        catch (IOException e) { 
            System.out.println("Please download or create the words.txt file.");
        }
            
        for (String i : wordsGet){
            if (i.length() == 5){
                fiveLetterWords.add(i); // adds to 5-letter-words if the word is five letters
            }
        }
        
        Random num = new Random();
        int x = num.nextInt(fiveLetterWords.size()); // random int in array length + 1
        return fiveLetterWords.get(x); // returns random 5-letter-word at position x
    }
}