package document;

import java.util.ArrayList;
import java.util.List;

/**
 * A naive implementation of the Document abstract class.
 * @author UC San Diego Intermediate Programming MOOC team
 */
public class BasicDocument extends Document
{
	/** Create a new BasicDocument object
	 *
	 * @param text The full text of the Document.
	 */
	public BasicDocument(String text)
	{
		super(text);
	}


	/**
	 * Get the number of words in the document.
	 * "Words" are defined as contiguous strings of alphabetic characters
	 * i.e. any upper or lower case characters a-z or A-Z
	 *
	 * @return The number of words in the document.
	 */
	@Override
	public int getNumWords()
	{
		//TODO: Implement this method.  See the Module 1 support videos
	    // if you need help.
		String pattern = "[a-zA-Z]+";
		List<String> words = this.getTokens( pattern );
	    return words.size();
	}

	/**
	 * Get the number of sentences in the document.
	 * Sentences are defined as contiguous strings of characters ending in an
	 * end of sentence punctuation (. ! or ?) or the last contiguous set of
	 * characters in the document, even if they don't end with a punctuation mark.
	 *
	 * @return The number of sentences in the document.
	 */
	@Override
	public int getNumSentences()
	{
	    //TODO: Implement this method.  See the Module 1 support videos
        // if you need help.
		String pattern = "[^!?.]+";
		List<String> sentences = this.getTokens(pattern);
		//System.out.println("\nSentences are: ");
		//System.out.println(sentences);
        return sentences.size();
	}

	/**
	 * Get the number of syllables in the document.
	 * Words are defined as above.  Syllables are defined as:
	 * a contiguous sequence of vowels, except for a lone "e" at the
	 * end of a word if the word has another set of contiguous vowels,
	 * makes up one syllable.   y is considered a vowel.
	 * @return The number of syllables in the document.
	 */
	@Override
	public int getNumSyllables()
	{
	    //TODO: Implement this method.  See the Module 1 support videos
        // if you need help.

		/*
		 * approach 1:
		 * I wrote a regular expression to calculate all the syllables
		 * in the sentence
		 *
		 * later filter out lone 'e'/'E' and decrement the syllables
		 *
		 *
		 *
		 * approach 2:
		 * Implemented the method countSyllables(String word)
		 * in Document.java
		 *
		 */

		//common part to both the approaches
		int totCount = 0;
		if( this.getText().isEmpty() ) {
			//System.out.println("text is empty so returning 0.");
			return totCount;
		}

		String pattern = "[a-zA-Z]+";
		List<String> words = this.getTokens( pattern );

		/*
		 * approach1:
		int count  = 0;
		pattern = "[aeiouyAEIOUY]+";

		List<String> syllables = this.getTokens(pattern);

		count = syllables.size();

		for(String word : words) {
			char lastChar = word.charAt( word.length()-1 );

			//if second last char is a vowel then it's not a lone e condition
			//so continue
			if( word.length() >= 2 && isVowel(word.charAt(word.length()-2)) ) {
				continue;
			}

			//lone e
			//if lastChar is e/E and there is at least one vowel before that
			//then reduce count of syllables by 1
			for( int i=0; i<word.length()-1; i++ ) {
				if( isVowel(word.charAt(i)) && (lastChar == 'e' || lastChar == 'E') ) {
					count--;
					//System.out.println(word + " : reducing count");
					break;
				}
			}
		}

		totCount += count;
        return totCount;
        */

		//approach2:
		for( String word : words ) {
			totCount += countSyllables(word);
		}


		return totCount;

	}




	/* The main method for testing this class.
	 * You are encouraged to add your own tests.  */
	public static void main(String[] args)
	{

		testCase(new BasicDocument("This is a test.  How many???  "
		        + "Senteeeeeeeeeences are here... there should be 5!  Right?"),
				16, 13, 5);
		testCase(new BasicDocument(""), 0, 0, 0);
		testCase(new BasicDocument("sentence, with, lots, of, commas.!  "
		        + "(And some poaren)).  The output is: 7.5."), 15, 11, 4);
		testCase(new BasicDocument("many???  Senteeeeeeeeeences are"), 6, 3, 2);
		testCase(new BasicDocument("Here is a series of test sentences. Your program should "
				+ "find 3 sentences, 33 words, and 49 syllables. Not every word will have "
				+ "the correct amount of syllables (example, for example), "
				+ "but most of them will."), 49, 33, 3);
		testCase(new BasicDocument("Segue"), 2, 1, 1);
		testCase(new BasicDocument("Sentence"), 2, 1, 1);
		testCase(new BasicDocument("Sentences?!"), 3, 1, 1);
		testCase(new BasicDocument("Lorem ipsum dolor sit amet, qui ex choro quodsi moderatius, nam dolores explicari forensibus ad."),
		         32, 15, 1);


		/*
		System.out.println("Testing isVowel");
		BasicDocument doc = new BasicDocument("Number of vowels test");
		System.out.println("a" + doc.isVowel('a'));
		System.out.println("a" + doc.isVowel('e'));
		System.out.println("a" + doc.isVowel('i'));
		System.out.println("a" + doc.isVowel('z'));
		*/

		/*
		System.out.println("Flesch Scores: ");
		System.out.println(new BasicDocument("").getFleschScore());
		*/

		/*
		String s1 = new String("String 1");
		String s2 = "String 1";
		if (s1 == s2) {
		    System.out.println("Equal");
		}
		else {
		    System.out.println("Not equal");
		}
		*/

		/*
		String someText = "one (1), two (2), three (3)";
		//String pattern = "";
		Document d = (new BasicDocument(someText));

		List<String> tokens = d.getTokens("[^, ]+");
		System.out.println(tokens);
		System.out.println(d.getTokens("[^,]+"));
		System.out.println(d.getTokens("[a-z()0-9]+"));
		System.out.println(d.getTokens("[a-z]+|[()0-9]+"));
		*/

		/*
		Document d = (new BasicDocument("some text"));
		String word = "seeeee";
		System.out.println( "number of syllables in " + word + ": " + d.countSyllables(word) );
		*/
	}

}
