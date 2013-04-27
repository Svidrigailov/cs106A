import acm.program.*;
import acm.util.*;

public class Hangman extends ConsoleProgram {
  private HangmanCanvas canvas;
	private HangmanLexicon lexicon = new HangmanLexicon();
	private RandomGenerator rgen = RandomGenerator.getInstance();
	private int numofguesses = 8;
	private static final int NUM_OF_GUESSES = 8;
	/*hidden word*/
	String word;
	
	/*your guess*/
	char result;
	/*String that is updated ,when you  guess letter*/
	String guess;
	/*String ,that you enter in  readLine*/
	String letter;
	/*String for renewing your missed guesses*/
	String addLetters = "";

	public void run() {

		startingProgram();

		enterLetter();

	}

	/* gives you a random word */
	private String returnWord() {

		int numOfWord = rgen.nextInt(0, lexicon.getWordCount() - 1);
		word = lexicon.getWord(numOfWord);
		return word;

	}

	/* set ups the program . */
	private void startingProgram() {
		canvas.reset();

		println("Welcome To Hangman !");
		println("Now  Word Looks  Like  This: " + numOfLetters());
		println("you have " + numofguesses + " guesses left");
		canvas.displayWord(guess);

	}

	/* draws - in the place of letters.now you know the length of the word */
	private String numOfLetters() {
		returnWord();
		guess = "";
		for (int i = 0; i < word.length(); i++) {
			guess += "-";

		}
		return guess;
	}

	private void enterLetter() {

		while (numofguesses > 0) {

			letter = readLine("your guess: ");
			/* this condition checks if you entered something */
			if (letter.length() != 0) {
				/* finds mistake if you entered more than one symbol */
				if (letter.length() > 1) {
					println("length of your guess  is  more than  one symbol");
					enterLetter();

				} else {

					result = letter.charAt(0);
				}
				/* you must enter your guess again,if your guess is not a letter */
				if (!Character.isLetter(result)) {
					println("your guess  is not a letter");
					enterLetter();

				}
				/* checks if the character is lowercase */
				if (Character.isLowerCase(result)) {
					result = Character.toUpperCase(result);
				}
				/*
				 * if your guess is not in the hidden word ,this conditional
				 * sentence draws body part and unguessed letter in canvas.
				 * also, you have one less try
				 */
				if (word.indexOf(result) == -1) {

					println("There  are no " + result + "'s in  this Word");
					canvas.displayWord(guess);
					addLetters = addLetters + result;
					canvas.noteIncorrectGuess(addLetters);
					numofguesses--;
					/*
					 * if you don't have tries program writes ,that you lose and
					 * starts again
					 */
					if (numofguesses < 1) {
						println("You are  completely hang!");
						println("The  word  was: " + word);
						println("You lose");
						canvas.removeAll();

						numofguesses = NUM_OF_GUESSES;
						startingProgram();
						addLetters = "";
						enterLetter();

					}
					/*
					 * if you have tries program continues and gives you a
					 * number of guesses ,that you have and gives you the word
					 * ,how that looks like in the moment
					 */
					if (numofguesses > 0) {
						println("You have " + numofguesses + " guesses Left");

						println("Now word looks like this " + guess);
					}

				}
				if (word.indexOf(result) != -1) {

					println("Your guess is Correct ");

					checkForLetters();
					canvas.displayWord(guess);
					/* if you win ,program starts again */
					if (guess.equals(word)) {

						println("The word was:  " + word);
						println("You Win");
						canvas.removeAll();
						numofguesses = NUM_OF_GUESSES;
						startingProgram();
						addLetters = "";
						enterLetter();

					}

					println("You have " + numofguesses + " guesses left");

					println("Now word  looks like this " + guess);

				}

			}
		}
	}

	/*
	 * Checks, if in the hidden word guessed letter isn't in the first
	 * position,this method cuts substring from the beginning to the index of
	 * guessed letter and plusses letter and substring from i+1 index to the
	 * end. if it is, this method plusses substring from 1 to the end to guess
	 */
	private void checkForLetters() {
		for (int i = 0; i < word.length(); i++) {
			if (word.charAt(i) == result && i != 0) {
				guess = guess.substring(0, i) + result + guess.substring(i + 1);
			}
			if (word.charAt(i) == result && i == 0)
				guess = result + guess.substring(1);
		}

	}

	/* initializes canvas */
	public void init() {
		canvas = new HangmanCanvas();
		add(canvas);
	}

}
