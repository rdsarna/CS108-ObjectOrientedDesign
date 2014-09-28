package assign1;

import java.util.HashSet;
import java.util.Set;

// CS108 HW1 -- String static methods

public class StringCode {

	/**
	 * Given a string, returns the length of the largest run.
	 * A a run is a series of adajcent chars that are the same.
	 * @param str
	 * @return max run length
	 */
	public static int maxRun(String str) {
		if (str.length() == 0) return 0;
		int max = 1;
		int currentCount = 1;
		for (int i = 1; i < str.length(); i++) {
			char ch = str.charAt(i);
			if (ch == str.charAt(i-1)) {
				currentCount++;
			} else {
				if (currentCount > max)
					max = currentCount;
				currentCount = 1;
			}
		}
		return max;
	}

	
	/**
	 * Given a string, for each digit in the original string,
	 * replaces the digit with that many occurrences of the character
	 * following. So the string "a3tx2z" yields "attttxzzz".
	 * @param str
	 * @return blown up string
	 */
	public static String blowup(String str) {
		String result = "";

		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);
			if (Character.isDigit(ch) && (i != str.length()-1)) {
				for (int j = 0; j < (ch-'0'); j++) {
					result += str.charAt(i+1);
				}
			}
			else if (Character.isDigit(ch) && (i == str.length()-1))
				return result; // Do nothing and return the string
			else
				result += ch;
		}

		return result;
	}
	
	/**
	 * Given 2 strings, consider all the substrings within them
	 * of length len. Returns true if there are any such substrings
	 * which appear in both strings.
	 * Len should be 1 or more.
	 */
	public static boolean stringIntersect(String a, String b, int len) {
		HashSet<String> set = new HashSet<String>();
				
		for (int i = 0; i < a.length()+1-len; i++)
			set.add(a.substring(i, i+len));
		
		for (int i = 0; i < b.length()+1-len; i++) {
			if (set.contains(b.substring(i, i+len)))
				return true;
		}
		return false;
	}
}
