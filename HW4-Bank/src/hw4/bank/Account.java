package hw4.bank;

public class Account {
	
	private final int id;
	private int numTransactions;
	private int balance;
	
	public Account(int id) {
		this.id = id;
		numTransactions = 0;
		balance = 1000;
	}
	
	public synchronized void deposit(int amount) {
		balance += amount;
		numTransactions++;
	}
	
	public synchronized void withdraw(int amount) {
		if (amount > 0) {
			balance -= amount;
			numTransactions++;
		}
	}
	
	public int getID() {
		return id;
	}
	
	@Override
	public String toString() {
		return "Account:" + id + " Bal:" 
				+ balance + " " + "Trans:" + numTransactions; 
	}
	
}
