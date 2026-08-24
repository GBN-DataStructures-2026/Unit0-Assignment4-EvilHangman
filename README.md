# Unit 0 - Assignment 4: Evil Hangman

## Objective
Build a game of Hangman where the computer cheats! Instead of picking a single word at the start, the computer keeps a list of all possible words. Every time the user guesses a letter, the computer groups the remaining words into "word families" based on where that letter appears, and secretly switches its active word list to the **largest group**.

---

## Step-by-Step Method Implementation Guide

Complete the following helper methods inside `EvilHangman.java` in order:

### 1. `getPattern(String word, String letter)`
Creates a single pattern string (e.g., `"-LL-"`) for a given word and guessed letter.
Loop through each character of 'word' and compare to letter.  Either the letter or a dash will be part of the pattern you return.


---

### 2. `getPatterns(String letter)`
Finds every unique pattern string created by `letter` across all current words in `words`.
Create and return an ArrayList of Strings with all of the unique patterns across all current words in 'words'.
You can use the List method .contains(E item) to help check to see if a pattern has already been added to your list.

---

### 3. `getPartitions(List<String> patterns, String letter)`
Groups all current words into separate sub-lists based on their pattern.
* **Step 1:** Create a master list of lists: `List<List<String>> partitions = new ArrayList<>();`.
* **Step 2:** Loop through each `String pattern` in `patterns`.
* **Step 3:** Inside the loop, create a new sub-list: `ArrayList<String> matches = new ArrayList<>();`.
* **Step 4:** Loop through every `String word` in `words`. If `pattern.equals(getPattern(word, letter))`, add `word` to `matches`.
* **Step 5:** Add `matches` to `partitions`.
* **Step 6:** Return `partitions`.

---

### 4. `getLargestRemaining(List<List<String>> partitions)`
Finds and returns the sub-list containing the most words.


---

### 5. `substitute(String found, String letter)`
Updates the game board (ie you are mutating the instance variable `solution`) with any newly revealed letters.
Looping through the `found` variable, if any character is equal to `letter` then you update `solution` to now have that letter.


---

### 6. `playGame()`
Drives the game loop from start to finish.
* **Step 1:** Create a `while` loop that runs as long as `solution` contains `"-"` AND `remainingGuesses > 0`.
* **Step 2:** Inside the loop:
  1. Print game status (`System.out.println(this);`).
  2. Prompt for a letter (`String letter = inputLetter();`) and append it to `guessedLetters`.
  3. Use getPatterns, getPartitions, and gtLargestRemaining to update the words instance variable.
  4. Save `String oldSolution = solution;`.
  5. Update solution board: `substitute(words.get(0), letter);`.
  6. If `oldSolution.equals(solution)` (meaning no new letters were revealed), subtract 1 from `remainingGuesses`.
* **Step 3:** After the loop finishes:
  * If `remainingGuesses > 0`, print `"You win, congratulations!"`.
  * Otherwise, print `"You lose, sorry!"`.
  * Print a random secret word from the remaining `words` list:  
    `int index = (int)(Math.random() * words.size());`  
    `System.out.println("The word was \"" + words.get(index) + "\"");`

---

## How to Test Your Code

### Phase 1: Unit Testing (9-Word Dictionary)
Run `Tester.java` to check each helper method individually before running a full game.

### Phase 2: Full Game Execution (Dictionary File)
Once unit tests pass, switch to running EvilHangmanMain.java. 
This driver can load a full English dictionary (dictionary.txt).

It's also enlightening to print out the number of words remaining (the size of the words variable) during each turn.  You can do this simply by editing EvilHangmanMain.java and changing the second parameter in the constructor call to 'true'.
