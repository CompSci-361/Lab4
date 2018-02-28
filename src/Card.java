
public class Card {
	private int accountNumber;
	
	public Card(int accountNumber) {
		this.accountNumber = accountNumber;
	}
	
	public int getAccountNumber(){
		return this.accountNumber;
	}
	
	@Override
	public String toString() {
		return "Card<[Account Num: " + accountNumber + "]>";
	}
}
