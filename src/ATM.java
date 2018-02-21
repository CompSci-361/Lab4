import java.util.ArrayList;


public class ATM {
	private Bank bank;
	private CardReader cardReader;
	private BankingSession currentSession = null;
	
	public ATM(){
		bank = new Bank();
		cardReader = new CardReader();
	}
	
	public ATM(ArrayList<Account> startingAccounts) {
		bank = new Bank(startingAccounts);
		cardReader = new CardReader();
	}
	
	public CardReader getCardReader() {
		return cardReader;
	}
	
	private BankingSession onCardInserted(Card insertedCard) throws Throwable {
		//this is fired by the card reader when the card is inserted.
		currentSession = start(insertedCard);
		return currentSession;
	}
	
	private void onCardEjected(Card ejectedCard) {
		//this is fired by the card reader when the card is ejected.
		if (currentSession != null) {
			if (currentSession.enteredCard == ejectedCard) {
				currentSession.end();
				//let the user know that the session as ended.
			}
		}
	}
	
	private BankingSession start(Card card) throws Throwable{
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
		BankingSession(Card card) throws Exception {
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
	
	public class CardReader {
		private Card enteredCard = null;
		public CardReader(){
		}
		
		public int getAccountNumber(){
			return enteredCard != null ? enteredCard.getAccountNumber() : -1;
		}
		
		public boolean isCardInserted() {
			return enteredCard != null;
		}
		
		public BankingSession insertCard(Card card) throws Throwable {
			if (isCardInserted()) throw new Exception("There is already a card in the card reader.");
			if (card == null) throw new Exception("Invalid card: null");
			
			//validate card here
			
			enteredCard = card;
			return ATM.this.onCardInserted(card);
		}
		
		public void ejectCard() {
			if (isCardInserted()) {
				ATM.this.onCardEjected(enteredCard);
				enteredCard = null;
			}
		}
	}
	
}
