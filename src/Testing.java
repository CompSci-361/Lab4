import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

public class Testing {
	private ArrayList<Account> getInitialAccounts() {
    	Account accountOne = new Account(1234, 6789, 80);
    	Account accountTwo = new Account(6789, 4321, 60);

    	System.out.println("Setting up initial data for ATM...");
    	
    	ArrayList<Account> accounts = new ArrayList<Account>();
    	accounts.add(accountOne);
    	accounts.add(accountTwo);
    	

    	for(Account account : accounts) {
    		System.out.println("Account: " + account.toString());
    	}
    	
    	return accounts;
	}
	
    @Test
    public void test1() throws Throwable {
    	System.out.println("----Test1----");
    	ArrayList<Account> initialAccounts = getInitialAccounts();
    	
    	ATM atm = new ATM(initialAccounts);
    	Card card1 = new Card(1234);
    	int pin = 6789;

    	System.out.println("Inserting Card: " + card1.toString());
    	
    	ATM.BankingSession session = atm.getCardReader().insertCard(card1);

    	System.out.println("Authenticating with pin: " + pin);
    	assertEquals(session.tryAuthenticate(pin), true);
    	assertEquals(session.getIsAuthenticated(), true);
    	System.out.println("Checking Balance... Expected: $80 - Actual: $" + session.getBalance());
    	assertEquals(session.getBalance(), 80.0, 0);
    	ATM.WithdrawingTransaction transaction = (ATM.WithdrawingTransaction) session.withdraw();
    	System.out.println("Withdrawing $20...");
    	transaction.provideAmount(20);
    	assertEquals(session.commitTransaction(transaction), 60.0);
    	System.out.println("Checking Balance... Expected: $60 - Actual: $" + session.getBalance());
    	assertEquals(session.getBalance(), 60.0, 0);	
    	System.out.println("-------------");
    }
    
    @Test
    public void test2() throws Throwable {
    	System.out.println("----Test2----");
    	ArrayList<Account> initialAccounts = getInitialAccounts();
    	
    	ATM atm = new ATM(initialAccounts);
    	Card card1 = new Card(1234);
    	int pin = 6789;
    	

    	System.out.println("Inserting Card: " + card1.toString());
    	
    	ATM.BankingSession session = atm.getCardReader().insertCard(card1);

    	System.out.println("Authenticating with pin: " + pin);	
    	assertEquals(session.tryAuthenticate(pin), true);
    	assertEquals(session.getIsAuthenticated(), true);
    	System.out.println("Checking Balance... Expected: $80 - Actual: $" + session.getBalance());
    	assertEquals(session.getBalance(), 80.0, 0);
    	ATM.WithdrawingTransaction transaction = (ATM.WithdrawingTransaction) session.withdraw();
    	transaction.provideAmount(80);
    	System.out.println("Withdrawing $80...");
    	assertEquals(session.commitTransaction(transaction), 0.0);
    	System.out.println("Checking Balance... Expected: $0 - Actual: $" + session.getBalance());
    	assertEquals(session.getBalance(), 0.0, 0);
    	System.out.println("-------------");
    }
    
    @Test
    public void test3() throws Throwable {
    	System.out.println("----Test3----");
    	ArrayList<Account> initialAccounts = getInitialAccounts();
    	
    	ATM atm = new ATM(initialAccounts);
    	Card card1 = new Card(6789);
    	int pin = 0000;
    	
    	System.out.println("Inserting Card: " + card1.toString());
    	
    	ATM.BankingSession session = atm.getCardReader().insertCard(card1);

    	System.out.println("Authenticating with pin: " + pin);
    	
    	assertEquals(session.tryAuthenticate(pin), false);
    	assertEquals(session.getIsAuthenticated(), false);    	

    	System.out.println("Pin is incorrect.");
    	System.out.println("-------------");
    }
    
    @Test
    public void test4() throws Throwable {
    	System.out.println("----Test4----");
    	ArrayList<Account> initialAccounts = getInitialAccounts();
    	
    	ATM atm = new ATM(initialAccounts);
    	Card card1 = new Card(6789);
    	int pin = 4321;
    	
    	System.out.println("Inserting Card: " + card1.toString());
    	
    	ATM.BankingSession session = atm.getCardReader().insertCard(card1);
    	
    	System.out.println("Authenticating with pin: " + pin);
    	assertEquals(session.tryAuthenticate(pin), true);
    	assertEquals(session.getIsAuthenticated(), true);
    	System.out.println("Checking Balance... Expected: $60 - Actual: $" + session.getBalance());
    	assertEquals(session.getBalance(), 60.0, 0);
    	ATM.WithdrawingTransaction transaction = (ATM.WithdrawingTransaction) session.withdraw();
    	transaction.provideAmount(20);
    	System.out.println("Withdrawing $20...");
    	assertEquals(session.commitTransaction(transaction), 40.0);
    	System.out.println("Checking Balance... Expected: $40 - Actual: $" + session.getBalance());
    	assertEquals(session.getBalance(), 40.0, 0);	
    	System.out.println("-------------");
    }
    
}
