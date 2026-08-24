import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class EvilHangman
{
    private boolean debug;
    private Scanner scanner;
    private List<String> words;
    private int wordLength;
    private int remainingGuesses = 5;
    private String solution;
    private String guessedLetters = "";

    /**
     * Construct an EvilHangman object and initiailize all instance variables.
     * @param fineName the name of the file that contains the word list.
     * @param debug indicates if the size of the remaining word list
     *        should be included in the toString result.
     * @throws FileNotFoundException when the fileName file does not exist.
     */
    public EvilHangman(String fileName, boolean debug)
        throws FileNotFoundException
    {
        this.debug = debug;
        scanner = new Scanner(System.in);
        inputWords(fileName);
        System.out.print("Number of guesses? ");
        remainingGuesses = scanner.nextInt();
        solution = "";
        for (int i = 0; i < wordLength; i++)
            solution += "-";
        guessedLetters = "";
    }

    public EvilHangman(boolean debug) throws FileNotFoundException
    {
        this.debug = debug;
        scanner = new Scanner(System.in);
       
        words = new ArrayList<String>();
        words.add("ALLY");
        words.add("COOL");
        words.add("BETA");
        words.add("DEAL");
        words.add("ELSE");
        words.add("FLEW");
        words.add("GOOD");
        words.add("HOPE");
        words.add("IBEX");
        
        solution = "";
        for (int i = 0; i < 4; i++)
            solution += "-";
    }
    
    /**
     * Plays one the game.  The user guesses letters until either
     * they guess the word, or they run out of guesses.
     * Game status is printed each turn and winning / losing
     * information is printed at the conclusion of the game.
     */
    public void playGame()
    {
        while(solution.contains("-") && remainingGuesses > 0)
        {
            System.out.println(this);
            String letter = inputLetter();
            guessedLetters += letter;
            List<String> patterns = getPatterns(letter);
            List<List<String>> partitions = getPartitions(patterns, letter);
            words = getLargestRemaining(partitions);
            String oldSolution = solution;
            substitute(words.get(0), letter.substring(0, 1));
            if(oldSolution.equals(solution))
                remainingGuesses --;
        }
        
        if(remainingGuesses > 0)
            System.out.println("You win, congratulations!");
        else
            System.out.println("You lose, sorry!");
            
        int index = (int)(Math.random() * words.size());    
        System.out.println("The word was \"" + words.get(index) + "\"");
    }

    /**
     * Creates and returns a status string that indicates the
     * state of the game.
     * @return the game status string.
     */
    public String toString()
    {
        String result = "\n";
        result += "Remaining guesses: " + remainingGuesses + "\n";
        result += "Guessed letters: " + guessedLetters + "\n";
        result += "Solution: " + solution + "\n";
        //if (debug)
            result += "Remaining words: " + words.size() + "\n";
        return result;
    }


    /**
     * Helper method for the constructor:
     * Inputs the word length from the user, reads in the words from
     * the fileName file, and initializes the words instance variable
     * with the words of the correct length.  This method loops until
     * a valid word length is entered.
     * @param fineName the name of the file that contains the word list.
     * @throws FileNotFoundException when the fileName file does not exist.
     */
    public void inputWords() throws FileNotFoundException
    {
        while (words.size() == 0)
        {
            System.out.print("Word length? ");
            wordLength = scanner.nextInt();

            if (words.size() == 0)
                System.out.println("There are no words with " +
                    wordLength + " letters. ");
        }
    }

    private void inputWords(String fileName) throws FileNotFoundException
    {
        words = new ArrayList<String>();

        while (words.size() == 0)
        {
            System.out.print("Word length? ");
            wordLength = scanner.nextInt();

            Scanner file = new Scanner(new File(fileName));
            while (file.hasNext())
            {
                String word = file.next();
                if (word.length() == wordLength)
                    words.add(word);
            }

            if (words.size() == 0)
                System.out.println("There are no words with " +
                    wordLength + " letters.");
        }
    }

    /**
     * Helper method for playGame:
     * Inputs a one-letter string from the user.
     * Loops until the string is a one-character character in the range
     * a-z or A-Z.
     * @return the single-letter string converted to upper-case.
     */
    public String inputLetter()
    {
        String letter = null;
        while (letter == null)
        {
            System.out.print("Next letter? ");
            letter = scanner.next().toUpperCase();

            if (letter.length() != 1 ||
                "A".compareTo(letter) > 0 ||
                "Z".compareTo(letter) < 0)
            {
                letter = null;
                System.out.println("Invalid input!");
            }
        }
        return letter;
    }

    /**
     * Helper method for getPatterns:
     * Uses word and letter to create a pattern.  The pattern string
     * has the same number of letter as word.  If a character in
     * word is the same as letter, the pattern is letter; Otherwise
     * it's "-".
     * @param word the word used to create the patte    rn
     * @param letter the letter used to create the pattern
     * @return the pattern
     */
    public String getPattern(String word, String letter)
    {
        String pattern = "";

        for(int i = 0; i < word.length(); i++){
            if(word.substring(i, i+1).equals(letter))
                pattern += letter;
            else
                pattern += "-";
        }

        return pattern;    
    }

    /**
     * Helper method for playGame:
     * Gets the patterns for all words in words using letter.
     * @param letter the letter used to find the patterns.
     * @return the list of patterns.
     */
    public List<String> getPatterns(String letter)
    {
        ArrayList<String> patterns = new ArrayList<String>();

        for(String word : words){
            String tempPattern = getPattern(word, letter);
            boolean notThere = true;
            for(String pattern : patterns){
                if(tempPattern.equals(pattern))
                    notThere = false;
            }
            if(notThere)
                patterns.add(getPattern(word, letter));
        }
        
        return patterns;   

    }

    /**
     * Helper method for playGame:
     * Partitions each list in words based on the list of patterns
     * and letter.
     * @param patterns the list of patterns
     * @param letter the letter used to find the pattern for
     *        each word in words.
     * @return the list of partitions.  The ith partition corresponds
     *         to the ith pattern.
     */
    public List<List<String>> getPartitions(
        List<String> patterns, String letter)
    {
        
        List<List<String>> partitions = new ArrayList<List<String>>();
        ArrayList<String> matches = new ArrayList<String>();;
        
        for(String pattern : getPatterns(letter)){
            matches = new ArrayList<String>();
            for(String word : words){
                
                
                if(pattern.equals(getPattern(word, letter))){
                    matches.add(word);
                }
            }
            partitions.add(matches);
        }
        
        return partitions;    // REPLACE

    }

    /**
     * Helper method for playGame:
     * Determines the largest of the list of partitions.
     * @param partitions the list of partitions.
     * @return the largest partition.
     */
    public List<String> getLargestRemaining(List<List<String>> partitions)
    {
        int maxWords = 0;
        int maxPosition = 0;
        int index = 0;
        for(List<String> wordsLeft : partitions){
            if(wordsLeft.size() > maxWords){
                maxWords = wordsLeft.size();
                maxPosition = index;
            }
            index++;
        }
        return partitions.get(maxPosition);    
    }

    /**
     * Helper method for playGame:
     * Creates a new string for solution.  If the ith letter of
     * found equals letter, then the ith letter of solution is
     * changed to letter; Otherwise it is unchanged.
     * @param found the string to check for occurances of letter.
     * @param letter the letter to check for in found.
     */
    public void substitute(String found, String letter)
    {
        String temp = "";
        
        for(int i = 0; i < found.length(); i++){
            if(found.substring(i, i+1).equals(letter)){
                temp += letter;
            }
            else
                temp += solution.substring(i, i+1);
        }
        
        solution = temp;
    }
    
    public String getSolution(){
        return solution;
    }
}
