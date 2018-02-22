import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class ATM {
	private Bank bank;
	private CardReader cardReader;
	private CashDispenser cashDispenser = null;
	private Printer printer = null;
	private BankingSession currentSession = null;
	
	public ATM(){
		bank = new Bank();
		cardReader = new CardReader();
		cashDispenser = new CashDispenser();
		printer = new Printer();
	}
	
	public ATM(ArrayList<Account> startingAccounts) {
		bank = new Bank(startingAccounts);
		cardReader = new CardReader();
		cashDispenser = new CashDispenser();
		printer = new Printer();
	}
	
	public CardReader getCardReader() {
		return cardReader;
	}
	
	public CashDispenser getCashDispenser() {
		return cashDispenser;
	}
	
	public Printer getReceiptPrinter() {
		return printer;
	}
	
	private void displayMessage(String message) {
		System.out.println(message);
	}
	
	private BankingSession onCardInserted(Card insertedCard) throws Throwable {
		//this is fired by the card reader when the card is inserted.
		currentSession = start(insertedCard);
		System.out.println("Enter PIN");
		return currentSession;
	}
	
	private void onCardEjected(Card ejectedCard) {
		//this is fired by the card reader when the card is ejected.
		if (currentSession != null) {
			if (currentSession.enteredCard == ejectedCard) {
				currentSession.end();
				//let the user know that the session as ended.
				System.out.println("EJECT CARD");
			}
		}
	}
	
	private void onSessionTransactionBegan(Transaction transaction, BankingSession session, Account account) {
		//this is fired when the user has began a transaction.
		if (transaction instanceof WithdrawingTransaction || transaction instanceof DepositingTransaction) {
			System.out.println("Amount?");
		}
		
	}

	private void onSessionAuthenticated(BankingSession session, Account enteredAccount) {
		//this is fired when the session was authenticated
		
	}
	
	private void onSessionReadyForTransaction(BankingSession session, Account account) {
		//this is fired when we're able to accept a command from the user.
		System.out.println("Choose Transaction");
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
		
		public Transaction withdraw() throws Throwable {
			if(!(getIsAuthenticated())) throw new Exception("Not authenticated.");
			return new WithdrawingTransaction(this, enteredAccount);
		}
		
		public Transaction deposit() throws Throwable {
			if(!(getIsAuthenticated())) throw new Exception("Not authenticated.");
			return new DepositingTransaction(this, enteredAccount);
		}
		
		public Object commitTransaction(Transaction transaction) throws Throwable {
			if(!(getIsAuthenticated())) throw new Exception("Not authenticated.");
			if (transaction == null) throw new Exception("Cannot perform a null transaction.");
			
			Object result = transaction.executeTransaction();
			ATM.this.onSessionReadyForTransaction(this, enteredAccount);
			return result;
		}
		
		public boolean tryAuthenticate(int pinCode) {
			if (isAuthenticated) return true;
			
			Account temp = bank.getAccount(enteredCard);
			isAuthenticated = bank.validate(temp, pinCode);
			if(isAuthenticated) {
				enteredAccount = temp;
				ATM.this.onSessionAuthenticated(this, enteredAccount);
				ATM.this.onSessionReadyForTransaction(this, enteredAccount);
			}
			return isAuthenticated;
		}
		
		public double getBalance() {
			if(!(getIsAuthenticated())) return -1;
			return bank.checkBalance(enteredAccount);
		}
		
		public void printBalance() {
			if(!(getIsAuthenticated())) return;
			
			double balance = bank.checkBalance(enteredAccount);
			//todo use format strings
			ATM.this.displayMessage("Balance (" + enteredAccount.getAccountNumber() + "): $" + balance);
			ATM.this.onSessionReadyForTransaction(this, enteredAccount);
		}
		
		public void display(String text) {
			//this does nothing other than call display
			ATM.this.displayMessage(text);
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
	
	public class CashDispenser {
		public void dispense(int amount) throws Exception {
			if (amount < 1) {
				//can't dispense less than a single dollar
				throw new Exception("Unable to dispense cash. Amount must be at least one.");
			}
			
			//cash dispensed
			ATM.this.getReceiptPrinter().printTransaction("withdrawl", amount);
		}
	}
	
	public class Printer {
		private String formatTransaction(String transType, double amount){
			 LocalDateTime time = LocalDateTime.now();
			 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
			 String formatDateTime = time.format(formatter);
			 
			return (formatDateTime + " " + transType + " $" + amount);
		}
		public void printTransaction(String transType, double amount) {
			ATM.this.displayMessage(formatTransaction(transType, amount));
		}
		public void print(String text) {
			//this does nothing other than call display since we can't actually print a reciept.
			ATM.this.displayMessage(text);
		}
	}
	
	public abstract class Transaction {
		protected double transactionAmount;
		protected Account transactionAccount;
		protected BankingSession transactionSession;
		protected boolean executed = false;
		public Transaction(BankingSession session, Account account) {
			//todo null check
			transactionSession = session;
			transactionAccount = account;
			
			ATM.this.onSessionTransactionBegan(this, session, account);
		}
		
		public void provideAmount(double amount) {
			transactionAmount = amount;
		}
		
		public void cancelTransaction() {
			//pretend we executed it
			executed = true;
		}
		
		abstract Object executeTransaction();
	}
	
	public class WithdrawingTransaction extends Transaction {

		public WithdrawingTransaction(BankingSession session, Account account) {
			super(session, account);
		}

		@Override
		Object executeTransaction() {
			if (executed) return -1;
			double result = bank.withdraw(transactionAccount, (int)transactionAmount);
			if(result != -1)
				ATM.this.getReceiptPrinter().printTransaction("W", transactionAmount);
			executed = true;
			return result;
		}
		
	}
	
	public class DepositingTransaction extends Transaction {

		public DepositingTransaction(BankingSession session, Account account) {
			super(session, account);
		}

		@Override
		Object executeTransaction() {
			if (executed) return -1;
			double result = bank.deposit(transactionAccount, (int)transactionAmount);
			ATM.this.getReceiptPrinter().printTransaction("D", transactionAmount);
			executed = true;
			return result;
		}
		
	}
}
