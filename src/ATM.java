
public class ATM {
	
	public BankingSession start(Card card){
		//start is called when the user first walks up to the ATM and the session begins
		
		//validation below should be moved to banking session.
		if(card == null) throw new NullPointerException("Card should not be null");
		if(Integer.toString(card.getAccountNumber(card)).length() !=  4){
			throw new IllegalArgumentException("accountNumber should be 4 digits");
		}
		
		BankingSession session = new BankingSession(card);
		return session;	
	}
	
	public class BankingSession {
		private boolean isAuthenticated = false;
		private Card enteredCard = null;
		private BankingSession(Card card) {
			enteredCard = card;
			isAuthenticated = false;
		}
		
		public boolean getIsAuthenticated() {
			return isAuthenticated;
		}
		
		public boolean tryAuthenticate(int pinCode) {
			//todo validate enteredCard with account's pin code
			//set isAuthenticated and then return that variable
		}
		
		public Account getAccount() {
			return null;
		}
	}
}
