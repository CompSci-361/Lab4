import java.util.ArrayList;

public class Bank {
	ArrayList<Account> accounts;
	public Bank() {
		accounts = new ArrayList<Account>();
	}
	public Bank(Account acc) {
		accounts = new ArrayList<Account>();
		accounts.add(acc);
	}
	//Add accounts to the bank
	public void addAccount(Account acc) {
		accounts.add(acc);
	}

	public Account getAccount(Card card){
		for (Account acc: accounts){
			if (acc.getAccountNumber() == card.getAccountNumber()) return acc;
		}
		return null;
	}
	
	public boolean validate(Account acc, int pin) {
		if(acc == null) throw new NullPointerException("Account should not be null");
		if(accounts.contains(acc)) return false;
		return acc.validate(pin);
	}
	
	//Will return -1 if there is not enough money, and will print an error message
	//Returns accounts withdrawal
	//Atm must first call validate on the account.
	public int withdraw(Account acc, int val) {
		return acc.withdraw(val);
	}
	//Returns accounts deposit
	public int deposit(Account acc, int val) {
		return acc.deposit(val);
	}
}
