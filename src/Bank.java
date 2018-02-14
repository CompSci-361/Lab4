import java.awt.List;
import java.util.ArrayList;

public class Bank {
	ArrayList<Account> accounts;
	public Bank() {
		accounts = new ArrayList<Account>();
	}
	public Bank(Account acc) {
		accounts.add(acc);
	}
	//Add accounts to the bank
	public void addAccount(Account acc) {
		accounts.add(acc);
	}
	//return -1 if not a valid account, if valid, it will return the position of the account in accounts
	//So that it can be accessed later.
	public int validate(Account acc) {
		int ret=-1;
		for(int i=0;i<accounts.size()&&ret<0;++i) {
			if((accounts.get(i)).validate(acc)) {
				ret=i;
			}
		}
		return ret;
	}
	//Will return -1 if there is not enough money, and will print an error message
	//Returns accounts deposit
	//Atm must first call validate on the account.
	public int withdraw(Account acc, int val) {
		return acc.withdraw(val);
	}
	//Returns accounts deposit
	public int deposit(Account acc, int val) {
		return acc.deposit(val);
	}
	
}
