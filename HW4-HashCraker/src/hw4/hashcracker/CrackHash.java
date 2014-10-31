package hw4.hashcracker;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class CrackHash implements Runnable {
	private char[] firstLetters;
	private int pwdLength;
	private String hashValue;
	private List<String> passwords;
	private CountDownLatch countDownLatch;
	
	public CrackHash(char[] firstLetters, int pwdLength, String hashValue,
			CountDownLatch countDownLatch) {
		this.firstLetters = firstLetters;
		this.pwdLength = pwdLength;
		this.hashValue = hashValue;
		this.countDownLatch = countDownLatch;
	}

	@Override
	public void run() {
		for (int i = 0; i < firstLetters.length; i++) {
			getWords(firstLetters[i]);
		}
		countDownLatch.countDown();
	}

	private void getWords(char firstLetter) {
		String allLetters = new String(Cracker.CHARS);
		for (int wordLength = 1; wordLength <= pwdLength-1; wordLength++) {
			makeWords("", allLetters, firstLetter, wordLength);
		}
	}
	
	private void makeWords(String soFar, String remaining, char firstLetter,
			int wordLength) {
		if (soFar.length() >= wordLength) {
			String word = firstLetter + soFar;
			String hash = Cracker.generateHashValue(word);
			if (hash.equals(hashValue)) {
				System.out.println(word);
			}
			return;
		}
		
		for (int index = 0; index < remaining.length(); index++) {
			String newSoFar = soFar + remaining.charAt(index); 
			makeWords(newSoFar, remaining, firstLetter, wordLength);
		}
	}

	public List<String> getPasswords() {
		return passwords;
	}

}
