package hw4.bank;

public class Transaction {

	private int withdrawID;
	private int depositID;
	private int amount;
	
	public Transaction(int withdrawID, int depositID, int amount) {
		this.withdrawID = withdrawID;
		this.depositID = depositID;
		this.amount = amount;
	}

	public int getWithdrawID() {
		return withdrawID;
	}
	
	public int getDepositID() {
		return depositID;
	}
	
	public int getAmount() {
		return amount;
	}
	
}
