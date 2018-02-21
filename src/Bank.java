import java.util.ArrayList;

public class Bank {
	ArrayList<Account> accounts;
	public Bank() {
		accounts = new ArrayList<Account>();
	}
	public Bank(ArrayList<Account> accs) {
		accounts = accs;
	}
	//Add accounts to the bank
	public void addAccount(Account acc) {
		accounts.add(acc);
	}

	public Account getAccount(CardReader card){
		for (Account acc: accounts){
			if (acc.getAccountNumber() == card.getAccountNumber()) return acc;
		}
		return null;
	}
	
	public boolean validate(Account acc, int pin) {
		if(acc == null) throw new NullPointerException("Account should not be null");
		if(!accounts.contains(acc)) return false;
		return acc.validate(pin);
	}
	
	//Will return -1 if there is not enough money, and will print an error message
	//Returns accounts withdrawal
	//Atm must first call validate on the account.
	public int withdraw(Account acc, int val) {
		int balance = acc.getBalance();
		//If there is not enough money, print an error and don't actually change the account money
		if( (balance-val) < 0) {
			System.out.println("Cannot withdraw "+val+" from account.");
			return -1;
		}
		acc.setBalance(balance-=val);
		return acc.getBalance();
	}
	//Returns accounts deposit
	public int deposit(Account acc, int val) {
		//adding money is always allowed
		int balance = acc.getBalance();
		acc.setBalance(balance+=val);
		return acc.getBalance();
	}
	
	//Returns current balance
	public int checkBalance(Account acc){
		return acc.getBalance();
	}

}
