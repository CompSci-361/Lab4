
public class ATM {
	private  Bank bank;
	
	public ATM(){
		bank = new Bank();
	}
	
	public BankingSession start(Card card){
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
		private Card enteredCard = null;
		private Account enteredAccount = null;
		private BankingSession(Card card) {
			enteredCard = card;
			isAuthenticated = false;
		}
		
		public boolean getIsAuthenticated() {
			return isAuthenticated;
		}
		
		public int withdraw(int val){
			if(!(getIsAuthenticated())) return -1;
			return enteredAccount.withdraw(val);
		}
		
		public int deposit(int val){
			if(!(getIsAuthenticated())) return -1;
			return enteredAccount.deposit(val);
		}
		
		public boolean tryAuthenticate(int pinCode) {
			Account temp = bank.getAccount(enteredCard);
			isAuthenticated = bank.validate(temp, pinCode);
			if(isAuthenticated) 
				enteredAccount = temp;
			return isAuthenticated;
			//todo validate enteredCard with account's pin code
			//set isAuthenticated and then return that variable
		}
		
		
	}
}
