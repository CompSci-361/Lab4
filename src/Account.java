
public class Account {
	private int accountNumber;
	private int PINcode;
	private double balance;
	public Account(int acc, int pin, double bal) {
		accountNumber = acc;
		PINcode = pin;
		balance = bal;
	}
	public int getAccountNumber() {
		return accountNumber;
	}
	public int getAccountPIN() {
		return PINcode;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	public boolean validate(double pin) {
		return pin == PINcode;
	}
	
	//Account comp is a valid input if it matches the accountNumber and the PINcode of this account.
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Account)) return false;
		Account comp = (Account)obj;
		return comp.getAccountNumber() == this.accountNumber && comp.getAccountPIN() == this.PINcode;
	}
	
	@Override
	public String toString() {
		return "Account<[Account Num: " + getAccountNumber() + " | Balance: $" + getBalance() + " | Pin: " + getAccountPIN() + "]>";
	}
}
