/*  Student information for assignment:
 *
 *  On my honor, Avi Ghayalod, this programming assignment is my own work
 *  and I have not provided this code to any other student.
 *
 *  Name: Avi Ghayalod
 *  email address: aghayalod@utexas.edu
 *  UTEID:akg2628
 *  Section 5 digit ID: 50220
 *  Grader name: Andrew
 *  Number of slip days used on this assignment: 0 
 */

// add imports as necessary

import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages the details of EvilHangman. This class keeps tracks of the possible
 * words from a dictionary during rounds of hangman, based on guesses so far.
 *
 */
public class HangmanManager {

    // instance vars
    // Store dictionary of words
    private Set<String> dictionary;
    // Possible words that can be used
    private ArrayList<String> words;
    // Store status of debug
    private boolean debugOn;
    // Keep Track of guesses possible
    private int totalGuesses;
    // Difficulty of hangman selected implement by using diff==HangMan.HARD or etc.
    private HangmanDifficulty diff;
    // Length of word selected
    private int wordLen;
    // number of guesses made
    int numGuessesMade;
    // Current pattern of choices being picked
    private char[] pattern;
    // TreeSet of already guessed chars
    TreeSet<Character> guessed;

    /**
     * Create a new HangmanManager from the provided set of words and phrases. pre:
     * words != null, words.size() > 0
     * 
     * @param words   A set with the words for this instance of Hangman.
     * @param debugOn true if we should print out debugging to System.out.
     */
    public HangmanManager(Set<String> words, boolean debugOn) {
        if (words == null || words.size() <= 0)
            throw new IllegalArgumentException("set of words cannot be empty or null");
        this.dictionary = words;
        this.debugOn = debugOn;

    }

    /**
     * Create a new HangmanManager from the provided set of words and phrases.
     * Debugging is off. pre: words != null, words.size() > 0
     * 
     * @param words A set with the words for this instance of Hangman.
     */
    public HangmanManager(Set<String> words) {
        if (words == null || words.size() > 0)
            throw new IllegalArgumentException("set of words cannot be empty or null");
        this.dictionary = words;
        this.debugOn = false;
    }

    /**
     * Get the number of words in this HangmanManager of the given length. pre: none
     * 
     * @param length The given length to check.
     * @return the number of words in the original Dictionary with the given length
     */
    public int numWords(int length) {
        int count = 0;
        /// Find words that of the specified length
        for (String word : dictionary) {
            if (word.length() == length)
                count++;
        }

        return count;
    }

    /**
     * Get for a new round of Hangman. Think of a round as a complete game of
     * Hangman.
     * 
     * @param wordLen    the length of the word to pick this time. numWords(wordLen)
     *                   > 0
     * @param numGuesses the number of wrong guesses before the player loses the
     *                   round. numGuesses >= 1
     * @param diff       The difficulty for this round.
     */
    public void prepForRound(int wordLen, int numGuesses, HangmanDifficulty diff) {
        if (numWords(wordLen) <= 0 || numGuesses < 1)
            throw new IllegalArgumentException(
                    "number of words of wordLen must be greater than 0 and numGuesses must be greater than or equal 1");
        // Reset everything with user input
        this.totalGuesses = numGuesses;
        this.wordLen = wordLen;
        pattern = new char[wordLen];
        words = new ArrayList<String>();
        this.diff = diff;
        numGuessesMade = 0;
        guessed = new TreeSet<Character>();
        words = new ArrayList<String>();
        // Only add words that are of the right length
        for (String word : dictionary) {
            if (word.length() == wordLen) {
                words.add(word);
            }
            // Set pattern to only dashes to reset
        }
        for (int i = 0; i < wordLen; i++) {
            pattern[i] = '-';

        }

    }

    /**
     * The number of words still possible (live) based on the guesses so far.
     * Guesses will eliminate possible words.
     * 
     * @return the number of words that are still possibilities based on the
     *         original dictionary and the guesses so far.
     */
    public int numWordsCurrent() {
        return words.size();
    }

    /**
     * Get the number of wrong guesses the user has left in this round (game) of
     * Hangman.
     * 
     * @return the number of wrong guesses the user has left in this round (game) of
     *         Hangman.
     */
    public int getGuessesLeft() {
        return totalGuesses - numGuessesMade;
    }

