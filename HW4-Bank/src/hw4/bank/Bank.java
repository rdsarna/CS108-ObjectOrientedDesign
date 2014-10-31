package hw4.bank;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

public class Bank {
	
	private static final int QUEUE_SIZE = 10;
	private static final Transaction nullTrans =
			new Transaction(-1, 0, 0);
	
	class Worker extends Thread {
		Transaction currTrans;
		@Override
		public void run() {
			try {
				while ((currTrans = queue.take()) != nullTrans) {
					transferMoney(currTrans);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			latch.countDown();
			System.out.println(Thread.currentThread().getName() + " is done.");
		}
		
		private void transferMoney(Transaction transaction) {
			int amount = transaction.getAmount();
			int withdrawID = transaction.getWithdrawID();
			int depositID = transaction.getDepositID();
			
			accounts.get(withdrawID).withdraw(amount);

			accounts.get(depositID).deposit(amount);
		}
	}
	
	public void go(String[] args) {
		File file = new File(args[0]);
		int numThreads = Integer.parseInt(args[1]);
		
		latch = new CountDownLatch(numThreads);
		
		kickOffWorkers(numThreads);
		
		readTransactionsFromFile(file, numThreads);
		
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		printFinalAccountStatus();
	}
	
	private void printFinalAccountStatus() {
		Set<Integer> ids = accounts.keySet();
		for (int id : accounts.keySet()) {
			System.out.println(accounts.get(id).toString());
		}
	}
	
	private void readTransactionsFromFile(File file, int numThreads) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(
					new FileReader(file));
			String line;
			while ((line = br.readLine()) != null) {
				String[] tokens = line.split("\\s");
				
				if (tokens.length != 3) {
					throw new IOException();
				}
				
				int withdrawID = Integer.parseInt(tokens[0]);
				int depositID = Integer.parseInt(tokens[1]);
				int amount = Integer.parseInt(tokens[2]);
				
				if (!accounts.containsKey(withdrawID)) {
					accounts.put(withdrawID, new Account(withdrawID));
				}
				
				if (!accounts.containsKey(depositID)) {
					accounts.put(depositID, new Account(depositID));
				}
				
				queue.put(new Transaction(withdrawID, depositID, amount));
			}
			
			for (int i = 0; i < numThreads; i++) {
				queue.put(nullTrans);
			}
			
		} catch (FileNotFoundException e) {
			System.err.println("File not found");
			System.exit(0);
		} catch (IOException e) {
			System.err.println("Error reading file");
			System.exit(0);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

	private void kickOffWorkers(int numThreads) {
		for (int i = 0; i < numThreads; i++) {
			new Worker().start();
		}
	}

	public static void main(String[] args) {
		Bank bank = new Bank();
		bank.go(args);
	}
	
	private BlockingQueue<Transaction> queue = new ArrayBlockingQueue<>(QUEUE_SIZE);
	private Map<Integer, Account> accounts = new HashMap<>();
	private CountDownLatch latch;
	private Object lock;

}
