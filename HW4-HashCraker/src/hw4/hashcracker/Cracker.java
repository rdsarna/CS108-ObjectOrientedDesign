package hw4.hashcracker;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class Cracker {
	// Array of chars used to produce strings
	public static final char[] CHARS = "abcdefghijklmnopqrstuvwxyz0123456789.,-!".toCharArray();	
	private static int start, stop;
	private static int numCharLimit;
	private static CountDownLatch countDownLatch;
	
	public static void main(String[] args) {
		if (args.length == 1) {
			String hashValue = generateHashValue(args[0]);
			System.out.println(hashValue);
		}
		else if (args.length == 3) {
			String hashGiven = args[0];
			numCharLimit = Integer.parseInt(args[1]);
			int numThreads = Integer.parseInt(args[2]);
			
			displayPasswords(hashGiven, numCharLimit,
					 numThreads);
		}
		else {
			System.err.println("Illegal number of arguments");
			System.exit(0);
		}
		
		System.out.println("all done");
	}
	
	private static void displayPasswords(String hashValue, int numCharLimit, int numThreads) {
		countDownLatch = new CountDownLatch(numThreads);
		int numCharsPerThread = CHARS.length / numThreads;
		
		start = 0;
		stop = numCharsPerThread;
		
		for (int i = 0; i < numThreads; i++) {
			char[] firstLetters;
			if (i != numThreads-1) {
				firstLetters = Arrays.copyOfRange(CHARS, start, stop);
				
			} else {
				firstLetters = Arrays.copyOfRange(CHARS, start, CHARS.length);
			}
			CrackHash crackJob = 
					new CrackHash(firstLetters, numCharLimit, hashValue, countDownLatch);
			Thread thread = new Thread(crackJob);
			thread.start();
			start = stop;
			stop += numCharsPerThread;
		}
				
		
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static String generateHashValue(String string) {
		byte[] output = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA");
			md.update(string.getBytes());
			output = md.digest();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return hexToString(output);
	}
	
	/*
	 Given a byte[] array, produces a hex String,
	 such as "234a6f". with 2 chars for each byte in the array.
	 (provided code)
	*/
	public static String hexToString(byte[] bytes) {
		StringBuffer buff = new StringBuffer();
		for (int i=0; i<bytes.length; i++) {
			int val = bytes[i];
			val = val & 0xff;  // remove higher bits, sign
			if (val<16) buff.append('0'); // leading 0
			buff.append(Integer.toString(val, 16));
		}
		return buff.toString();
	}
	
	/*
	 Given a string of hex byte values such as "24a26f", creates
	 a byte[] array of those values, one byte value -128..127
	 for each 2 chars.
	 (provided code)
	*/
	public static byte[] hexToArray(String hex) {
		byte[] result = new byte[hex.length()/2];
		for (int i=0; i<hex.length(); i+=2) {
			result[i/2] = (byte) Integer.parseInt(hex.substring(i, i+2), 16);
		}
		return result;
	}
	
	// possible test values:
	// a 86f7e437faa5a7fce15d1ddcb9eaeaea377667b8
	// fm adeb6f2a18fe33af368d91b09587b68e3abcb9a7
	// a! 34800e15707fae815d7c90d49de44aca97e2d759
	// xyz 66b27417d37e024c46526c2f6d358a754fc552f3

}
