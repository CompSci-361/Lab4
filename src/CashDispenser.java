
public class CashDispenser {
	public void dispense(int amount) throws Exception {
		if (amount < 1) {
			//can't dispense less than a single dollar
			throw new Exception("Unable to dispense cash. Amount must be at least one.");
		}
		
		//cash dispensed
		return;
	}
}
