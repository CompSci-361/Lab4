
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
	public void setBalance(int balance){
		this.balance = balance;
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
}
