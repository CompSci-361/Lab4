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
	public int withdraw(Account acc, int val) {
		return acc.withdraw(val);
	}
	public int deposit(Account acc, int val) {
		return acc.deposit(val);
	}
	
}