    /**
     * Return a String that contains the letters the user has guessed so far during
     * this round. The String is in alphabetical order. The String is in the form
     * [let1, let2, let3, ... letN]. For example [a, c, e, s, t, z]
     * 
     * @return a String that contains the letters the user has guessed so far during
     *         this round.
     */
    public String getGuessesMade() {
        return guessed.toString();
    }

    /**
     * Check the status of a character.
     * 
     * @param guess The character to check.
     * @return true if guess has been used or guessed this round of Hangman, false
     *         otherwise.
     */
    public boolean alreadyGuessed(char guess) {
        return getGuessesMade().indexOf(guess) >= 0;
    }

    /**
     * Get the current pattern. The pattern contains '-''s for unrevealed (or
     * guessed) characters and the actual character for "correctly guessed"
     * characters.
     * 
     * @return the current pattern.
     */
    public String getPattern() {
        return new String(pattern);
    }

    /**
     * create potential pattern with made guess and given word
     * 
     * @param word that is being used
     */
    private char[] updatePattern(String word, char guess) {
        char[] currPattern = new char[wordLen];
        // Create a temporary copy of pattern to create a pattern for each word
        System.arraycopy(pattern, 0, currPattern, 0, wordLen);
        for (int i = 0; i < wordLen; i++) {
            if (word.charAt(i) == guess && currPattern[i] == '-')
                currPattern[i] = guess;
        }
        return currPattern;
    }

    // pre: !alreadyGuessed(ch)
    // post: return a tree map with the resulting patterns and the number of
    // words in each of the new patterns.
    // the return value is for testing and debugging purposes
    /**
     * Update the game status (pattern, wrong guesses, word list), based on the give
     * guess.
     * 
     * @param guess pre: !alreadyGuessed(ch), the current guessed character
     * @return return a tree map with the resulting patterns and the number of words
     *         in each of the new patterns. The return value is for testing and
     *         debugging purposes.
     */
    public TreeMap<String, Integer> makeGuess(char guess) {

        // if(alreadyGuessed(guess))
        // throw new IllegalStateException("This guess has already been made");
        // Store length of each family and associated array
        TreeMap<String, Integer> debugPatterns = new TreeMap<String, Integer>();
        // Keep track of pattern and associated word family
        Map<String, ArrayList<String>> patterns = new HashMap<String, ArrayList<String>>();
        // Go through all words in words set
        for (String word : words) {
            // Create potential patterns
            String choice = new String(updatePattern(word, guess));
            // Check if new pattern
            if (!patterns.containsKey(choice)) {
                ArrayList<String> fam = new ArrayList<String>();
                fam.add(word);
                patterns.put(choice, fam);
                debugPatterns.put(choice, fam.size());
            }
            // Check if pattern already exists
            else {
                ArrayList<String> fam = patterns.get(choice);
                fam.add(word);
                patterns.put(choice, fam);
                debugPatterns.put(choice, fam.size());
            }

        }

        // List of patterns that will be sorted
        ArrayList<Family> sortedPatterns = new ArrayList<Family>();

        // Fill array using debugPatterns
        Set<String> keys = debugPatterns.keySet();
        for (String key : keys) {
            Family data = new Family(key, debugPatterns.get(key));
            sortedPatterns.add(data);
        }

        // Sort sortedPatterns list in terms of "hardest" patterns
        Collections.sort(sortedPatterns);
        numGuessesMade++;

        // update words array list
        updateWords(sortedPatterns, patterns);

        // Add guess to already guessed
        guessed.add(guess);

        return debugPatterns;
    }

    /**
     * DEBUGGING: Picking hardest list. DEBUGGING: New pattern is: ----. New family
     * has 6 words.
     */
    private void printDebugStatements(Family fam) {
        // Convert diff to String
        String difficulty = null;
        if (diff == HangmanDifficulty.HARD)
            difficulty = "hardest";
        else if (diff == HangmanDifficulty.MEDIUM)
            difficulty = "medium";
        else if (diff == HangmanDifficulty.EASY)
            difficulty = "easiest";

        int famSize = fam.getSize();
        String famPattern = fam.getPattern();

        System.out.println("\nDEBUGGING: Picking " + difficulty + " list.\nDEBUGGING: New pattern is: " + famPattern
                + ". New family has " + famSize + " words.\n");

    }

