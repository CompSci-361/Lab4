
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
			System.out.print("Cannot withdraw "+val+" from account.");
			return -1;
		}
		return balance-=val;
	}
	//Account comp is a valid input if it matches the accountNumber and the PINcode of this account.
	public boolean validate(Account comp) {
		return comp.getAccountNumber()==this.accountNumber&&comp.getAccountPIN()==this.PINcode;
	}
}
