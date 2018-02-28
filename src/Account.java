
public class Account {
	private int accountNumber;
	private int PINcode;
	private int balance;
	public Account(int acc, int pin, int bal) {
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
	public int getBalance() {
		return balance;
	}
	public int deposit(int val) {
		//adding money is always allowed
		return balance+=val;
	}
	//Take money from account
	public int withdraw(int val) {
		
		//If there is not enough money, print an error and don't actually change the account money
		if(balance-val<0) {
			System.out.println("Cannot withdraw "+val+" from account.");
			return -1;
		}
		return balance-=val;
	}
	
	public boolean validate(int pin){
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
