package document;

/**
 * A class that represents a text document
 * @author UC San Diego Intermediate Programming MOOC team
 */
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Document {

	private String text;

	/** Create a new document from the given text.
	 * Because this class is abstract, this is used only from subclasses.
	 * @param text The text of the document.
	 */
	protected Document(String text)
	{
		this.text = text;
	}

	/** Returns the tokens that match the regex pattern from the document
	 * text string.
	 * @param pattern A regular expression string specifying the
	 *   token pattern desired
	 * @return A List of tokens from the document text that match the regex
	 *   pattern
	 */
	protected List<String> getTokens(String pattern)
	{
		ArrayList<String> tokens = new ArrayList<String>();
		Pattern tokSplitter = Pattern.compile(pattern);
		Matcher m = tokSplitter.matcher(text);

		while (m.find()) {
			tokens.add(m.group());
		}

		return tokens;
	}

	// This is a helper function that returns the number of syllables
	// in a word.  You should write this and use it in your
	// BasicDocument class.
	// You will probably NOT need to add a countWords or a countSentences method
	// here.  The reason we put countSyllables here because we'll use it again
	// next week when we implement the EfficientDocument class.
	protected int countSyllables(String word)
	{
		// TODO: Implement this method so that you can call it from the
	    // getNumSyllables method in BasicDocument (module 1) and
	    // EfficientDocument (module 2).

		if( word.isEmpty() ) {
			return 0;
		}

		int countSyll = 0;

		/*
		int counter = 0;
		for( int i=0; i<word.length()-1;  ){
			counter = i+1;

			System.out.println("i: " + i + ", counter: " + counter);

			char ch = word.charAt(i);
			System.out.println("char: " + ch);

			if( isVowel( ch ) ){
				System.out.println("Is vowel.");
				while( isVowel(word.charAt(counter)) ) {
					System.out.println("inside while loop");
					System.out.println("is vowel, char: " + word.charAt( counter ));
					counter++;

					if( counter > word.length()-1 ) {
						System.out.println("counter greater than last index....breaking");
						break;
					}
				}
				System.out.println("out of loop");
				countSyll++;
			}
			else {
				System.out.println("not a vowel... next round.");
			}
			System.out.println("counter: " + counter + ", assigning to i");
			i = counter;
		}
		*/

		List<Integer> indexOfVowels = new ArrayList<Integer>();
		for(int i=0; i<word.length(); i++) {
			if( isVowel(word.charAt(i)) ) {
				indexOfVowels.add(i);
			}
		}

		//System.out.println(indexOfVowels);

		for( int i=0; i<indexOfVowels.size()-1; ) {
			if( indexOfVowels.get(i+1) - indexOfVowels.get(i) == 1 ) {
				//System.out.println("removing index at i: " + i);

				indexOfVowels.remove(i);

				//System.out.println("indexOfVowels after removal: ");
				//System.out.println(indexOfVowels);
			}
			else {
				i++;
			}
		}

		//System.out.println("final indexOfVowels: ");

		countSyll = indexOfVowels.size();

		//System.out.println("countSyll: " + countSyll);

		char lastChar = word.charAt( word.length()-1 );
		//check if lastChar is a lone e/E
		if( word.length() >=2
				&& !isVowel( word.charAt( word.length()-2 ))
				&& indexOfVowels.size() > 1
				&& (lastChar == 'e' || lastChar == 'E') ) {
			//System.out.println("lastChar is a lone e/E");
			countSyll--;
		}


		//System.out.println(indexOfVowels);

		//countSyll = indexOfVowels.size();
	    return countSyll;

		//return 0;
	}

	protected boolean isVowel( char ch ) {
		String vowels = "aeiouyAEIOUY";
		if( vowels.contains( ch+"" ) ) {
			return true;
		}
		return false;
	}

	/** A method for testing
	 *
	 * @param doc The Document object to test
	 * @param syllables The expected number of syllables
	 * @param words The expected number of words
	 * @param sentences The expected number of sentences
	 * @return true if the test case passed.  False otherwise.
	 */
	public static boolean testCase(Document doc, int syllables, int words, int sentences)
	{
		System.out.println("Testing text: ");
		System.out.print(doc.getText() + "\n....");
		boolean passed = true;
		int syllFound = doc.getNumSyllables();
		int wordsFound = doc.getNumWords();
		int sentFound = doc.getNumSentences();
		if (syllFound != syllables) {
			System.out.println("\nIncorrect number of syllables.  Found " + syllFound
					+ ", expected " + syllables);
			passed = false;
		}
		if (wordsFound != words) {
			System.out.println("\nIncorrect number of words.  Found " + wordsFound
					+ ", expected " + words);
			passed = false;
		}
		if (sentFound != sentences) {
			System.out.println("\nIncorrect number of sentences.  Found " + sentFound
					+ ", expected " + sentences);
			passed = false;
		}

		if (passed) {
			System.out.println("passed.\n");
		}
		else {
			System.out.println("FAILED.\n");
		}
		return passed;
	}


	/** Return the number of words in this document */
	public abstract int getNumWords();

	/** Return the number of sentences in this document */
	public abstract int getNumSentences();

	/** Return the number of syllables in this document */
	public abstract int getNumSyllables();

	/** Return the entire text of this document */
	public String getText()
	{
		return this.text;
	}

	/** return the Flesch readability score of this document */
	public double getFleschScore()
	{

		if( getNumSentences() == 0 || getNumWords() == 0 ) {
			return 0;
		}

		double fleschScore = 206.835 - 1.015 * ( ((double)getNumWords())/(double)getNumSentences() )
				- 84.6 * ( (double)getNumSyllables()/(double)getNumWords());
	    return fleschScore;
	}



}
