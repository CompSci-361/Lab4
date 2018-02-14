
public class ATM {
	
	public void start(Card card, int pin){
		if(card == null) throw new NullPointerException("Card should not be null");
		if(Integer.toString(card.getAccountNumber(card)).length() !=  4){
			throw new IllegalArgumentException("accountNumber should be 4 digits");
		}
		
		
	}
}
