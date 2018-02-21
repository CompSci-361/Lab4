import java.util.ArrayList;


public class ATM {
	private Bank bank;
	private CardReader cardReader;
	
	public ATM(){
		bank = new Bank();
		cardReader = new CardReader();
	}
	
	public ATM(ArrayList<Account> startingAccounts) {
		bank = new Bank(startingAccounts);
		cardReader = new CardReader();
	}
	
	
	public BankingSession start(CardReader card) throws Throwable{
		//start is called when the user first walks up to the ATM and the session begins
		
		//validation below should be moved to banking session.
		if(card == null) throw new NullPointerException("Card should not be null");
		if(Integer.toString(card.getAccountNumber()).length() !=  4){
			throw new IllegalArgumentException("accountNumber should be 4 digits");
		}
		
		BankingSession session = new BankingSession(card);
		return session;	
	}
	
	public class BankingSession {
		private boolean isAuthenticated = false;
		private CardReader enteredCard = null;
		private Account enteredAccount = null;
		BankingSession(CardReader card) throws Exception {
			enteredCard = card;
			isAuthenticated = false;
			
			if (bank.getAccount(enteredCard) == null) {
				throw new Exception("Account not found");
			}
		}
		
		public boolean getIsAuthenticated() {
			return isAuthenticated;
		}
		
		public int withdraw(int val) {
			if(!(getIsAuthenticated())) return -1;
			return bank.withdraw(enteredAccount, val);
		}
		
		public int deposit(int val) {
			if(!(getIsAuthenticated())) return -1;
			return bank.deposit(enteredAccount, val);
		}
		
		public boolean tryAuthenticate(int pinCode) {
			if (isAuthenticated) return true;
			
			Account temp = bank.getAccount(enteredCard);
			isAuthenticated = bank.validate(temp, pinCode);
			if(isAuthenticated) 
				enteredAccount = temp;
			return isAuthenticated;
		}
		
		public int getBalance() {
			if(!(getIsAuthenticated())) return -1;
			return bank.checkBalance(enteredAccount);
		}

		public void end() {
			isAuthenticated = false;
			enteredCard = null;
			enteredAccount = null;
		}		
	}
}