    /**
     * Used to update set of words used based on difficulty set by user.
     * 
     * If hardest,return the set of the hardest words based on the size of the
     * family associated with each pattern, number of unrevealed characters in the
     * pattern, and ASCII value of each pattern
     * 
     * If medium, alternate between hardest and second hardest set of words every
     * four guesses
     * 
     * If easy, alternate between hardest and second hardest set of words every
     * guess
     * 
     * @param list of Family elements which contain the pattern and number of words
     *             in the family associated with this pattern
     * @param map  is the pattern and families of words associated with this pattern
     */
    private void updateWords(ArrayList<Family> list, Map<String, ArrayList<String>> map) {
        // Hardest family in the list
        Family hardest = list.get(list.size() - 1);
        // Easier family in the list, unless family is only one word long
        Family easier = list.get(list.size() - 1);
        if (list.size() > 1)
            easier = list.get(list.size() - 2);

        // if hardest difficulty provide hardest family every time
        if (diff == HangmanDifficulty.HARD) {
            updateGame(hardest, map);

        }
        // if easiest difficulty provide alternating between easier and hardest family
        if (diff == HangmanDifficulty.EASY) {
            if (numGuessesMade % 2 == 0) {
                updateGame(easier, map);
            } else {

                updateGame(hardest, map);

            }
        }
        // if medium difficulty alternate easier family every four guesses
        if (diff == HangmanDifficulty.MEDIUM)
            if (numGuessesMade % 4 == 0) {
                updateGame(easier, map);

            } else {
                updateGame(hardest, map);

            }

    }

    /**
     * Update words and current pattern
     * 
     * @param fam family that is chosen by the based on difficulty
     * @param map is the pattern and families of words associated with this pattern
     */
    private void updateGame(Family fam, Map<String, ArrayList<String>> map) {
        words = map.get(fam.getPattern());
        pattern = fam.getPattern().toCharArray();
        if (debugOn)
            printDebugStatements(fam);
    }

    /**
     * This class is designed to store the pattern and the size of its associated
     * family of words
     * 
     * It is designed to help with the sorting process in order to find the hardest
     * and second hardest set of words
     * 
     * @author aghayalod
     */
    private class Family implements Comparable<Family> {
        private String pattern;
        private Integer size;

        /**
         * Basic constructor for the Family object
         * 
         * @param pattern is the pattern to which the family of words is associated
         * @param size    is the size of the family of words associated with the pattern
         */
        Family(String key, Integer freq) {
            pattern = key;
            size = freq;
        }

        /**
         * @return the pattern of the Family
         */
        public String getPattern() {
            return pattern;
        }

        public int getSize() {
            return size;
        }

        /**
         * This method is deisgned to find the hardest family in a set of families
         * 
         * @param o is the other Family object the initial family object is being
         *          compared with
         */
        @Override
        public int compareTo(Family other) {
            
            int sizeDiff = size - other.size;
            int dashDiff = countDashes(pattern) - countDashes(other.pattern);
            int ASCIIdiff = other.pattern.compareTo(pattern);
            // If families are not the same size
            if (sizeDiff != 0) {
                return sizeDiff;
            }
            // If families are the same size
            else {
                // Check if number of dashes in each family's pattern is the same=
                if (dashDiff != 0) {
                    return dashDiff;
                }
                // Check if pattern have same ASCII value
                else {
                    return ASCIIdiff;
                }
            }
        }

        /**
         * Used to convert Family object to String for debugging purposes.
         */
        public String toString() {
            return ("Pattern: " + pattern + " " + "Size: " + size);
        }
    }

    /**
     * Return the secret word this HangmanManager finally ended up picking for this
     * round. If there are multiple possible words left one is selected at random.
     * <br>
     * pre: numWordsCurrent() > 0 7
     * 
     * @return return the secret word the manager picked.
     */
    public String getSecretWord() {
        if (numWordsCurrent() <= 0)
            throw new IllegalStateException("set of words cannot be empty");
        int index = 0;
        // If more than one letter left
        if (words.size() > 1) {
            index = (int) (Math.random() * words.size() - 1);
        }
        return words.get(index);
    }

    /**
     * Count dashes in given sequence
     * 
     * @param pattern
     * @return dashes in given sequence
     */
    private int countDashes(String pattern) {
        int count = 0;
        for (int i = 0; i < pattern.length(); i++) {
            if (pattern.charAt(i) == '-')
                count++;
        }
        return count;
    }
}
