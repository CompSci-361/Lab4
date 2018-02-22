import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

public class Testing {
	private ArrayList<Account> getInitialAccounts() {
    	Account accountOne = new Account(1234, 6789, 80);
    	Account accountTwo = new Account(6789, 4321, 60);

    	ArrayList<Account> accounts = new ArrayList<Account>();
    	accounts.add(accountOne);
    	accounts.add(accountTwo);
    	
    	return accounts;
	}
	
    @Test
    public void test1() throws Throwable {
    	ArrayList<Account> initialAccounts = getInitialAccounts();
    	
    	ATM atm = new ATM(initialAccounts);
    	Card card1 = new Card(1234);
    	int pin = 6789;
    	
    	ATM.BankingSession session = atm.getCardReader().insertCard(card1);
    	
    	assertEquals(session.tryAuthenticate(pin), true);
    	assertEquals(session.getIsAuthenticated(), true);
    	assertEquals(session.getBalance(), 80);
    	ATM.Transaction transaction = session.withdraw();
    	transaction.provideAmount(20);
    	assertEquals(session.commitTransaction(transaction), 60);
    	assertEquals(session.getBalance(), 60);	

    }
    
    @Test
    public void test2() throws Throwable {
    	ArrayList<Account> initialAccounts = getInitialAccounts();
    	
    	ATM atm = new ATM(initialAccounts);
    	Card card1 = new Card(1234);
    	int pin = 6789;
    	
    	ATM.BankingSession session = atm.getCardReader().insertCard(card1);
    	
    	assertEquals(session.tryAuthenticate(pin), true);
    	assertEquals(session.getIsAuthenticated(), true);
    	assertEquals(session.getBalance(), 80);
    	ATM.Transaction transaction = session.withdraw();
    	transaction.provideAmount(80);
    	assertEquals(session.commitTransaction(transaction), 0);
    	assertEquals(session.getBalance(), 0);
 
    }
    
    @Test
    public void test3() throws Throwable {
    	ArrayList<Account> initialAccounts = getInitialAccounts();
    	
    	ATM atm = new ATM(initialAccounts);
    	Card card1 = new Card(6789);
    	int pin = 0000;
    	
    	ATM.BankingSession session = atm.getCardReader().insertCard(card1);
    	
    	assertEquals(session.tryAuthenticate(pin), false);
    	assertEquals(session.getIsAuthenticated(), false);    	

    }
    
    @Test
    public void test4() throws Throwable {
    	ArrayList<Account> initialAccounts = getInitialAccounts();
    	
    	ATM atm = new ATM(initialAccounts);
    	Card card1 = new Card(6789);
    	int pin = 4321;
    	
    	ATM.BankingSession session = atm.getCardReader().insertCard(card1);
    	
    	assertEquals(session.tryAuthenticate(pin), true);
    	assertEquals(session.getIsAuthenticated(), true);
    	assertEquals(session.getBalance(), 60);
    	ATM.Transaction transaction = session.withdraw();
    	transaction.provideAmount(20);
    	assertEquals(session.commitTransaction(transaction), 40);
    	assertEquals(session.getBalance(), 40);	
    }
    
}
