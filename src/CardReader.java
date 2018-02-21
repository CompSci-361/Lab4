
public class CardReader {
	private Card enteredCard = null;
	public CardReader(){
		
	}
	
	public int getAccountNumber(){
		return enteredCard != null ? enteredCard.getAccountNumber() : -1;
	}
}
