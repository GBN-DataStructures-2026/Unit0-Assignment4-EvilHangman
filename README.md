# Unit 0 - Assignment 4: Evil Hangman

## Objective
Build a game of Hangman where the computer cheats! Instead of picking a single word at the start, the computer keeps a list of all possible words. Every time the user guesses a letter, the computer groups the remaining words into "word families" based on where that letter appears, and secretly switches its active word list to the **largest group**.

---

## Step-by-Step Method Implementation Guide

Complete the following helper methods inside `EvilHangman.java` in order:

### 1. `getPattern(String word, String letter)`
Creates a single pattern string (e.g., `"-LL-"`) for a given word and guessed letter.
* **Step 1:** Initialize an empty string variable (e.g., `String pattern = "";`).
* **Step 2:** Loop through every index of `word`.
* **Step 3:** Compare the single character at index `i` (using `.substring(i, i+1)`) to `letter`.
  * If it matches, append `letter` to `pattern`.
  * Otherwise, append `"-"` to `pattern`.
* **Step 4:** Return `pattern`.

---

### 2. `getPatterns(String letter)`
Finds every unique pattern string created by `letter` across all current words in `words`.
* **Step 1:** Create a new `ArrayList<String>` to store unique patterns.
* **Step 2:** Loop through every `String word` in the `words` instance variable.
* **Step 3:** Call `getPattern(word, letter)` to get the pattern for that word.
* **Step 4:** Check if your pattern list already contains this new pattern (use a boolean flag or `.contains()`). If it is not there yet, add it to your pattern list.
* **Step 5:** Return the list of unique patterns.

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
* **Step 1:** Create tracking variables for `maxWords = 0` and `maxPosition = 0`.
* **Step 2:** Iterate through `partitions` using an index variable `i`.
* **Step 3:** If the size of the sub-list at index `i` (`partitions.get(i).size()`) is greater than `maxWords`, update `maxWords` and set `maxPosition = i`.
* **Step 4:** Return the largest sub-list using `partitions.get(maxPosition)`.

---

### 5. `substitute(String found, String letter)`
Updates the game board (`solution`) with any newly revealed letters.
* **Step 1:** Create a temporary empty string variable (`String temp = "";`).
* **Step 2:** Loop through index `i` from `0` to `found.length() - 1`.
* **Step 3:** If `found.substring(i, i+1)` equals `letter`, append `letter` to `temp`. Otherwise, append the character currently at `solution.substring(i, i+1)`.
* **Step 4:** Reassign `solution = temp;`.

---

### 6. `playGame()`
Drives the game loop from start to finish.
* **Step 1:** Create a `while` loop that runs as long as `solution` contains `"-"` AND `remainingGuesses > 0`.
* **Step 2:** Inside the loop:
  1. Print game status (`System.out.println(this);`).
  2. Prompt for a letter (`String letter = inputLetter();`) and append it to `guessedLetters`.
  3. Get unique patterns: `List<String> patterns = getPatterns(letter);`.
  4. Partition words: `List<List<String>> partitions = getPartitions(patterns, letter);`.
  5. Cheat! Reassign `words = getLargestRemaining(partitions);`.
  6. Save `String oldSolution = solution;`.
  7. Update solution board: `substitute(words.get(0), letter);`.
  8. If `oldSolution.equals(solution)` (meaning no new letters were revealed), subtract 1 from `remainingGuesses`.
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
```bash
javac Tester.java EvilHangman.java
java Tester
